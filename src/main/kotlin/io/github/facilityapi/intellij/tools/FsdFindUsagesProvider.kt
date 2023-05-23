package io.github.facilityapi.intellij.tools

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import io.github.facilityapi.intellij.lexer.FsdLexerAdapter
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorSetSpec
import io.github.facilityapi.intellij.psi.FsdIdentifierDeclaration
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdFindUsagesProvider : FindUsagesProvider {
    override fun getHelpId(psiElement: PsiElement): String? = null

    override fun getWordsScanner(): WordsScanner = DefaultWordsScanner(
        FsdLexerAdapter(),
        TokenSet.create(FsdTypes.IDENTIFIER_DECLARATION),
        TokenSet.create(FsdTypes.COMMENT),
        TokenSet.EMPTY,
    )

    override fun canFindUsagesFor(psiElement: PsiElement) = psiElement is PsiNamedElement

    override fun getType(element: PsiElement) = when (element.parent) {
        is FsdDataSpec -> "data"
        is FsdEnumSpec -> "enum"
        is FsdErrorSetSpec -> "error set"
        else -> "type"
    }

    override fun getDescriptiveName(element: PsiElement) = (element as? FsdIdentifierDeclaration)?.name ?: ""

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String = when (element) {
        is FsdIdentifierDeclaration -> element.text
        else -> ""
    }
}
