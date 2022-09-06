package io.github.facilityapi.intellij.tools

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import io.github.facilityapi.intellij.findTypeDefinitions
import io.github.facilityapi.intellij.psi.FsdFile
import io.github.facilityapi.intellij.psi.FsdNamedElementImpl

class FsdStructureViewElement(
    private val element: NavigatablePsiElement,
) : StructureViewTreeElement, SortableTreeElement {

    override fun getAlphaSortKey() = element.name ?: ""
    override fun getValue(): Any = element
    override fun getPresentation(): ItemPresentation = element.presentation!!
    override fun navigate(requestFocus: Boolean) = element.navigate(requestFocus)
    override fun canNavigate() = element.canNavigate()
    override fun canNavigateToSource() = element.canNavigateToSource()

    override fun getChildren(): Array<TreeElement> {
        if (element !is FsdFile) return emptyArray()

        return findTypeDefinitions(element.project)
            .filterIsInstance<FsdNamedElementImpl>()
            .map(::FsdStructureViewElement)
            .toList()
            .toTypedArray()
    }
}
