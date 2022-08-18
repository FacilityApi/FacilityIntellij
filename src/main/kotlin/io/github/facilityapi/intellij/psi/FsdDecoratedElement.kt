package io.github.facilityapi.intellij.psi

import com.intellij.psi.PsiElement

interface FsdDecoratedElement : PsiElement {
    val attributeListList: List<FsdAttributeList>
}
