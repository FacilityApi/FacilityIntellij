package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.descendants
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdNamedElement
import io.github.facilityapi.intellij.psi.FsdReferenceType

class UnusedTypeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            private val deleteFix = DeleteUnusedTypeFix()
            override fun visitElement(element: PsiElement) {
                val namedElement = element as? FsdNamedElement ?: return

                val isUsed = element.containingFile.descendants()
                    .filterIsInstance<FsdReferenceType>()
                    .any { it.reference.isReferenceTo(namedElement) }

                val isDataDefinition = namedElement.parent is FsdDataSpec
                val isEnumDefinition = namedElement.parent is FsdEnumSpec

                if (!isUsed && isDataDefinition) {
                    val message = FsdBundle.getMessage("inspections.hints.unused.data", namedElement.name)
                    holder.registerProblem(
                        namedElement.nameIdentifier!!,
                        message,
                        ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                        deleteFix
                    )
                } else if (!isUsed && isEnumDefinition) {
                    val message = FsdBundle.getMessage("inspections.hints.unused.enum", namedElement.name)
                    holder.registerProblem(
                        namedElement.nameIdentifier!!,
                        message,
                        ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                        deleteFix
                    )
                }
            }
        }
    }
}
