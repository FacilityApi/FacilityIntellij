package io.github.facilityapi.intellij.tools

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.presentation.java.SymbolPresentationUtil
import com.intellij.psi.util.siblings
import io.github.facilityapi.intellij.psi.FsdIdentifierDeclaration

class FsdDocumentationProvider : AbstractDocumentationProvider() {
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        if (element is FsdIdentifierDeclaration) {
            return render(
                element,
                element.parent.firstChild.text, // data, method, enum, errors
                SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile()),
                findDocumentationComment(element),
            )
        }
        return super.generateDoc(element, originalElement)
    }

    private fun render(type: FsdIdentifierDeclaration, kind: String, file: String, docComment: String) = buildString {
        append(DocumentationMarkup.DEFINITION_START)
        append(kind)
        append(' ')
        append(type.identifier.text)
        append(DocumentationMarkup.DEFINITION_END)
        append(DocumentationMarkup.CONTENT_START)
        append(docComment)
        append(DocumentationMarkup.CONTENT_END)
        append(DocumentationMarkup.SECTIONS_START)
        append(DocumentationMarkup.SECTION_HEADER_START)
        append("File:")
        append(DocumentationMarkup.SECTION_SEPARATOR)
        append("<p>")
        append(file)
        append(DocumentationMarkup.SECTION_END)
        append(DocumentationMarkup.SECTIONS_END)
    }

    companion object {
        fun findDocumentationComment(type: FsdIdentifierDeclaration): String {
            return type.parent.parent.siblings(forward = false, withSelf = false)
                .takeWhile { it is PsiComment || (it is PsiWhiteSpace && it.text.count { c -> c == '\n' } <= 1) }
                .filterIsInstance<PsiComment>()
                .filter { it.text.startsWith("///") }
                .toList()
                .reversed()
                .joinToString(separator = " ") { it.text.trimStart('/', ' ', '\t') }
        }
    }
}
