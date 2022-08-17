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
        private val serviceMemberFix = ServiceFix()
        private val fieldFix = FieldFix()

        override fun visitElement(element: PsiElement) {
            if (element is FsdServiceItems) {
                val nameGroups = element.decoratedServiceItemList
                    .mapNotNull {
                        it.dataSpec?.identifierDeclaration
                            ?: it.methodSpec?.identifierDeclaration
                            ?: it.errorSetSpec?.identifierDeclaration
                            ?: it.enumSpec?.identifierDeclaration
                    }.groupBy { it.identifier.text }

                for ((memberName, memberIds) in nameGroups.filter { it.value.size > 1 }) {
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
                val nameGroups = (element.enumValueList?.decoratedEnumValueList ?: emptyList())
                    .groupBy { it.enumValue!!.identifier.text }

                for ((caseName, cases) in nameGroups.filter { it.value.size > 1 }) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.enumerated", caseName)
                    for (case in cases) {
                        holder.registerProblem(case, message, fieldFix)
                    }
                }
            }

            if (element is FsdErrorList) {
                val nameGroups = element.decoratedErrorSpecList.groupBy { it.errorSpec!!.identifier.text }

                for ((errorName, errors) in nameGroups.filter { it.value.size > 1 }) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.error", errorName)
                    for (error in errors) {
                        holder.registerProblem(error, message, fieldFix)
                    }
                }
            }
        }

        private fun checkForFieldDuplicates(fields: List<FsdDecoratedField>, bundleKey: String) {
            val nameGroups = fields.groupBy { it.field.identifier.text }
            for ((fieldName, elements) in nameGroups.filter { it.value.size > 1 }) {
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
}
