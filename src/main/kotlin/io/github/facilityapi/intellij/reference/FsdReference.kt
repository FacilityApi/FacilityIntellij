package io.github.facilityapi.intellij.reference

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiReferenceBase
import io.github.facilityapi.intellij.findTypeDefinitions
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorSetSpec
import io.github.facilityapi.intellij.psi.FsdMethodSpec

class FsdReference(element: PsiElement, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange) {

    private val identifier = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? {
        return findTypeDefinitions(myElement.project, identifier)
            .map(::PsiElementResolveResult)
            .map(PsiElementResolveResult::getElement)
            .firstOrNull()
    }

    override fun getVariants(): Array<Any> {
        return findTypeDefinitions(myElement.project).mapNotNull { type ->
            if (type.parent is FsdMethodSpec) return@mapNotNull null

            val icon = when (type.parent) {
                is FsdDataSpec -> IconLoader.getIcon("/icons/data.svg", FsdDataSpec::class.java)
                is FsdEnumSpec -> IconLoader.getIcon("/icons/enum.svg", FsdEnumSpec::class.java)
                is FsdErrorSetSpec -> IconLoader.getIcon("/icons/error-set.svg", FsdErrorSetSpec::class.java)
                else -> null
            }

            LookupElementBuilder.create(type).withIcon(icon)
        }.toList().toTypedArray()
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        val newTypeReference = createTypeReference(myElement.project, newElementName)
        myElement.parent.node.replaceChild(myElement.node, newTypeReference.node)
        return myElement
    }
}
