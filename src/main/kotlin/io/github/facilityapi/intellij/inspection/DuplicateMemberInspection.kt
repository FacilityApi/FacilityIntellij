package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttribute
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorList
import io.github.facilityapi.intellij.psi.FsdErrorSetSpec
import io.github.facilityapi.intellij.psi.FsdMethodSpec
import io.github.facilityapi.intellij.psi.FsdServiceItems

class DuplicateMemberInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor = object : PsiElementVisitor() {
        private val attributeFix = AttributeFix()
        private val serviceMemberFix = ServiceFix()
        private val fieldFix = FieldFix()

        override fun visitElement(element: PsiElement) {
            if (element is FsdServiceItems) {
                for (serviceItem in element.decoratedServiceItemList) {
                    checkForAttributeDuplicates(serviceItem.attributeListList)
                }

                val duplicateMembers = element.decoratedServiceItemList.asSequence()
                    .mapNotNull {
                        it.dataSpec?.identifierDeclaration
                            ?: it.methodSpec?.identifierDeclaration
                            ?: it.errorSetSpec?.identifierDeclaration
                            ?: it.enumSpec?.identifierDeclaration
                    }
                    .groupBy { it.identifier.text }
                    .filter { it.value.size > 1 }

                for ((memberName, memberIds) in duplicateMembers) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.service", memberName)
                    for (memberId in memberIds) {
                        holder.registerProblem(memberId, message, serviceMemberFix)
                    }
                }
            }

            if (element is FsdMethodSpec) {
                val requestFields = element.requestFields?.decoratedFieldList ?: emptyList()
                checkForFieldDuplicates(requestFields, "inspections.bugs.duplicate.member.requestfield")

                val responseFields = element.responseFields?.decoratedFieldList ?: emptyList()
                checkForFieldDuplicates(responseFields, "inspections.bugs.duplicate.member.responsefield")
            }

            if (element is FsdDataSpec) {
                checkForFieldDuplicates(element.decoratedFieldList, "inspections.bugs.duplicate.member.field")
            }

            if (element is FsdEnumSpec) {
                for (enumSpec in element.enumValueList?.decoratedEnumValueList ?: emptyList()) {
                    checkForAttributeDuplicates(enumSpec.attributeListList)
                }

                val duplicateNames = (element.enumValueList?.decoratedEnumValueList ?: emptyList())
                    .groupBy { it.enumValue!!.identifier.text }
                    .filter { it.value.size > 1 }

                for ((caseName, cases) in duplicateNames) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.enumerated", caseName)
                    for (case in cases) {
                        holder.registerProblem(case, message, fieldFix)
                    }
                }
            }

            if (element is FsdErrorList) {
                for (errorSpec in element.decoratedErrorSpecList) {
                    checkForAttributeDuplicates(errorSpec.attributeListList)
                }

                val duplicateErrors = element.decoratedErrorSpecList
                    .groupBy { it.errorSpec!!.identifier.text }
                    .filter { it.value.size > 1 }

                for ((errorName, errors) in duplicateErrors) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.error", errorName)
                    for (error in errors) {
                        holder.registerProblem(error, message, fieldFix)
                    }
                }
            }
        }

        private fun checkForAttributeDuplicates(attributeListList: List<FsdAttributeList>) {
            val duplicates = attributeListList.flatMap { it.attributeList }
                .groupBy { it.attributename.text }
                .filter { it.value.size > 1 }

            for ((memberName, memberIds) in duplicates) {
                val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.attribute", memberName)
                for (memberId in memberIds) {
                    holder.registerProblem(memberId, message, attributeFix)
                }
            }
        }

        private fun checkForFieldDuplicates(fields: List<FsdDecoratedField>, bundleKey: String) {
            for (field in fields) {
                checkForAttributeDuplicates(field.attributeListList)
            }

            val duplicateField = fields
                .groupBy { it.field.identifier.text }
                .filter { it.value.size > 1 }

            for ((fieldName, elements) in duplicateField) {
                val message = FsdBundle.getMessage(bundleKey, fieldName)
                for (element in elements) {
                    holder.registerProblem(element, message, fieldFix)
                }
            }
        }
    }

    class FieldFix : LocalQuickFix {
        override fun getFamilyName() = NAME

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            descriptor.psiElement.delete()
        }

        companion object {
            val NAME = FsdBundle.getMessage("inspections.bugs.duplicate.member.quickfix")
        }
    }

    class ServiceFix : LocalQuickFix {
        private val PsiElement.isServiceItem: Boolean
            get() = this is FsdMethodSpec || this is FsdDataSpec || this is FsdEnumSpec || this is FsdErrorSetSpec

        override fun getFamilyName() = NAME

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            var element: PsiElement? = descriptor.psiElement
            while (element != null && !element.isServiceItem) { element = element.parent }
            element?.delete()
        }

        companion object {
            val NAME = FsdBundle.getMessage("inspections.bugs.duplicate.member.quickfix")
        }
    }

    class AttributeFix : LocalQuickFix {
        override fun getFamilyName() = NAME

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val attribute = descriptor.psiElement as FsdAttribute
            val attributeList = attribute.parent as FsdAttributeList
            val size = attributeList.attributeList.size

            if (size == 1) {
                attributeList.delete()
            } else {
                val index = attributeList.attributeList.indexOf(attribute)

                if (index == size - 1) {
                    attributeList.deleteChildRange(attributeList.attributeList[index - 1].nextSibling, attribute)
                } else {
                    attributeList.deleteChildRange(attribute, attributeList.attributeList[index + 1].prevSibling)
                }
            }
        }

        companion object {
            val NAME = FsdBundle.getMessage("inspections.bugs.duplicate.member.quickfix")
        }
    }
}
