package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.psi.FsdAttribute
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorSpec
import io.github.facilityapi.intellij.supportsValidate

class ValidateAttributeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            val deleteAttributeFix = DeleteAttributeFix()

            override fun visitElement(element: PsiElement) {
                if (element !is FsdAttribute || element.attributename.text != "validate") return
                val decoratedElement = PsiTreeUtil.getParentOfType(element, FsdDecoratedElement::class.java) ?: return

                when (decoratedElement) {
                    is FsdDecoratedServiceItem -> {
                        // The only service item that supports [validate] are enums
                        if (decoratedElement.enumSpec == null) {
                            holder.registerProblem(
                                element,
                                "This has no effect.", // todo: i10n & clarify
                                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                                deleteAttributeFix
                            )
                        }
                    }
                    is FsdDecoratedField -> {
                        if (!supportsValidate(decoratedElement.field)) {
                            holder.registerProblem(
                                element,
                                "This has no effect.", // todo: i10n & clarify
                                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                                deleteAttributeFix
                            )
                        }
                    }
                    is FsdEnumSpec -> {}
                    is FsdErrorSpec -> {
                        holder.registerProblem(
                            element,
                            "This has no effect.", // todo: i10n & clarify
                            ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                            deleteAttributeFix
                        )
                    }
                }
            }
        }
    }
}
