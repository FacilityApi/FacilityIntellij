package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.impl.source.tree.FileElement
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdIndentProcessor {
    fun getIndent(node: ASTNode): Indent {
        if (node.treeParent is FileElement && (node.elementType == FsdTypes.COMMENT || node.elementType == FsdTypes.ATTRIBUTE_LIST)) {
            return Indent.getNoneIndent()
        }

        if (node.elementType in BLOCKS) {
            return Indent.getNormalIndent()
        }

        return Indent.getNoneIndent()
    }

    companion object {
        val BLOCKS = setOf(
            FsdTypes.COMMENT,
            FsdTypes.DECORATED_SERVICE_ITEM,
            FsdTypes.ENUM_VALUE,
            FsdTypes.ERROR_SPEC,
            FsdTypes.DECORATED_FIELD,
        )
    }
}
