package io.github.facilityapi.intellij.inspection

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.facilityapi.intellij.FsdLanguage

class FsdInspectionSuppressorTest : BasePlatformTestCase() {

    fun `test single inspection is suppressed`() {
        val code = """
        service MessageService {
            //noinspection UnusedType
            data Message {
                id: string;
            }
        }
        """

        checkInspection(code, listOf())
    }

    fun `test multiple inspection are suppressed`() {
        val code = """
        service MessageService {
            //noinspection all
            [validate]
            data Message {
                id: string;
            }
        }
        """

        checkInspection(code, listOf())
    }

    fun `test suppression above other adjacent comments`() {
        val code = """
        service MessageService {
            //noinspection all
            /// This is a message
            [validate]
            data Message {
                id: string;
            }
        }
        """

        checkInspection(code, listOf())
    }

    fun `test non-adjacent suppression is ignored`() {
        val code = """
        service MessageService {
            //noinspection all

            /// This is a message
            data Message {
                id: string;
            }
        }
        """

        checkInspection(code, listOf("Data \"Message\" is unused"))
    }

    private fun checkInspection(code: String, errorDescriptions: List<String>) {
        myFixture.configureByText(FsdLanguage.associatedFileType, code)
        myFixture.enableInspections(UnusedTypeInspection(), ValidateAttributeInspection())
        val highlights = myFixture.doHighlighting()

        assertThat(highlights.map { it.description }, "inspection failures")
            .isEqualTo(errorDescriptions)
    }
}
