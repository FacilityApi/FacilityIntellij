package io.github.facilityapi.intellij.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.psi.FsdDtoSpec


fun findDataTypes(project: Project, name: String): List<FsdDtoSpec> {
    // todo: this scope could probably be narrower?
    val types = mutableListOf<FsdDtoSpec>()
    val virtualFiles = FileTypeIndex.getFiles(FsdFileType, GlobalSearchScope.allScope(project))
    for (virtualFile in virtualFiles) {
        val file = PsiManager.getInstance(project).findFile(virtualFile!!) as FsdFile
        val dataTypes = PsiTreeUtil.getChildrenOfType(file, FsdDtoSpec::class.java)
        if (dataTypes != null) {
            types.addAll(dataTypes)
        }
    }
    return types
}