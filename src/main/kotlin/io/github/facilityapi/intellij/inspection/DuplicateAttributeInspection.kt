package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import io.github.facilityapi.intellij.psi.FsdDecoratedField

class DuplicateAttributeInspection : LocalInspectionTool() {
    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        session: LocalInspectionToolSession
    ): PsiElementVisitor = object : PsiElementVisitor() {
        private val deleteAttributeFix = DeleteAttributeFix()

        override fun visitElement(element: PsiElement) {
            if (element is FsdDecoratedElement) {
                val attributesByName = element.attributeListList
                    .flatMap { it.attributeList }
                    .groupBy { it.attributename.text }

                for ((attributeName, attributes) in attributesByName.filter { it.value.size > 1 }) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.attribute", attributeName)
                    for (memberId in attributes) {
                        holder.registerProblem(memberId, message, deleteAttributeFix)
                    }
                }

                val requiredAttributes = attributesByName["required"]
                if (element is FsdDecoratedField &&
                    PsiTreeUtil.skipWhitespacesForward(element.field.type)?.textMatches("!") == true &&
                    !requiredAttributes.isNullOrEmpty()
                ) {
                    val message = FsdBundle.getMessage("inspections.bugs.duplicate.attribute", "required")
                    holder.registerProblem(requiredAttributes.first(), message, deleteAttributeFix)
                }
            }
        }
    }
}
