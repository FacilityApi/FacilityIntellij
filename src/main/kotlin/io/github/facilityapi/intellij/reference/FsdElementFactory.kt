package io.github.facilityapi.intellij.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleManager
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdNamedElement
import io.github.facilityapi.intellij.psi.FsdReferenceType

fun createTypeDefinition(project: Project, name: String, declType: String): FsdNamedElement {
    val serviceText = """
        service dummy {
            $declType $name {
            }
        }
    """.trimIndent()

    return createFromText(project, serviceText)
        .filterIsInstance<FsdNamedElement>()
        .first()
}

fun createTypeReference(project: Project, name: String): FsdReferenceType {
    val serviceText = """
        service dummy {
            data dummy {
              field: $name;
            }
        }
    """.trimIndent()

    return createFromText(project, serviceText)
        .filterIsInstance<FsdReferenceType>()
        .first()
}

fun createAttribute(project: Project, name: String): FsdAttributeList {
    val serviceText = """
        [$name]
        service dummy {
        }
    """.trimIndent()

    return createFromText(project, serviceText)
        .filterIsInstance<FsdAttributeList>()
        .first()
}

fun createFromText(project: Project, text: String): Sequence<PsiElement> {
    val fileName = "dummy.fsd"

    // For internal parser consistency, psi elements must be created this way
    // https://plugins.jetbrains.com/docs/intellij/rename-refactoring.html
    val file = PsiFileFactory.getInstance(project)
        .createFileFromText(fileName, FsdFileType, text) as FsdFile

    return file.descendants
}

fun addAttribute(field: PsiElement, attributeName: String) {
    val newEnumSpec = field.copy()

    val codeStylist = CodeStyleManager.getInstance(field.project)
    val newLine = createFromText(field.project, "[dummy]\n")
        .filterIsInstance<PsiWhiteSpace>()
        .first()

    newEnumSpec.addBefore(newLine, newEnumSpec.firstChild)
    newEnumSpec.addBefore(createAttribute(field.project, attributeName), newEnumSpec.firstChild)

    field.replace(codeStylist.reformat(newEnumSpec))
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
