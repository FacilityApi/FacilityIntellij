package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings
import io.github.facilityapi.intellij.psi.FsdType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdIndentProcessor(private val codeStyleSettings: CodeStyleSettings) {
    fun getChildIndent(node: ASTNode): Indent = when(node.elementType) {
        FsdTypes.LEFT_BRACE,
        FsdTypes.RIGHT_BRACE -> Indent.getNoneIndent()

        FsdTypes.ATTRIBUTE_LIST,
        FsdTypes.SERVICE_ITEMS,
        FsdTypes.METHOD_SPEC,
        FsdTypes.DATA_SPEC,
        FsdTypes.ENUM_SPEC,
        FsdTypes.ERROR_SET_SPEC,
        FsdTypes.ENUM_SPEC -> Indent.getNormalIndent(true)
        else -> Indent.getNoneIndent()
    }
}