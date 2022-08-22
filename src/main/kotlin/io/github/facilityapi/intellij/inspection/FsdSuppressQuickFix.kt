package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiParserFacade
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.psi.FsdDecoratedElement

class FsdSuppressQuickFix(private val toolId: String) : SuppressQuickFix {
    override fun getFamilyName(): String {
        return "Suppress \"$toolId\""
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val element = PsiTreeUtil.getParentOfType(descriptor.psiElement, FsdDecoratedElement::class.java) ?: return
        val elementIndex = element.parent.children.indexOf(element)

        val newParent = element.parent.copy()
        val newChild = newParent.children[elementIndex]

        val psiFacade = PsiParserFacade.SERVICE.getInstance(project)

        val comment = psiFacade.createLineCommentFromText(element.language, " Suppress $toolId")
        val newline = psiFacade.createWhiteSpaceFromText("\n")

        newParent.addBefore(comment, newChild)
        newParent.addBefore(newline, newChild)

        val codeStylist = CodeStyleManager.getInstance(project)
        element.parent.replace(codeStylist.reformat(newParent))
    }

    override fun isAvailable(project: Project, context: PsiElement): Boolean {
        return context.isValid && PsiTreeUtil.getParentOfType(context, FsdDecoratedElement::class.java) != null
    }

    override fun isSuppressAll(): Boolean = false // todo: investigate this
}
