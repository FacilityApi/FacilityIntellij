package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.ASTBlock
import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.ChildAttributes
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.formatting.Wrap
import com.intellij.formatting.templateLanguages.BlockWithParent
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.IElementType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment), BlockWithParent {

    private val _indent: Indent = FsdIndentProcessor().getIndent(node)
    private var parent: BlockWithParent? = null

    override fun getIndent(): Indent = _indent
    override fun getSpacing(child1: Block?, child2: Block): Spacing? = null
    override fun isLeaf() = myNode.firstChildNode == null

    override fun buildChildren(): MutableList<Block> {
        val blocks = mutableListOf<FsdBlock>()

        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                val block = FsdBlock(
                    child,
                    null,
                    if (DEFINITION_SPECS.contains(node.elementType)) alignment else Alignment.createAlignment(),
                    codeStyleSettings,
                )

                block.setParent(this)
                blocks.add(block)
            }

            child = child.treeNext
        }

        @Suppress("UNCHECKED_CAST")
        return blocks as MutableList<Block>
    }

    override fun getParent(): BlockWithParent? = parent
    override fun setParent(newParent: BlockWithParent?) {
        parent = newParent
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return ChildAttributes(Indent.getNormalIndent(), null)
//        if (newChildIndex == 0) {
//            return ChildAttributes(Indent.getNoneIndent(), null)
//        }
//
//        val blocks = subBlocks.take(newChildIndex)
//            .filterIsInstance<ASTBlock>()
//            .filter { it !is PsiWhiteSpace }
//            .reversed()
//
//        val previousBlock = blocks.getOrNull(0)
//        val secondPreviousBlock = blocks.getOrNull(1)
//        val thirdPreviousBlock = blocks.getOrNull(2)
//
//        val prevType = previousBlock?.node?.elementType
//        val secondPrevType = secondPreviousBlock?.node?.elementType
//        val thirdPrevType = thirdPreviousBlock?.node?.elementType
//

    }

    companion object {
        private val DEFINITION_KEYWORDS: Set<IElementType> = hashSetOf(
            FsdTypes.SERVICE,
            FsdTypes.DATA,
            FsdTypes.METHOD,
            FsdTypes.ENUM,
            FsdTypes.ERRORS,
        )

        private val DEFINITION_SPECS: Set<IElementType> = hashSetOf(
            FsdTypes.DATA_SPEC,
            FsdTypes.METHOD_SPEC,
            FsdTypes.ENUM_SPEC,
            FsdTypes.ERROR_SET_SPEC,
        )
    }
}
