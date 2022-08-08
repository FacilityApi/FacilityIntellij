package io.github.facilityapi.intellij

import com.intellij.testFramework.LightPlatformCodeInsightTestCase

class DuplicateNameInspectionTest : LightPlatformCodeInsightTestCase() {

    override fun getTestDataPath() = "src/test/testData"

    fun testDuplicateFieldName() {
        configureByFile("DuplicateField.fsd")
        enableInspectionTools(DuplicateNameInspection())
        inspection
    }
}
