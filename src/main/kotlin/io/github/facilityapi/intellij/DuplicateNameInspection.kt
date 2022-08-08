package io.github.facilityapi.intellij

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
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
        override fun visitElement(element: PsiElement) {
            if (element is FsdMethodSpec) {
                val requestFields = element.requestFields?.decoratedFieldList ?: emptyList()
                checkForFieldDuplicates(requestFields)

                val responseFields = element.responseFields?.decoratedFieldList ?: emptyList()
                checkForFieldDuplicates(responseFields)
            }

            if (element is FsdDataSpec) {
                checkForFieldDuplicates(element.decoratedFieldList)
            }

            if (element is FsdEnumSpec) {
                val seenCases = mutableSetOf<String>()
                for (case in element.enumValueList?.decoratedEnumValueList ?: emptyList()) {
                    val caseName = case.enumValue!!.identifier.text
                    if (!seenCases.add(caseName)) {
                        holder.registerProblem(case, "Duplicate enum value: $caseName")
                    }
                }
            }

            if (element is FsdErrorList) {
                val seenFields = mutableSetOf<String>()
                for (decoratedError in element.decoratedErrorSpecList) {
                    val errorName = decoratedError.errorSpec!!.identifier.text
                    if (!seenFields.add(errorName)) {
                        holder.registerProblem(decoratedError, "Duplicate error: $errorName")
                    }
                }
            }
        }

        private fun checkForFieldDuplicates(fields: List<FsdDecoratedField>) {
            val seenFields = mutableSetOf<String>()

            for (decoratedField in fields) {
                val fieldName = decoratedField.field.identifier.text
                if (!seenFields.add(fieldName)) {
                    holder.registerProblem(decoratedField, "Illegal field redeclaration: $fieldName")
                }
            }
        }
    }
}
