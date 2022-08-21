package io.github.facilityapi.intellij.inspection

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.facilityapi.intellij.FsdFileType
import junit.framework.TestCase

class UnusedTypeInspectionTest : BasePlatformTestCase() {
    fun `test unused data is detected`() {
        val code = """
        service MessageService {
            data Message
            {
                id: string;
            }
        }
        """

        checkInspection(
            code,
            listOf("Data \"Message\" is unused")
        )
    }

    fun `test unused enum is detected`() {
        val code = """
        service MessageService {
            enum MessageKind
            {
                text,
                image
            }
        }
        """

        checkInspection(
            code,
            listOf("Enum \"MessageKind\" is unused")
        )
    }

    fun `test used data and enum are not reported`() {
        val code = """
        service MessageService {
            method getMessages
            {
            }:
            {
                messages: Message[];
            }

            data Message {
                id: string;
                kind: MessageKind;
            }

            enum MessageKind
            {
                text,
                image
            }
        }
        """

        checkInspection(code, listOf())
    }

    fun `test unused data fix`() {
        val before = """
        service MessageService {
            // A floating comment that should not be deleted

            // Comment to delete
            data Me<caret>ssage {
                id: string;
            }
        }
        """

        val after = """
        service MessageService {
            // A floating comment that should not be deleted

        }
        """

        checkFix(before, after)
    }

    fun `test unused enum fix`() {
        val before = """
        service MessageService {
            // A floating comment that should not be deleted

            // Comment to delete
            enum Me<caret>ssageKind {
                text,
                image
            }
        }
        """

        val after = """
        service MessageService {
            // A floating comment that should not be deleted

        }
        """

        checkFix(before, after)
    }

    private fun checkInspection(code: String, errorDescriptions: List<String>) {
        myFixture.configureByText(FsdFileType, code)
        myFixture.enableInspections(UnusedTypeInspection())
        val highlights = myFixture.doHighlighting()

        assertThat(highlights.map { it.description }, "inspection failures")
            .isEqualTo(errorDescriptions)
    }

    private fun checkFix(before: String, after: String) {
        myFixture.configureByText(FsdFileType, before)
        myFixture.enableInspections(UnusedTypeInspection())
        myFixture.doHighlighting()

        val intention = myFixture.findSingleIntention(DeleteUnusedTypeFix.NAME)
        myFixture.launchAction(intention)

        myFixture.checkResult(after)
    }
}
