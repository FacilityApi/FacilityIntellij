package io.github.facilityapi.intellij.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import io.github.facilityapi.intellij.psi.FsdDtoSpec


class FsdReference(element: PsiElement, textRange: TextRange)
    : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    private val identifier = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val dataTypes: List<FsdDtoSpec> = findDataTypes(myElement.project, identifier)
        return dataTypes.map(::PsiElementResolveResult).toTypedArray()
    }
}