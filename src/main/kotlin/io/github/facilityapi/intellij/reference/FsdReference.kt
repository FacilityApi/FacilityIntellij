package io.github.facilityapi.intellij.reference

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiReferenceBase
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.findTypeDefinitions

class FsdReference(element: PsiElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange) {

    private val identifier = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? {
        return findTypeDefinitions(myElement.project, identifier)
            .map(::PsiElementResolveResult)
            .map(PsiElementResolveResult::getElement)
            .firstOrNull()
    }

    override fun getVariants(): Array<Any> {
        return findTypeDefinitions(myElement.project).map { type ->
            LookupElementBuilder
                .create(type)
                .withIcon(FsdFileType.icon)
                .withTypeText(type.containingFile.name)
        }.toList().toTypedArray()
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        val newTypeReference = createTypeReference(myElement.project, newElementName)
        myElement.parent.node.replaceChild(myElement.node,  newTypeReference.node)
        return myElement
    }
}