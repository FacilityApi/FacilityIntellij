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
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdEnumValueList
import io.github.facilityapi.intellij.psi.FsdErrorList
import io.github.facilityapi.intellij.psi.FsdErrorSetSpec
import io.github.facilityapi.intellij.psi.FsdRequestFields
import io.github.facilityapi.intellij.psi.FsdResponseFields
import io.github.facilityapi.intellij.psi.FsdServiceSpec
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdFoldingBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        return FOLDABLES.flatMap { foldable ->
            PsiTreeUtil.findChildrenOfType(root, foldable).mapNotNull { child ->
                when (child) {
                    is FsdServiceSpec -> {
                        val serviceItems = child.serviceItems ?: return@mapNotNull null

                        val startOffset = serviceItems.textRange.startOffset + 1
                        val endOffset = serviceItems.textRange.endOffset - 1

                        createFoldingDescriptor(
                            child.node,
                            startOffset,
                            endOffset,
                            "Fsd Service Folding Group ${child.identifier}"
                        )
                    }

                    is FsdResponseFields,
                    is FsdRequestFields -> {
                        val startOffset = child.textOffset + 1
                        val endOffset = child.endOffset - 1
                        createFoldingDescriptor(child.node, startOffset, endOffset, "Fsd Method Parameter Body Group")
                    }

                    is FsdDataSpec -> {
                        val start = PsiTreeUtil.findSiblingForward(child.firstChild, FsdTypes.LEFT_BRACE, null) ?: return@mapNotNull null
                        val startOffset = start.textOffset + 1
                        val endOffset = child.endOffset - 1
                        createFoldingDescriptor(child.node, startOffset, endOffset, "Fsd Data Body Group")
                    }

                    is FsdEnumSpec -> {
                        val start = PsiTreeUtil.findChildOfType(child, FsdEnumValueList::class.java) ?: return@mapNotNull null
                        val startOffset = start.textOffset + 1
                        val endOffset = child.endOffset - 1
                        createFoldingDescriptor(child.node, startOffset, endOffset, "Fsd Enumerated Value Body Group")
                    }

                    is FsdErrorSetSpec -> {
                        val start = PsiTreeUtil.findChildOfType(child, FsdErrorList::class.java) ?: return@mapNotNull null
                        val startOffset = start.textOffset + 1
                        val endOffset = child.endOffset - 1
                        createFoldingDescriptor(child.node, startOffset, endOffset, "Fsd Error Set Body Group")
                    }

                    else -> null
                }
            }
        }.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String = "..."

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false

    private fun createFoldingDescriptor(
        node: ASTNode?,
        startOffset: Int,
        endOffset: Int,
        debugMessage: String
    ): FoldingDescriptor? {
        if (endOffset <= startOffset || node == null) return null

        val range = TextRange(startOffset, endOffset)
        val group = FoldingGroup.newGroup(debugMessage)
        return FoldingDescriptor(node, range, group)
    }

    companion object {
        val FOLDABLES = setOf(
            FsdServiceSpec::class.java,
            FsdRequestFields::class.java,
            FsdResponseFields::class.java,
            FsdDataSpec::class.java,
            FsdEnumSpec::class.java,
            FsdErrorSetSpec::class.java,
        )
    }
}
