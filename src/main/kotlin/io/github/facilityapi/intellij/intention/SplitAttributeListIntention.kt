package io.github.facilityapi.intellij.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiParserFacade
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import io.github.facilityapi.intellij.reference.createAttribute

class SplitAttributeListIntention : PsiElementBaseIntentionAction() {
    override fun startInWriteAction(): Boolean = true

    override fun getText() = TEXT

    override fun getFamilyName() = FAMILY_NAME

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val attributeList = PsiTreeUtil.getParentOfType(element, FsdAttributeList::class.java) ?: return false
        return attributeList.attributeList.size > 1
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        if (editor == null) return

        val attributeList = PsiTreeUtil.getParentOfType(element, FsdAttributeList::class.java) ?: return
        val decoratedElement = PsiTreeUtil.getParentOfType(attributeList, FsdDecoratedElement::class.java) ?: return

        val psiFacade = PsiParserFacade.SERVICE.getInstance(project)
        val newline = psiFacade.createWhiteSpaceFromText("\n")

        for (attribute in attributeList.attributeList) {
            val attr = createAttribute(project, attribute.text)
            val addedElement = decoratedElement.addBefore(attr, attributeList)
            decoratedElement.addAfter(newline, addedElement)
        }

        attributeList.delete()
    }

    companion object {
        val TEXT = FsdBundle.getMessage("intentions.attribute.lists.split.text")
        val FAMILY_NAME = FsdBundle.getMessage("intentions.attribute.lists.split.family")
    }
}
