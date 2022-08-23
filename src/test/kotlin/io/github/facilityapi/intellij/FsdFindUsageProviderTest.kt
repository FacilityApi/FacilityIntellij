package io.github.facilityapi.intellij

import assertk.assertThat
import assertk.assertions.hasSize
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FsdFindUsageProviderTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = "src/test/testData/findUsages"

    fun `test find usages works from type definition`() {
        val usages = myFixture.testFindUsagesUsingAction("typeDefinition.fsd")
        assertThat(usages, "usages").hasSize(2)
    }

    fun `test find usages works from type reference on arrays`() {
        val usages = myFixture.testFindUsagesUsingAction("arrays.fsd")
        assertThat(usages, "usages").hasSize(2)
    }

    fun `test find usages works from type reference`() {
        val usages = myFixture.testFindUsagesUsingAction("typeReference.fsd")
        assertThat(usages, "usages").hasSize(2)
    }
}
