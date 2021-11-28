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

        return Indent.getNoneIndent()
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
