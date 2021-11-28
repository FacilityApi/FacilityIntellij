package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.*
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
        if (newChildIndex == 0) {
            return ChildAttributes(Indent.getNoneIndent(), null)
        }

        if (node.elementType is IFileElementType) {
            return ChildAttributes(Indent.getNoneIndent(), alignment)
        }

        val previousBlock = subBlocks.take(newChildIndex)
            .reversed()
            .firstOrNull { (it as ASTBlock) !is PsiWhiteSpace }

        val prevType = (previousBlock as ASTBlock?)?.node?.elementType
        if (prevType == FsdTypes.LEFT_BRACE ||
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
            child.elementType != FsdTypes.DECORATED_FIELD &&
            child.elementType != FsdTypes.ENUM_VALUE &&
            child.elementType != FsdTypes.ERROR_SPEC
    }

    companion object {
        private val DEFINITION_SPECS: Set<IElementType> = hashSetOf(
            FsdTypes.DECORATED_SERVICE_ITEM,
            FsdTypes.DATA_SPEC,
            FsdTypes.METHOD_SPEC,
            FsdTypes.ENUM_SPEC,
            FsdTypes.ERROR_SET_SPEC,
            FsdTypes.DECORATED_FIELD,
        )
    }
}
