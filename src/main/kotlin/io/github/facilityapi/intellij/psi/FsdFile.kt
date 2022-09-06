package io.github.facilityapi.intellij.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import io.github.facilityapi.intellij.FsdLanguage

class FsdFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, FsdLanguage) {
    override fun getFileType(): FileType = viewProvider.fileType
    override fun toString(): String = "Facility Service Definition File"
}
