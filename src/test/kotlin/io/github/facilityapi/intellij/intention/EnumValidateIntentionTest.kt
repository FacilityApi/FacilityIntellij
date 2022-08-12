package io.github.facilityapi.intellij.intention

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class EnumValidateIntentionTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/testData"

    fun `test enum validation attribute is inserted correctly`() {
        myFixture.configureByFile("intentions/enumvalidation/before.fsd")

        val action = myFixture.findSingleIntention(EnumValidateIntention.TEXT)
        myFixture.launchAction(action)

        myFixture.checkResultByFile("intentions/enumvalidation/after.fsd")
    }
}
