package io.github.facilityapi.intellij.inspection

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.facilityapi.intellij.FsdLanguage

class InvalidNullableTypeInspectionTest : BasePlatformTestCase() {
    fun `test recursive nullability detected`() {
        val code = """
        service MessageService {
            data Message
            {
                id: string;
                text: nullable<nullable<string>>;
            }
        }
        """

        checkInspection(code, listOf("Nested nullable type: nullable<string>"))
    }

    private fun checkInspection(code: String, errorDescriptions: List<String>) {
        myFixture.configureByText(FsdLanguage.associatedFileType, code)
        myFixture.enableInspections(InvalidNullableTypeInspection())
        val highlights = myFixture.doHighlighting()

        assertThat(highlights.map(HighlightInfo::getDescription), "inspection failures")
            .isEqualTo(errorDescriptions)
    }
}
