package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.tree.LeafPsiElement
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdTypeParameter
import io.github.facilityapi.intellij.psi.FsdTypes

class InvalidNullableTypeInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor = object : PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            val typeParameter = element as? FsdTypeParameter ?: return
            val parameterizedType = typeParameter.type.firstChild as? LeafPsiElement ?: return

            if (parameterizedType.elementType == FsdTypes.NULLABLE) {
                val message = FsdBundle.getMessage("inspection.bugs.nested.nullable.type", typeParameter.type.text)
                holder.registerProblem(typeParameter.type, message)
            }
        }
    }
}
