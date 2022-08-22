package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.codeInspection.SuppressionUtil
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiParserFacade
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.psi.FsdDecoratedElement

class FsdSuppressQuickFix(private val toolId: String) : SuppressQuickFix {
    override fun getFamilyName(): String {
        return "Suppress \"$toolId\"" // todo: i10n
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val element = PsiTreeUtil.getParentOfType(descriptor.psiElement, FsdDecoratedElement::class.java) ?: return

        val psiFacade = PsiParserFacade.SERVICE.getInstance(project)

        SuppressionUtil.createSuppression(project, element, toolId, element.language)

        val newline = psiFacade.createWhiteSpaceFromText("\n")
        element.parent.addBefore(newline, element)
    }

    override fun isAvailable(project: Project, context: PsiElement): Boolean {
        return context.isValid && PsiTreeUtil.getParentOfType(context, FsdDecoratedElement::class.java) != null
    }

    override fun isSuppressAll(): Boolean = SuppressionUtil.ALL.equals(toolId, true)
}
