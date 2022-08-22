package io.github.facilityapi.intellij

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType

object FsdLanguage : Language("FSD", "text/fsd") {
    override fun getAssociatedFileType(): LanguageFileType {
        return super.getAssociatedFileType()!!
    }

    override fun isCaseSensitive(): Boolean = true
    override fun getDisplayName(): String = "Facility Service Definition"
}
