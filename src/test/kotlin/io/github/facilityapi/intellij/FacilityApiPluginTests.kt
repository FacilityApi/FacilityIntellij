package io.github.facilityapi.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.PsiErrorElementUtil

class FacilityApiPluginTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/testData"

    fun `test parse definition`() {
        val psiFile = myFixture.configureByFile("ExampleApi.fsd")
        val fsdFile = assertInstanceOf(psiFile, FsdFile::class.java)

        assertFalse(PsiErrorElementUtil.hasErrors(project, fsdFile.virtualFile))
    }

    fun `test definition has psi elements`() {
        val psiFile = myFixture.configureByFile("ExampleApi.fsd")
        val fsdFile = assertInstanceOf(psiFile, FsdFile::class.java)

        assert(fsdFile.children.isNotEmpty())
    }
}
