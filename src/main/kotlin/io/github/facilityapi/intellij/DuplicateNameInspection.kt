package io.github.facilityapi.intellij

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorList

class DuplicateNameInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor = object : PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is FsdDataSpec) {
                val seenFields = mutableSetOf<String>()
                for (decoratedField in element.decoratedFieldList) {
                    val fieldName = decoratedField.field.identifier.text
                    if (!seenFields.add(fieldName)) {
                        holder.registerProblem(decoratedField, "Duplicate field name: $fieldName")
                    }
                }
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
                        holder.registerProblem(decoratedError, "Duplicate error name: $errorName")
                    }
                }

            }
        }
    }
}
