package io.github.facilityapi.intellij.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiParserFacade
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdLanguage
import io.github.facilityapi.intellij.descendants
import io.github.facilityapi.intellij.psi.FsdAttributeList
import io.github.facilityapi.intellij.psi.FsdNamedElement
import io.github.facilityapi.intellij.psi.FsdReferenceType

fun createTypeDefinition(project: Project, name: String): FsdNamedElement {
    val serviceText = """
        service dummy {
            data $name {
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

fun createAttribute(project: Project, attributeText: String): FsdAttributeList {
    val serviceText = """
        [$attributeText]
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
        .createFileFromText(fileName, FsdLanguage, text) as FsdFile

    return file.descendants
}

fun addAttribute(element: PsiElement, attributeText: String) {
    val psiFacade = PsiParserFacade.SERVICE.getInstance(element.project)
    val newline = psiFacade.createWhiteSpaceFromText("\n")

    val attribute = element.addBefore(createAttribute(element.project, attributeText), element.firstChild)
    element.addAfter(newline, attribute)
}
