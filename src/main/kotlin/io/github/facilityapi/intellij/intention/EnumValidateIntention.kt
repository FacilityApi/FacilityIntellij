package io.github.facilityapi.intellij.intention

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.reference.addAttribute

class EnumValidateIntention : PsiElementBaseIntentionAction(), IntentionAction {
    override fun startInWriteAction(): Boolean = true

    override fun getText() = TEXT

    override fun getFamilyName() = FAMILY_NAME

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java) ?: return false
        val attributes = serviceItem.attributeListList.flatMap(FsdAttributeList::getAttributeList)
        val isEnumSpec = serviceItem.enumSpec != null

        return isEnumSpec && attributes.none { it.attributename.textMatches("validate") }
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java) ?: return
        addAttribute(serviceItem, "validate")
    }

    companion object {
        val TEXT = FsdBundle.getMessage("intentions.validate.enum.text")
        val FAMILY_NAME = FsdBundle.getMessage("intentions.validate.enum.family")
    }
}
