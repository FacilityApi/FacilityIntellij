package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.*
import com.intellij.formatting.templateLanguages.BlockWithParent
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment), BlockWithParent {

    private val indent = FsdIndentProcessor(codeStyleSettings).getChildIndent(node)
    private val spacingProcessor = FsdSpacingProcessor(node, codeStyleSettings)
    private var parent: BlockWithParent? = null

    override fun getIndent(): Indent = indent

    // todo: implement for real
    override fun getSpacing(child1: Block?, child2: Block): Spacing = spacingProcessor.getChildSpacing(child1, child2)

    override fun isLeaf() = myNode.firstChildNode == null

    override fun buildChildren(): MutableList<Block> {
        val blocks = mutableListOf<Block>()

        var child = myNode.firstChildNode
        while (child != null) {
            if (child.firstChildNode.elementType == FsdTypes.LEFT_BRACE && child.lastChildNode.elementType == FsdTypes.RIGHT_BRACE) {
                val block = FsdBlock(
                    child,
                    Wrap.createWrap(WrapType.NORMAL, false),
                    Alignment.createAlignment(),
                    codeStyleSettings,
                )

                block.setParent(this)
                blocks.add(block)
            }

            child = myNode.treeNext
        }

        return blocks
    }

    override fun getParent(): BlockWithParent? = parent
    override fun setParent(newParent: BlockWithParent?) {
        parent = newParent
    }
}