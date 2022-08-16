package io.github.facilityapi.intellij.intention

import com.intellij.testFramework.fixtures.BasePlatformTestCase

// Note: These tests don't complete the template.
// That would probably be better, but doesn't seem
// to be super easy in the test harness.
class FieldValidateIntentionTest : BasePlatformTestCase() {
    override fun getTestDataPath() = "src/test/testData"

    fun `test number validation attribute is inserted correctly`() {
        checkBeforeAfter("intBefore.fsd", "intAfter.fsd")
    }

    fun `test string validation attribute is inserted correctly`() {
        checkBeforeAfter("stringBefore.fsd", "stringAfter.fsd")
    }

    fun `test collection validation attribute is inserted correctly`() {
        checkBeforeAfter("collectionBefore.fsd", "collectionAfter.fsd")
    }

    fun `test enum valued validation attribute is inserted correctly`() {
        checkBeforeAfter("enumValuedBefore.fsd", "enumValuedAfter.fsd")
    }

    fun `test weird spacing validation attribute is inserted correctly`() {
        checkBeforeAfter("weirdSpacingBefore.fsd", "weirdSpacingAfter.fsd")
    }

    fun `test comment above validation attribute is inserted correctly`() {
        checkBeforeAfter("commentAboveBefore.fsd", "commentAboveAfter.fsd")
    }

    private fun checkBeforeAfter(before: String, after: String) {
        myFixture.configureByFile("intentions/fieldValidation/$before")

        val action = myFixture.findSingleIntention(FieldValidateIntention.TEXT)
        myFixture.launchAction(action)

        myFixture.checkResultByFile("intentions/fieldValidation/$after")
    }
}
