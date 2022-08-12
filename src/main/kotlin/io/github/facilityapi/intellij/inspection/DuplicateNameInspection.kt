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
import io.github.facilityapi.intellij.psi.FsdMethodSpec

class DuplicateNameInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor = object : PsiElementVisitor() {
        private val fix = Fix()

        override fun visitElement(element: PsiElement) {
            if (element is FsdMethodSpec) {
                val requestFields = element.requestFields?.decoratedFieldList ?: emptyList()
                checkForFieldDuplicates(requestFields, "inspections.bugs.duplicatename.requestfield")

                val responseFields = element.responseFields?.decoratedFieldList ?: emptyList()
                checkForFieldDuplicates(responseFields, "inspections.bugs.duplicatename.responsefield")
            }

            if (element is FsdDataSpec) {
                checkForFieldDuplicates(element.decoratedFieldList, "inspections.bugs.duplicatename.field")
            }

            if (element is FsdEnumSpec) {
                val seenCases = hashSetOf<String>()
                for (case in element.enumValueList?.decoratedEnumValueList ?: emptyList()) {
                    val caseName = case.enumValue!!.identifier.text
                    if (!seenCases.add(caseName)) {
                        val message = FsdBundle.getMessage("inspections.bugs.duplicatename.enumerated", caseName)
                        holder.registerProblem(case, message, fix)
                    }
                }
            }

            if (element is FsdErrorList) {
                val seenFields = hashSetOf<String>()
                for (decoratedError in element.decoratedErrorSpecList) {
                    val errorName = decoratedError.errorSpec!!.identifier.text
                    if (!seenFields.add(errorName)) {
                        val message = FsdBundle.getMessage("inspections.bugs.duplicatename.error", errorName)
                        holder.registerProblem(decoratedError, message, fix)
                    }
                }
            }
        }

        private fun checkForFieldDuplicates(fields: List<FsdDecoratedField>, bundleKey: String) {
            val seenFields = hashSetOf<String>()

            for (decoratedField in fields) {
                val fieldName = decoratedField.field.identifier.text
                if (!seenFields.add(fieldName)) {
                    val message = FsdBundle.getMessage(bundleKey, fieldName)
                    holder.registerProblem(decoratedField, message, fix)
                }
            }
        }
    }

    class Fix : LocalQuickFix {
        override fun getFamilyName() = NAME

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            descriptor.psiElement.delete()
        }

        companion object {
            val NAME = FsdBundle.getMessage("inspections.bugs.duplicatenames.quickfix")
        }
    }
}
