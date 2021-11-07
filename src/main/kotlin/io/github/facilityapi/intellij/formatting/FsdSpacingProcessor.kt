package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Block
import com.intellij.formatting.Spacing
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CodeStyleSettings

class FsdSpacingProcessor(private val node: ASTNode, private val codeStyleSettings: CodeStyleSettings) {
    fun getChildSpacing(first: Block?, second: Block): Spacing {

    }
}