package io.github.facilityapi.intellij.inspection

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.facilityapi.intellij.FsdLanguage

class DuplicateAttributeInspectionTest : BasePlatformTestCase() {
    fun `test duplicate detected on service item`() {
        val code = """
        service MessageService {
            [obsolete, obsolete]
            enum Message
            {
                audio,
                video,
                text
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: obsolete", "Duplicate attribute: obsolete"))
    }

    fun `test duplicate detected on service item list fix`() {
        val before = """
        service MessageService {
            [obsolete, obso<caret>lete]
            enum Message
            {
                audio,
                video,
                text
            }
        }
        """

        val after = """
        service MessageService {
            [obsolete]
            enum Message
            {
                audio,
                video,
                text
            }
        }
        """

        checkFix(before, after)
    }

    fun `test duplicate detected on service item multiple list fix`() {
        val before = """
        service MessageService {
            [obso<caret>lete]
            [obsolete]
            enum Message
            {
                audio,
                video,
                text
            }
        }
        """

        val after = """
        service MessageService {
            [obsolete]
            enum Message
            {
                audio,
                video,
                text
            }
        }
        """

        checkFix(before, after)
    }

    fun `test duplicate detected on request field`() {
        val code = """
        service MessageService {
            method getMessages
            {
                [validate(value: 1..), validate(value: 10..)]
                limit: int32;
            }:
            {
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: validate", "Duplicate attribute: validate"))
    }

    fun `test fix on duplicate attributes with parameters`() {
        val before = """
        service MessageService {
            method getMessages
            {
                [validate(value: 1..), val<caret>idate(value: 10..)]
                limit: int32;
            }:
            {
            }
        }
        """

        val after = """
        service MessageService {
            method getMessages
            {
                [validate(value: 1..)]
                limit: int32;
            }:
            {
            }
        }
        """

        checkFix(before, after)
    }

    fun `test duplicate detected on response field`() {
        val code = """
        service MessageService {
            method getMessages
            {
            }:
            {
                [validate(value: 1..), validate(value: 10..)]
                count: int32;
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: validate", "Duplicate attribute: validate"))
    }

    fun `test duplicate detected on data field`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, required]
                id: string;
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: required", "Duplicate attribute: required"))
    }

    fun `test duplicate detected on enum case`() {
        val code = """
        service MessageService {
            enum MessageKind
            {
                [obsolete, obsolete]
                image,
                text,
                media
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: obsolete", "Duplicate attribute: obsolete"))
    }

    fun `test duplicate detected on error`() {
        val code = """
        service MessageService {
            errors Messages
            {
                [retired, retired]
                corrupted
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: retired", "Duplicate attribute: retired"))
    }

    fun `test duplicate required attribute detected with shorthand`() {
        val code = """
        service MessageService {
            data Message
            {
                [required]
                id: string!;
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: required"))
    }

    fun `test duplicate required attribute detected with shorthand and whitespace`() {
        val code = """
        service MessageService {
            data Message
            {
                [required]
                id: string !;
            }
        }
        """

        checkInspection(code, listOf("Duplicate attribute: required"))
    }

    private fun checkInspection(code: String, errorDescriptions: List<String>) {
        myFixture.configureByText(FsdLanguage.associatedFileType, code)
        myFixture.enableInspections(DuplicateAttributeInspection())
        val highlights = myFixture.doHighlighting()

        assertThat(highlights.map(HighlightInfo::getDescription), "inspection failures")
            .isEqualTo(errorDescriptions)
    }

    private fun checkFix(before: String, after: String) {
        myFixture.configureByText(FsdLanguage.associatedFileType, before)
        myFixture.enableInspections(DuplicateAttributeInspection())
        myFixture.doHighlighting()

        val intention = myFixture.findSingleIntention(DeleteAttributeFix.NAME)
        myFixture.launchAction(intention)

        myFixture.checkResult(after)
    }
}
