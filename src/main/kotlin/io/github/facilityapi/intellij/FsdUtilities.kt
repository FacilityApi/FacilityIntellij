package io.github.facilityapi.intellij

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
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
        .flatMap { it.descendants.filterIsInstance<FsdNamedElement>() }
}

// These are copied because of a source breaking change in the framework
// JetBrains Platform Slack: https://app.slack.com/client/T5P9YATH9/threads
// Eventually, they should be replaced with PsiElement.descendents() in psiTreeUtil.kt
val PsiElement.descendants: Sequence<PsiElement>
    get() = sequence {
        val root = this@descendants
        visitChildrenAndYield(root)
    }

private suspend fun SequenceScope<PsiElement>.visitChildrenAndYield(element: PsiElement) {
    var child = element.firstChild

    while (child != null) {
        visitChildrenAndYield(child)
        child = child.nextSibling
    }

    yield(element)
}
