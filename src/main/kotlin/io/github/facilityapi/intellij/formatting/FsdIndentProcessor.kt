package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiFile
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdIndentProcessor {
    fun getIndent(node: ASTNode): Indent {
        if (node.treePrev?.treePrev?.elementType == FsdTypes.METHOD) {
            return Indent.getNormalIndent()
        }

        if (node.treeParent?.psi is PsiFile) {
            return Indent.getAbsoluteNoneIndent()
        }

        if (node.elementType in blocks) {
            return Indent.getNormalIndent()
        }

        return Indent.getNoneIndent()
    }

    companion object {
        val blocks = setOf(
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
