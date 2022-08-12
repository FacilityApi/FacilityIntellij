package io.github.facilityapi.intellij

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.reference.createAttribute
import io.github.facilityapi.intellij.reference.createFromText

class EnumValidateIntention : PsiElementBaseIntentionAction(), IntentionAction {
    override fun startInWriteAction(): Boolean = true

    override fun getText() = TEXT

    override fun getFamilyName() = FAMILY_NAME

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java) ?: return false
        val attributes = serviceItem.attributeListList.flatMap(FsdAttributeList::getAttributeList)
        val isEnumSpec = serviceItem.children.any { it is FsdEnumSpec }

        return isEnumSpec && attributes.none { it.attributename.text == "validate" }
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java) ?: return
        val newEnumSpec = serviceItem.copy()

        val codeStylist = CodeStyleManager.getInstance(project)
        val newLine = createFromText(project, "[dummy]\n")
            .filterIsInstance<PsiWhiteSpace>()
            .first()

        newEnumSpec.addBefore(newLine, newEnumSpec.firstChild)
        newEnumSpec.addBefore(createAttribute(project, "validate"), newEnumSpec.firstChild)

        serviceItem.replace(codeStylist.reformat(newEnumSpec))
    }

    companion object {
        val TEXT = FsdBundle.getMessage("intentions.validate.enum.text")
        val FAMILY_NAME = FsdBundle.getMessage("intentions.validate.enum.familyname")
    }
}
