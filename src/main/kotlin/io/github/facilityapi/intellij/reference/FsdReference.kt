package io.github.facilityapi.intellij.reference

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import io.github.facilityapi.intellij.FsdFileType

class FsdReference(element: PsiElement, textRange: TextRange)
    : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    private val identifier = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val dataTypes = findTypeDefinitions(myElement.project, identifier)
        return dataTypes.map(::PsiElementResolveResult).toTypedArray()
    }

    override fun getVariants(): Array<LookupElement> {
        return findTypeDefinitions(myElement.project).map { typeDefinition ->
            LookupElementBuilder.create(typeDefinition)
                .withIcon(FsdFileType.icon)
                .withTypeText(typeDefinition.containingFile.name)
        }.toTypedArray()
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        val dataType = createTypeReference(myElement.project, newElementName)
        val newNode = dataType.node
        myElement.node.replaceChild(myElement.node, newNode)
        return myElement
    }
}