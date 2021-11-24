package io.github.facilityapi.intellij

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset

class FsdFoldingBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        return FOLDABLES.flatMap { foldable ->
            PsiTreeUtil.findChildrenOfType(root, foldable).mapNotNull { child ->
                when (child) {
                    is FsdServiceSpec -> {
                        FoldingDescriptor(
                            child.node,
                            TextRange(child.serviceItems.textRange.startOffset + 1, child.serviceItems.textRange.endOffset - 1),
                            FoldingGroup.newGroup("Fsd Service Folding Group ${child.identifier}")
                        )
                    }

                    is FsdMethodSpec,
                    is FsdDataSpec,
                    is FsdEnumSpec,
                    is FsdErrorSetSpec -> {
                        val start = PsiTreeUtil.findSiblingForward(child.firstChild, FsdTypes.LEFT_BRACE, null) ?: return@mapNotNull null
                        val range = TextRange(start.textOffset + 1, child.endOffset - 1)
                        val group = FoldingGroup.newGroup("Fsd Service Item Body Group")
                        FoldingDescriptor(child.node, range, group)
                    }

                    else -> null
                }
            }
        }.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String = "..."

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false

    companion object {
        val FOLDABLES = setOf(
            FsdServiceSpec::class.java,
            FsdMethodSpec::class.java,
            FsdDataSpec::class.java,
            FsdEnumSpec::class.java,
            FsdErrorSetSpec::class.java,
        )
    }
}
