package io.github.facilityapi.intellij.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.findDescendantOfType
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.psi.FsdNamedElement
import io.github.facilityapi.intellij.psi.FsdReferenceType

fun createTypeDefinition(project: Project, name: String): FsdNamedElement {
    val fileName = "dummy.fsd"
    // this is a dumb hack
    val serviceText = """
        service dummy {
            data $name {
            }
        }
    """.trimIndent()
    val file = PsiFileFactory.getInstance(project).createFileFromText(fileName, FsdFileType, serviceText) as FsdFile
    return file.findDescendantOfType()!!
}

fun createTypeReference(project: Project, name: String): FsdReferenceType {
    val fileName = "dummy.fsd"
    // this is a dumb hack
    val serviceText = """
        service dummy {
            data dummy {
              field: $name;
            }
        }
    """.trimIndent()
    val file = PsiFileFactory.getInstance(project).createFileFromText(fileName, FsdFileType, serviceText) as FsdFile
    return file.findDescendantOfType()!!
}