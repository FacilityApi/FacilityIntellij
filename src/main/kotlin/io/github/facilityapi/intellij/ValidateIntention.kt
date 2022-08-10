package io.github.facilityapi.intellij

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.descendants
import com.intellij.psi.util.elementType
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdDecoratedEnumValue
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdElementType
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdIdentifierDeclaration
import io.github.facilityapi.intellij.psi.FsdTypes
import io.github.facilityapi.intellij.reference.createAttribute

class ValidateIntention : PsiElementBaseIntentionAction(),  IntentionAction {
    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Add [validate] attribute"

    override fun getFamilyName(): String = "FacilityValidation"

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        // todo: also show intention on decl keyword
        return element.elementType == FsdTypes.IDENTIFIER
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        val codeStylist = CodeStyleManager.getInstance(project)
        val decoratedItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java)
        val whiteSpace = element.containingFile.descendants().first { it.text == "\n" }

        val enumSpecNew = decoratedItem?.copy()

        val attribute = createAttribute(
            project,
            "validate"
        )

        enumSpecNew?.addBefore(whiteSpace, enumSpecNew.firstChild)
        enumSpecNew?.addBefore(attribute, enumSpecNew.firstChild)

        if (enumSpecNew != null)
            decoratedItem?.replace(codeStylist.reformat(enumSpecNew))
    }
}
