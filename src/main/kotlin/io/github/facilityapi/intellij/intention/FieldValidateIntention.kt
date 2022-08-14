package io.github.facilityapi.intellij.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInsight.template.Template
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.FsdLanguage
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdField
import io.github.facilityapi.intellij.reference.addAttribute
import io.github.facilityapi.intellij.reference.createFromText

class FieldValidateIntention : PsiElementBaseIntentionAction() {
    override fun startInWriteAction(): Boolean = true

    override fun getText() = TEXT

    override fun getFamilyName() = FAMILY_NAME

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val field = PsiTreeUtil.getParentOfType(element, FsdField::class.java) ?: return false
        val decoratedField = PsiTreeUtil.getParentOfType(field, FsdDecoratedField::class.java) ?: return false
        val attributes = decoratedField.attributeListList.flatMap(FsdAttributeList::getAttributeList)

        val isEnumTyped = field.type.referenceType?.reference?.resolve()?.parent is FsdEnumSpec
        val isApplicableType = getValidateTemplateForField(field) != null || isEnumTyped

        return isApplicableType && attributes.none { it.attributename.text == "validate" }
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        if (editor == null) return

        val field = PsiTreeUtil.getParentOfType(element, FsdField::class.java) ?: return
        val decoratedField = PsiTreeUtil.getParentOfType(field, FsdDecoratedField::class.java) ?: return

        if (field.type.referenceType?.reference?.resolve()?.parent is FsdEnumSpec) {
            addAttribute(decoratedField, "validate")
            return
        }

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

        val template = getValidateTemplateForField(field)
        if (template != null) {
            templateManager.startTemplate(editor, template)
        }
    }

    private fun getValidateTemplateForField(field: FsdField): Template? {
        val type = field.type.text

        val templateKey = when {
            type == "string" -> "svalid"
            isCollection(type) -> "cvalid"
            isNumber(type) -> "nvalid"
            else -> return null
        }

        return TemplateSettings.getInstance().getTemplate(templateKey, FsdLanguage.displayName)
    }

    private fun isCollection(typeName: String) = typeName == "bytes" ||
        typeName.startsWith("map<") ||
        typeName.endsWith("[]")

    private fun isNumber(typeName: String) = typeName == "int32" || typeName == "int64"

    companion object {
        val TEXT = FsdBundle.getMessage("intentions.validate.field.text")
        val FAMILY_NAME = FsdBundle.getMessage("intentions.validate.field.familyname")
    }
}
