package io.github.facilityapi.intellij.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInsight.template.Template
import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateEditorUtil
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.FsdLanguage
import io.github.facilityapi.intellij.FsdLiveTemplateContext
import io.github.facilityapi.intellij.lexer.FsdLexerAdapter
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdField
import io.github.facilityapi.intellij.reference.createFromText

class FieldValidateIntention : PsiElementBaseIntentionAction() {
    override fun startInWriteAction(): Boolean = true

    override fun getText() = TEXT

    override fun getFamilyName() = FAMILY_NAME

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val field = PsiTreeUtil.getParentOfType(element, FsdField::class.java) ?: return false
        val decoratedField = PsiTreeUtil.getParentOfType(field, FsdDecoratedField::class.java) ?: return false
        val attributes = decoratedField.attributeListList.flatMap(FsdAttributeList::getAttributeList)

        val type = field.type.text
        val isApplicableType = type == "string" ||
            type == "bytes" ||
            type == "int32" ||
            type == "int64" ||
            type.endsWith("[]") ||
            type.startsWith("map<") ||
            // this suggests there may be a better parse tree structure here
            field.type.referenceType?.reference?.resolve()?.parent is FsdEnumSpec

        return isApplicableType && attributes.none { it.attributename.text == "validate" }
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        if (editor == null) return

        val field = PsiTreeUtil.getParentOfType(element, FsdField::class.java) ?: return
        val decoratedField = PsiTreeUtil.getParentOfType(field, FsdDecoratedField::class.java) ?: return

        val codeStylist = CodeStyleManager.getInstance(project)
        val templateManager = TemplateManager.getInstance(project)
        val newline = createFromText(project, "[dummy]\n")
            .filterIsInstance<PsiWhiteSpace>()
            .first()

        val newField = decoratedField.copy()
        val firstNewline = newField.addBefore(newline, field)
        newField.addBefore(newline, firstNewline)
        val addedAfter = newField.addAfter(newline, field)
        newField.addAfter(newline, addedAfter)

        editor.caretModel.moveToOffset(firstNewline.textOffset)

        decoratedField.replace(codeStylist.reformat(newField))

        val template = TemplateSettings.getInstance().getTemplate("cvalid", FsdLanguage.displayName)!!
        templateManager.startTemplate(editor, template)
    }

    companion object {
        val TEXT = FsdBundle.getMessage("intentions.validate.field.text")
        val FAMILY_NAME = FsdBundle.getMessage("intentions.validate.field.familyname")
        val NUMBER_TYPES: Set<String> = hashSetOf("int32", "int64") // todo: flesh out / double check all number apply
    }
}
