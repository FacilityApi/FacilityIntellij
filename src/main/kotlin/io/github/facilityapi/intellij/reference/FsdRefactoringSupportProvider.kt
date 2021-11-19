package io.github.facilityapi.intellij.reference

import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement
import io.github.facilityapi.intellij.psi.FsdReferenceType
import io.github.facilityapi.intellij.psi.FsdTypeIdentifier

class FsdRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(
        elementToRename: PsiElement,
        context: PsiElement?
    ) = elementToRename is FsdTypeIdentifier || elementToRename is FsdReferenceType
}
