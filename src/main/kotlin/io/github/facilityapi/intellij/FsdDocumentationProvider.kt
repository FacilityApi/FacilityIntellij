package io.github.facilityapi.intellij

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.presentation.java.SymbolPresentationUtil
import io.github.facilityapi.intellij.psi.FsdTypeIdentifier

class FsdDocumentationProvider : AbstractDocumentationProvider() {
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        if (element is FsdTypeIdentifier) {
            return render(
                element,
                element.parent.firstChild.text, // data, method, enum, errors
                SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile()),
                findDocumentationComment(element),
            )
        }
        return super.generateDoc(element, originalElement)
    }

    private fun render(type: FsdTypeIdentifier, kind: String, file: String, docComment: String) = buildString {
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
        fun findDocumentationComment(type: FsdTypeIdentifier): String {
            val result = mutableListOf<String>()
            var element = type.parent.parent.prevSibling
            while (element is PsiComment || element is PsiWhiteSpace) {
                if (element is PsiComment && element.text.startsWith("///")) {
                    val commentText = element.text.trimStart('/')
                    result.add(commentText)
                }
                element = element.prevSibling
            }

            return result.asReversed().joinToString()
        }
    }
}
