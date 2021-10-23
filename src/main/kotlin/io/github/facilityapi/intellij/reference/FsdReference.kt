package io.github.facilityapi.intellij.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiReferenceBase

class FsdReference(element: PsiElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange) {

    private val identifier = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? {
        val dataTypes = findTypeDefinitions(myElement.project, identifier)
        val resolveResults = dataTypes.map(::PsiElementResolveResult).toTypedArray()
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        val dataType = createTypeReference(myElement.project, newElementName)
        val newNode = dataType.node
        myElement.node.replaceChild(myElement.node, newNode)
        return myElement
    }
}