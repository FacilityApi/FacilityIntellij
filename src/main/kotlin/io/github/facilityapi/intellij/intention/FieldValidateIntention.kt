package io.github.facilityapi.intellij.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.reference.createFromText

class FieldValidateIntention : PsiElementBaseIntentionAction() {
    override fun startInWriteAction(): Boolean = true

    override fun getText() = TEXT

    override fun getFamilyName() = FAMILY_NAME

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val decoratedField = PsiTreeUtil.getParentOfType(element, FsdDecoratedField::class.java) ?: return false
        val attributes = decoratedField.attributeListList.flatMap(FsdAttributeList::getAttributeList)

        return attributes.none { it.attributename.text == "validate" }
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        if (editor == null) return

        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java) ?: return
        val newEnumSpec = serviceItem.copy()

        val codeStylist = CodeStyleManager.getInstance(project)
        val newLine = createFromText(project, "[dummy]\n")
            .filterIsInstance<PsiWhiteSpace>()
            .first()

        newEnumSpec.addBefore(newLine, newEnumSpec.firstChild)
        serviceItem.replace(codeStylist.reformat(newEnumSpec))

        editor.caretModel.currentCaret.moveToOffset(newEnumSpec.firstChild.textOffset)
        val template = TemplateImpl("cvalid", "Facility Service Definition")
        TemplateManager.getInstance(project).startTemplate(editor, template)
    }

    companion object {
        val TEXT = FsdBundle.getMessage("intentions.validate.field.text")
        val FAMILY_NAME = FsdBundle.getMessage("intentions.validate.field.familyname")
    }
}
