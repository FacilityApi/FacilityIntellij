package io.github.facilityapi.intellij.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdFileType

// This scope could probably be narrower since FSD
// only supports single file compilation units, but
// there are some tools that can combine FSD files,
// so this might be beneficial for those edge cases
// unless it becomes prohibitively expensive for
// the typical case
fun findTypeDefinitions(project: Project, name: String): List<FsdNamedElement> {
    val types = mutableListOf<FsdNamedElement>()
    val virtualFiles = FileTypeIndex.getFiles(FsdFileType, GlobalSearchScope.allScope(project))

    for (virtualFile in virtualFiles) {
        val file = PsiManager.getInstance(project).findFile(virtualFile) as FsdFile
        val dataTypes = PsiTreeUtil.findChildrenOfType(file, FsdNamedElement::class.java).filter { it.text == name }
        types.addAll(dataTypes)
    }

    return types
}