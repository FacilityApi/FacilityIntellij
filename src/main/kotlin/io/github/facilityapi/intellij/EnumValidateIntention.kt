package io.github.facilityapi.intellij

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.descendants
import com.intellij.psi.util.elementType
import com.intellij.psi.util.siblings
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdIdentifierDeclaration
import io.github.facilityapi.intellij.psi.FsdTypes
import io.github.facilityapi.intellij.reference.createAttribute

class EnumValidateIntention : PsiElementBaseIntentionAction(),  IntentionAction {
    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Add [validate] attribute"

    override fun getFamilyName(): String = "FacilityValidation"

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val attributes = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java)?.attributeListList?.flatMap { it.attributeList }

        val isEnumSpec = element.elementType == FsdTypes.ENUM ||
            (element.parent is FsdIdentifierDeclaration &&
                element.parent.siblings(false).any { it.elementType == FsdTypes.ENUM })

        return isEnumSpec && attributes?.none { it.attributename.text == "validate" } ?: true
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        val codeStylist = CodeStyleManager.getInstance(project)
        val decoratedItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java)
        val whiteSpace = element.containingFile.descendants().first { it is PsiWhiteSpace && it.text.endsWith("\n") }

        val enumSpecNew = decoratedItem?.copy()

        val attribute = createAttribute(
            project,
            "validate"
        )

        enumSpecNew?.addBefore(whiteSpace, enumSpecNew.firstChild)
        enumSpecNew?.addBefore(attribute, enumSpecNew.firstChild)

        if (enumSpecNew != null)
            decoratedItem.replace(codeStylist.reformat(enumSpecNew))
    }
}
