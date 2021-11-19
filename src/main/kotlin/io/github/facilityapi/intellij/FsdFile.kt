package io.github.facilityapi.intellij

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class FsdFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, FsdLanguage) {
    override fun getFileType(): FileType = FsdFileType
    override fun toString(): String = "Facility Service Definition File"
}
