package io.github.facilityapi.intellij

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.descendants
import com.intellij.psi.util.elementType
import com.intellij.psi.util.siblings
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdIdentifierDeclaration
import io.github.facilityapi.intellij.psi.FsdTypes
import io.github.facilityapi.intellij.reference.createAttribute

class EnumValidateIntention : PsiElementBaseIntentionAction(),  IntentionAction {
    override fun startInWriteAction(): Boolean = true

    override fun getText() = FsdBundle.getMessage("intentions.validate.enum.text")

    override fun getFamilyName() = FsdBundle.getMessage("intentions.validate.enum.familyname")

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
        val whiteSpace = element.containingFile.descendants()
            .filterIsInstance<PsiWhiteSpace>()
            .first { it.text.endsWith("\n") }

        newEnumSpec.addBefore(whiteSpace, newEnumSpec.firstChild)
        newEnumSpec.addBefore(createAttribute(project, "validate"), newEnumSpec.firstChild)

        serviceItem.replace(codeStylist.reformat(newEnumSpec))
    }
}
