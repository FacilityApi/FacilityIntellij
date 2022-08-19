package io.github.facilityapi.intellij.intention

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class SplitAttributeListIntentionTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/testData/intentions/splitAttributes"

    fun `test attribute lists are combined`() {
        myFixture.configureByFile("before.fsd")

        val action = myFixture.findSingleIntention(SplitAttributeListIntention.TEXT)
        myFixture.launchAction(action)

        myFixture.checkResultByFile("after.fsd")
    }
}
