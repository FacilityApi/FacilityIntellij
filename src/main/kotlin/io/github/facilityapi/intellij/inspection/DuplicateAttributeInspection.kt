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
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorList
import io.github.facilityapi.intellij.psi.FsdMethodSpec
import io.github.facilityapi.intellij.psi.FsdServiceItems

class DuplicateAttributeInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor = object : PsiElementVisitor() {
        private val attributeFix = Fix()

        override fun visitElement(element: PsiElement) {
            if (element is FsdServiceItems) {
                for (serviceItem in element.decoratedServiceItemList) {
                    checkForAttributeDuplicates(serviceItem.attributeListList)
                }
            }

            if (element is FsdMethodSpec) {
                val requestFields = element.requestFields?.decoratedFieldList ?: emptyList()
                for (field in requestFields) {
                    checkForAttributeDuplicates(field.attributeListList)
                }

                val responseFields = element.responseFields?.decoratedFieldList ?: emptyList()
                for (field in responseFields) {
                    checkForAttributeDuplicates(field.attributeListList)
                }
            }

            if (element is FsdDataSpec) {
                for (field in element.decoratedFieldList) {
                    checkForAttributeDuplicates(field.attributeListList)
                }
            }

            if (element is FsdEnumSpec) {
                for (enumSpec in element.enumValueList?.decoratedEnumValueList ?: emptyList()) {
                    checkForAttributeDuplicates(enumSpec.attributeListList)
                }
            }

            if (element is FsdErrorList) {
                for (errorSpec in element.decoratedErrorSpecList) {
                    checkForAttributeDuplicates(errorSpec.attributeListList)
                }
            }
        }

        private fun checkForAttributeDuplicates(attributeListList: List<FsdAttributeList>) {
            val duplicates = attributeListList.flatMap { it.attributeList }
                .groupBy { it.attributename.text }
                .filter { it.value.size > 1 }

            for ((memberName, memberIds) in duplicates) {
                val message = FsdBundle.getMessage("inspections.bugs.duplicate.attribute", memberName)
                for (memberId in memberIds) {
                    holder.registerProblem(memberId, message, attributeFix)
                }
            }
        }
    }

    class Fix : LocalQuickFix {
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
            val NAME = FsdBundle.getMessage("inspections.bugs.duplicate.attribute.quickfix")
        }
    }
}
