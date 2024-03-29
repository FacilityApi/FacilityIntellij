package io.github.facilityapi.intellij.tools

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.psi.PsiFile
import io.github.facilityapi.intellij.psi.FsdNamedElement

class FsdStructureViewModel(
    file: PsiFile,
) : StructureViewModelBase(file, FsdStructureViewElement(file)), StructureViewModel.ElementInfoProvider {
    override fun getSorters() = arrayOf<Sorter>(Sorter.ALPHA_SORTER)
    override fun isAlwaysShowsPlus(element: StructureViewTreeElement?) = false
    override fun isAlwaysLeaf(element: StructureViewTreeElement?): Boolean = element?.value is FsdNamedElement
}
