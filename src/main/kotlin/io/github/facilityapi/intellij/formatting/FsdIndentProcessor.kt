package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.psi.FsdType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdIndentProcessor(private val codeStyleSettings: CodeStyleSettings) {
    fun getIndent(node: ASTNode): Indent {
        val psi = node.psi
        val parent = node.treeParent

        val parentType = parent?.elementType

//        if (parentType in blocks && node.elementType in setOf(FsdTypes.LEFT_BRACE, FsdTypes.RIGHT_BRACE)) {
//            return Indent.getNoneIndent()
//        }

        if (parent?.psi is PsiFile) {
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