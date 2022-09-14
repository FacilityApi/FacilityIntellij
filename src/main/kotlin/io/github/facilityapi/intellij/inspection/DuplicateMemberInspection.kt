package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import io.github.facilityapi.intellij.psi.FsdDecoratedEnumValue
import io.github.facilityapi.intellij.psi.FsdDecoratedErrorSpec
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorList
import io.github.facilityapi.intellij.psi.FsdMethodSpec
import io.github.facilityapi.intellij.psi.FsdServiceItems
import io.github.facilityapi.intellij.psi.FsdTypes

class DuplicateMemberInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor = object : PsiElementVisitor() {
        private val fix = Fix()

        override fun visitElement(element: PsiElement) {
            if (element is FsdServiceItems) {
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
                        holder.registerProblem(memberId, message, fix)
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
                val duplicateNames = (element.enumValueList?.decoratedEnumValueList ?: emptyList())
                    .groupBy { it.enumValue!!.identifier.text }
                    .filter { it.value.size > 1 }

                for ((caseName, cases) in duplicateNames) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.enumerated", caseName)
                    for (case in cases) {
                        holder.registerProblem(case.enumValue!!.identifier, message, fix)
                    }
                }
            }

            if (element is FsdErrorList) {
                val duplicateErrors = element.decoratedErrorSpecList
                    .groupBy { it.errorSpec!!.identifier.text }
                    .filter { it.value.size > 1 }

                for ((errorName, errors) in duplicateErrors) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.member.error", errorName)
                    for (error in errors) {
                        holder.registerProblem(error.errorSpec!!.identifier, message, fix)
                    }
                }
            }
        }

        private fun checkForFieldDuplicates(fields: List<FsdDecoratedField>, bundleKey: String) {
            val duplicateField = fields
                .groupBy { it.field.identifier.text }
                .filter { it.value.size > 1 }

            for ((fieldName, elements) in duplicateField) {
                val message = FsdBundle.getMessage(bundleKey, fieldName)
                for (element in elements) {
                    holder.registerProblem(element.field.identifier, message, fix)
                }
            }
        }
    }

    class Fix : LocalQuickFix {
        override fun getFamilyName() = NAME

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val decoratedElement = PsiTreeUtil.getParentOfType(descriptor.psiElement, FsdDecoratedElement::class.java)!!
            val nextSibling = PsiTreeUtil.skipWhitespacesForward(decoratedElement)

            decoratedElement.delete()

            val isListItem = decoratedElement is FsdDecoratedEnumValue || decoratedElement is FsdDecoratedErrorSpec
            if (isListItem && nextSibling != null && nextSibling.elementType == FsdTypes.COMMA) {
                nextSibling.delete()
            }
        }

        companion object {
            val NAME = FsdBundle.getMessage("inspections.bugs.duplicate.member.quickfix")
        }
    }
}
