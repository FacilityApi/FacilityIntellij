package io.github.facilityapi.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FsdRenameTypeTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = "src/test/testData/rename"

    fun `test rename type from reference`() {
        myFixture.testRename("before.fsd", "after.fsd", "Rim")
    }
}
