package io.github.facilityapi.intellij

import com.intellij.codeInspection.ex.InspectListener
import com.intellij.codeInspection.ex.InspectionToolWrapper
import com.intellij.codeInspection.ex.reportWhenInspectionFinished
import com.intellij.openapi.project.Project
import com.intellij.testFramework.LightPlatformCodeInsightTestCase

class DuplicateNameInspectionTest : LightPlatformCodeInsightTestCase() {

    override fun getTestDataPath() = "src/test/testData"

    fun testDuplicateFieldName() {
        configureByFile("DuplicateField.fsd")
        enableInspectionTools(DuplicateNameInspection())
        inspection
    }
}
