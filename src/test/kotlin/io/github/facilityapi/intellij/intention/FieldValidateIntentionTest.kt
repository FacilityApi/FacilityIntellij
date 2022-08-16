package io.github.facilityapi.intellij.intention

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Ignore

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

    // This test is ignored because of issues with the test framework's
    // file index exhibiting surprising behavior
    // https://app.slack.com/client/T5P9YATH9/C5U8BM1MK/thread/C5U8BM1MK-1660614540.117659
    fun `ignored test enum valued validation attribute is inserted correctly`() {
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
