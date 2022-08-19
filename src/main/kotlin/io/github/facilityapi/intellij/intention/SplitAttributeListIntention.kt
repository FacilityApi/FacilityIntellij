package io.github.facilityapi.intellij.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttribute
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import io.github.facilityapi.intellij.reference.createAttribute
import io.github.facilityapi.intellij.reference.createFromText

class SplitAttributeListIntention :  PsiElementBaseIntentionAction() {
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
        val newElement = decoratedElement.copy() as FsdDecoratedElement

        val attributes = attributeList.attributeList.map { it.copy() as FsdAttribute }

        val newAttributeList = newElement.children.first { it.textMatches(attributeList) }
        val newline = createFromText(project, "[dummy]\nservice Dummy { }")
            .filterIsInstance<PsiWhiteSpace>()
            .first()

        for (attribute in attributes) {
            val attr = createAttribute(project, attribute.text)
            val addedElement = newElement.addBefore(attr, newAttributeList)
            newElement.addAfter(newline, addedElement)
        }

        newAttributeList.delete()

        decoratedElement.replace(newElement)
    }

    companion object {
        val TEXT = FsdBundle.getMessage("intentions.attribute.lists.split.text")
        val FAMILY_NAME = FsdBundle.getMessage("intentions.attribute.lists.split.family")
    }
}
