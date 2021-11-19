package io.github.facilityapi.intellij

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

object FsdFileType : LanguageFileType(FsdLanguage) {
    override fun getIcon(): Icon = IconLoader.getIcon("/icons/fsd.svg", FsdFileType::class.java)
    override fun getName(): String = "Facility Service Definition"
    override fun getDefaultExtension(): String = "fsd"
    override fun getDescription(): String = "API specifications using the Facility Service Definition language."
    override fun getCharset(file: VirtualFile, content: ByteArray): String = "UTF-8"
}
