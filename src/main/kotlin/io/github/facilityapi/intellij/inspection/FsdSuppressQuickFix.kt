package io.github.facilityapi.intellij.inspection

import com.intellij.codeInsight.daemon.impl.actions.AbstractBatchSuppressByNoInspectionCommentFix
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiParserFacade
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import org.jetbrains.annotations.Nls

class FsdSuppressQuickFix(id: String, @Nls text: String) :
    AbstractBatchSuppressByNoInspectionCommentFix(id, false) {

    init {
        setText(text)
    }

    override fun getContainer(context: PsiElement?) = runReadAction {
        PsiTreeUtil.getParentOfType(context, FsdDecoratedElement::class.java)
    }

    override fun createSuppression(project: Project, element: PsiElement, container: PsiElement) {
        val psi = PsiParserFacade.SERVICE.getInstance(project)
        val newline = psi.createWhiteSpaceFromText("\n")

        super.createSuppression(project, element, container)

        container.parent.addBefore(newline, container)
    }
}
