package io.github.facilityapi.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FsdFoldingTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = "src/test/testData"

    fun `test successful folding`() {
        myFixture.testFolding("$testDataPath/folding/folding.fsd")
    }
}
