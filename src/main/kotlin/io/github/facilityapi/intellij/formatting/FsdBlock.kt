package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.*
import com.intellij.formatting.templateLanguages.BlockWithParent
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.FormatterUtil
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.TokenSet
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.psi.FsdType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment), BlockWithParent {

    private var parent: BlockWithParent? = null

    override fun getIndent(): Indent = FsdIndentProcessor(codeStyleSettings).getChildIndent(node)
    override fun getSpacing(child1: Block?, child2: Block): Spacing? = null
    override fun isLeaf() = false

    override fun buildChildren(): MutableList<Block> {
        val blocks = mutableListOf<FsdBlock>()

        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType in setOf(TokenType.WHITE_SPACE)) {
                child = child.treeNext
                continue
            }

            val block = FsdBlock(
                child,
                null,
                null,
                codeStyleSettings,
            )

            block.setParent(this)
            blocks.add(block)

            child = child.treeNext
        }

        return blocks as MutableList<Block>
    }

    override fun getParent(): BlockWithParent? = parent
    override fun setParent(newParent: BlockWithParent?) {
        parent = newParent
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return super.getChildAttributes(newChildIndex) // todo: really implement this for nicer editing
    }
}