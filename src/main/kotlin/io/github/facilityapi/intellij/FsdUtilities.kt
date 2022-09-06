package io.github.facilityapi.intellij

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.descendants
import io.github.facilityapi.intellij.psi.FsdFile
import io.github.facilityapi.intellij.psi.FsdNamedElement

fun findTypeDefinitions(project: Project, name: String): Sequence<FsdNamedElement> {
    return findTypeDefinitions(project).filter { it.name == name }
}

// This scope could probably be narrower since FSD
// only supports single file compilation units, but
// there are some tools that can combine FSD files,
// so this might be beneficial for those edge cases
// unless it becomes prohibitively expensive for
// the typical case
fun findTypeDefinitions(project: Project): Sequence<FsdNamedElement> {
    return FileTypeIndex.getFiles(FsdLanguage.associatedFileType, GlobalSearchScope.allScope(project)).asSequence()
        .map { PsiManager.getInstance(project).findFile(it) }
        .filterIsInstance<FsdFile>()
        .flatMap { it.descendants().filterIsInstance<FsdNamedElement>() }
}
