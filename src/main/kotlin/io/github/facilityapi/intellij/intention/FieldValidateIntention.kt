package io.github.facilityapi.intellij.intention

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.codeInsight.template.Template
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.siblings
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
        val decoratedField = PsiTreeUtil.getParentOfType(element, FsdDecoratedField::class.java) ?: return false

        val field = decoratedField.field
        val attributes = decoratedField.attributeListList.flatMap(FsdAttributeList::getAttributeList)

        val isEnumTyped = field.type.referenceType?.reference?.resolve()?.parent is FsdEnumSpec
        val isApplicableType = getValidateTemplateForField(field) != null || isEnumTyped

        return isApplicableType && attributes.none { it.attributename.text == "validate" }
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        if (editor == null) return

        val decoratedField = PsiTreeUtil.getParentOfType(element, FsdDecoratedField::class.java) ?: return
        val field = decoratedField.field

        if (field.type.referenceType?.reference?.resolve()?.parent is FsdEnumSpec) {
            addAttribute(decoratedField, "validate")
            return
        }

        val templateManager = TemplateManager.getInstance(project)
        val previousNewline = (
            decoratedField.siblings(false, false)
                .first() as? PsiWhiteSpace
            )?.let { if (it.textContains('\n')) it else null }

        val newFieldParent = decoratedField.parent.copy()
        var offset = decoratedField.textOffset

        if (previousNewline != null) {
            val newDecoratedField = newFieldParent.children.find { it.textMatches(decoratedField) }

            val indent = previousNewline.text.trimStart('\r', '\n')
            val newWhitespace = createFromText(
                project,
                """[dummy]
                |${previousNewline.text}service Test {}
                """.trimMargin()
            ).filterIsInstance<PsiWhiteSpace>().first()

            newFieldParent.addBefore(newWhitespace, newDecoratedField)

            offset -= indent.length
        }

        decoratedField.parent.replace(newFieldParent)

        editor.caretModel.moveToOffset(offset)

        val template = getValidateTemplateForField(field)
        if (template != null) {
            PsiDocumentManager.getInstance(project)
                .doPostponedOperationsAndUnblockDocument(editor.document)

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
