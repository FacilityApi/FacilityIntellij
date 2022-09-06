package io.github.facilityapi.intellij

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.prop
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.PsiErrorElementUtil
import io.github.facilityapi.intellij.psi.FsdFile

class FsdParseTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/testData"

    fun `test parse definition`() {
        val psiFile = myFixture.configureByFile("ExampleApi.fsd")
        val fsdFile = assertInstanceOf(psiFile, FsdFile::class.java)

        assertThat(PsiErrorElementUtil.hasErrors(project, fsdFile.virtualFile), "has parsing errors")
            .isFalse()
    }

    fun `test definition has psi elements`() {
        val psiFile = myFixture.configureByFile("ExampleApi.fsd")

        assertThat(psiFile).isInstanceOf(FsdFile::class.java)
            .prop(FsdFile::getChildren)
            .isNotEmpty()
    }
}
