package io.github.facilityapi.intellij.inspection

import assertk.all
import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.hasSize
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.facilityapi.intellij.FsdFileType

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

        checkInspection(code, "Duplicate attribute: obsolete")
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

        checkInspection(code, "Duplicate attribute: validate")
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

        checkInspection(code, "Duplicate attribute: validate")
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

        checkInspection(code, "Duplicate attribute: required")
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

        checkInspection(code, "Duplicate attribute: obsolete")
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

        checkInspection(code, "Duplicate attribute: retired")
    }

    private fun checkInspection(code: String, errorDescription: String) {
        myFixture.configureByText(FsdFileType, code)
        myFixture.enableInspections(DuplicateAttributeInspection())
        val highlights = myFixture.doHighlighting()

        assertThat(highlights.map { it.description }, "inspection failures").all {
            hasSize(2)
            containsOnly(errorDescription)
        }
    }

    private fun checkFix(before: String, after: String) {
        myFixture.configureByText(FsdFileType, before)
        myFixture.enableInspections(DuplicateAttributeInspection())
        myFixture.doHighlighting()

        val intention = myFixture.findSingleIntention(DeleteAttributeFix.NAME)
        myFixture.launchAction(intention)

        myFixture.checkResult(after)
    }
}
