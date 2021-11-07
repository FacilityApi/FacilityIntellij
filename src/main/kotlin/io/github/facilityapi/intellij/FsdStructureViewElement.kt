package io.github.facilityapi.intellij

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.impl.FsdDataSpecImpl


class FsdStructureViewElement(
    private val element: NavigatablePsiElement,
) : StructureViewTreeElement, SortableTreeElement {

    override fun getAlphaSortKey() = element.name ?: ""
    override fun getValue(): Any = element
    override fun getPresentation(): ItemPresentation = element.presentation!! // todo: robustness
    override fun navigate(requestFocus: Boolean) = element.navigate(requestFocus)
    override fun canNavigate() = element.canNavigate()
    override fun canNavigateToSource() = element.canNavigateToSource()

    override fun getChildren(): Array<TreeElement> {
        if (element is FsdFile) {
            val properties: List<FsdDataSpec> = PsiTreeUtil.getChildrenOfTypeAsList(
                element,
                FsdDataSpec::class.java
            )
            val treeElements: MutableList<TreeElement> = ArrayList(properties.size)
            for (property in properties) {
                treeElements.add(FsdStructureViewElement(property as FsdDataSpecImpl))
            }
            return treeElements.toTypedArray()
        }

        return emptyArray()
    }
}
