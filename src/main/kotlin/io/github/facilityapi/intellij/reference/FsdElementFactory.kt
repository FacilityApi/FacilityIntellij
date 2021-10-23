package io.github.facilityapi.intellij.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.psi.FsdDtoSpec


fun createDataType(project: Project, name: String): FsdDtoSpec = createFile(project, name).firstChild as FsdDtoSpec

fun createFile(project: Project, text: String): FsdFile {
    val name = "dummy.fsd"
    return PsiFileFactory.getInstance(project).createFileFromText(name, FsdFileType, text) as FsdFile
}