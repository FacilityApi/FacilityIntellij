package io.github.facilityapi.intellij.reference

import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement
import io.github.facilityapi.intellij.psi.FsdIdentifierDeclaration
import io.github.facilityapi.intellij.psi.FsdReferenceType

class FsdRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(
        elementToRename: PsiElement,
        context: PsiElement?,
    ) = elementToRename is FsdIdentifierDeclaration || elementToRename is FsdReferenceType
}
