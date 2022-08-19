package io.github.facilityapi.intellij.intention

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class CombineAttributeListsIntentionTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/testData/intentions/combineAttributes"

    fun `test attribute lists are combined`() {
        myFixture.configureByFile("before.fsd")

        val action = myFixture.findSingleIntention(CombineAttributeListsIntention.TEXT)
        myFixture.launchAction(action)

        myFixture.checkResultByFile("after.fsd")
    }
}
