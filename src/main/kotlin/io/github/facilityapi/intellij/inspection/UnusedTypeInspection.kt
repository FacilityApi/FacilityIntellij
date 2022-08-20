package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.descendants
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdNamedElement
import io.github.facilityapi.intellij.psi.FsdReferenceType

class UnusedTypeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                val namedElement = element as? FsdNamedElement ?: return

                val isUsed = element.containingFile.descendants()
                    .filterIsInstance<FsdReferenceType>()
                    .any { it.reference.isReferenceTo(namedElement) }

                if (!isUsed && (namedElement.parent is FsdDataSpec || namedElement.parent is FsdEnumSpec)) {
                    holder.registerProblem(namedElement.nameIdentifier!!, "Unused type", ProblemHighlightType.LIKE_UNUSED_SYMBOL)
                }
            }
        }
    }
}
