package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiWhiteSpace
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdIndentProcessor {
    fun getIndent(node: ASTNode): Indent {
        if (node.elementType in BLOCKS) {
            return Indent.getNormalIndent()
        }

        // When people are typing a method definition, I suspect
        // they would type the entire request body before starting
        // the response, which means the PSI is going to be broken the
        // whole time they're typing. This clumsily works around that,
        // but still requires manually indenting fields in the request body.
        if (isRequestBlockLeft(node) || isRequestBlockRight(node)) {
            return Indent.getNormalIndent()
        }

        return Indent.getNoneIndent()
    }

    private fun isRequestBlockLeft(node: ASTNode): Boolean {
        if (node.elementType != FsdTypes.LEFT_BRACE) return false

        var firstNonWhitespaceNode: ASTNode? = node.treePrev

        while (firstNonWhitespaceNode != null && firstNonWhitespaceNode is PsiWhiteSpace) {
            firstNonWhitespaceNode = firstNonWhitespaceNode.treePrev
        }

        var secondNonWhitespaceASTNode: ASTNode? = firstNonWhitespaceNode?.treePrev
        while (secondNonWhitespaceASTNode != null && secondNonWhitespaceASTNode is PsiWhiteSpace) {
            secondNonWhitespaceASTNode = secondNonWhitespaceASTNode.treePrev
        }

        return firstNonWhitespaceNode?.elementType == FsdTypes.IDENTIFIER &&
            secondNonWhitespaceASTNode?.elementType == FsdTypes.METHOD
    }

    private fun isRequestBlockRight(node: ASTNode): Boolean {
        if (node.elementType != FsdTypes.RIGHT_BRACE) return false

        var firstNonWhitespaceNode: ASTNode? = node.treePrev

        while (firstNonWhitespaceNode != null && firstNonWhitespaceNode is PsiWhiteSpace) {
            firstNonWhitespaceNode = firstNonWhitespaceNode.treePrev
        }

        return firstNonWhitespaceNode != null && isRequestBlockLeft(firstNonWhitespaceNode)
    }

    companion object {
        val BLOCKS = setOf(
            FsdTypes.COMMENT,
            FsdTypes.ATTRIBUTE_LIST,
            FsdTypes.METHOD_SPEC,
            FsdTypes.DATA_SPEC,
            FsdTypes.ENUM_SPEC,
            FsdTypes.ENUM_VALUE,
            FsdTypes.ERROR_SET_SPEC,
            FsdTypes.ERROR_SPEC,
            FsdTypes.FIELD,
        )
    }
}
