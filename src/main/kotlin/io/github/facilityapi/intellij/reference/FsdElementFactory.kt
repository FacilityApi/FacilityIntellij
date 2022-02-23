package io.github.facilityapi.intellij.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.psi.FsdNamedElement
import io.github.facilityapi.intellij.psi.FsdReferenceType

fun createTypeDefinition(project: Project, name: String, declType: String): FsdNamedElement {
    val fileName = "dummy.fsd"
    // This is a silly way to do this on the surface, but the docs recommend it
    // https://plugins.jetbrains.com/docs/intellij/rename-refactoring.html
    val serviceText = """
        service dummy {
            $declType $name {
            }
        }
    """.trimIndent()
    val file = PsiFileFactory.getInstance(project).createFileFromText(fileName, FsdFileType, serviceText) as FsdFile
    return file.descendants.filterIsInstance<FsdNamedElement>().first()
}

fun createTypeReference(project: Project, name: String): FsdReferenceType {
    val fileName = "dummy.fsd"
    // This is a silly way to do this on the surface, but the docs recommend it
    // https://plugins.jetbrains.com/docs/intellij/rename-refactoring.html
    val serviceText = """
        service dummy {
            data dummy {
              field: $name;
            }
        }
    """.trimIndent()
    val file = PsiFileFactory.getInstance(project).createFileFromText(fileName, FsdFileType, serviceText) as FsdFile
    return file.descendants.filterIsInstance<FsdReferenceType>().first()
}

// These are copied because of a source breaking change in the framework
// JetBrains Platform Slack: https://app.slack.com/client/T5P9YATH9/threads
private val PsiElement.descendants: Sequence<PsiElement>
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
