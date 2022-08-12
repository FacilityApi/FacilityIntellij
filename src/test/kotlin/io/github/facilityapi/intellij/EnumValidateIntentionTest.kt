package io.github.facilityapi.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class EnumValidateIntentionTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/testData"

    fun `test enum validation attribute is inserted correctly`() {
        myFixture.configureByFile("intentions/enumvalidation/before.fsd")

        val action = myFixture.findSingleIntention("Add [validate]")
        myFixture.launchAction(action)

        myFixture.checkResultByFile("intentions/enumvalidation/after.fsd")
    }
}
