package io.github.facilityapi.intellij

import com.intellij.psi.formatter.FormatterTestCase

class FsdFormattingTest : FormatterTestCase() {
    override fun getTestDataPath() = "src/test/testData"
    override fun getBasePath(): String = "formatter"
    override fun getFileExtension() = FsdFileType.defaultExtension

    fun `test can correctly re-indent`() {
        doTest("indent", "indent_after")
    }
}
