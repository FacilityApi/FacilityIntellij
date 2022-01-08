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
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment), BlockWithParent {

    private var parent: BlockWithParent? = null

    private val _indent: Indent = FsdIndentProcessor().getIndent(node)

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
                    if (needsParentAlignment(child)) alignment else Alignment.createAlignment(),
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
        val reversedBlocks = subBlocks.take(newChildIndex).reversed()
        val previousBlock = reversedBlocks.firstOrNull { (it as ASTBlock) !is PsiWhiteSpace }
        val prevType = (previousBlock as ASTBlock?)?.node?.elementType

        // When parser is in a degenerate state, some
        // alignment needs to happen as if it weren't
        if (node.elementType is IFileElementType) {
            return if (reversedBlocks.any { (it as ASTBlock).node?.elementType == FsdTypes.LEFT_BRACE }) {
                val f = if (previousBlock?.node?.elementType == FsdTypes.RIGHT_BRACKET) {
                    reversedBlocks.firstOrNull { (it as ASTBlock).node?.elementType == FsdTypes.LEFT_BRACKET }
                } else null
                ChildAttributes(Indent.getNormalIndent(), f?.alignment)
            } else {
                ChildAttributes(Indent.getNoneIndent(), alignment)
            }
        }

        if (prevType == FsdTypes.LEFT_BRACE ||
            prevType == FsdTypes.COMMENT ||
            prevType == FsdTypes.COMMA ||
            prevType == FsdTypes.DECORATED_SERVICE_ITEM ||
            prevType == FsdTypes.DECORATED_FIELD ||
            prevType == FsdTypes.DECORATED_ENUM_VALUE ||
            prevType == FsdTypes.DECORATED_ERROR_SPEC
        ) {
            return ChildAttributes(Indent.getNormalIndent(), null)
        }

        return ChildAttributes(previousBlock?.indent, previousBlock?.alignment)
    }

    private fun needsParentAlignment(child: ASTNode): Boolean {
        return DEFINITION_SPECS.contains(node.elementType) &&
            child.elementType != FsdTypes.COMMENT &&
            child.elementType != FsdTypes.DECORATED_FIELD &&
            child.elementType != FsdTypes.DECORATED_ENUM_VALUE &&
            child.elementType != FsdTypes.DECORATED_ERROR_SPEC
    }

    companion object {
        private val DEFINITION_SPECS: Set<IElementType> = hashSetOf(
            FsdTypes.SERVICE_SPEC,
            FsdTypes.DECORATED_SERVICE_ITEM,
            FsdTypes.DATA_SPEC,
            FsdTypes.METHOD_SPEC,
            FsdTypes.ENUM_SPEC,
            FsdTypes.ERROR_SET_SPEC,
            FsdTypes.DECORATED_FIELD,
            FsdTypes.DECORATED_ENUM_VALUE,
            FsdTypes.DECORATED_ERROR_SPEC,
        )
    }
}
