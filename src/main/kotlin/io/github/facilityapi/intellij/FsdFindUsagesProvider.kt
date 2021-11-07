package io.github.facilityapi.intellij

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import io.github.facilityapi.intellij.lexer.FsdLexerAdapter
import io.github.facilityapi.intellij.psi.*

class FsdFindUsagesProvider : FindUsagesProvider {
  override fun getHelpId(psiElement: PsiElement): String? = null

  override fun getWordsScanner(): WordsScanner = DefaultWordsScanner(
    FsdLexerAdapter(),
    TokenSet.create(FsdTypes.TYPE_IDENTIFIER),
    TokenSet.create(FsdTypes.COMMENT),
    TokenSet.EMPTY
  )

  override fun canFindUsagesFor(psiElement: PsiElement) = psiElement is PsiNamedElement

  override fun getType(element: PsiElement) = when (element.parent) {
    is FsdDataSpec -> "data"
    is FsdEnumSpec -> "enum"
    is FsdErrorSetSpec -> "error set"
    else -> "type"
  }

  override fun getDescriptiveName(element: PsiElement) = when (element) {
    is FsdTypeIdentifier -> element.name
    else -> ""
  }

  override fun getNodeText(element: PsiElement, useFullName: Boolean): String = when (element) {
    is FsdTypeIdentifier -> element.text
    else -> ""
  }
}