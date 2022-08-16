package io.github.facilityapi.intellij.inspection

import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.facilityapi.intellij.FsdFileType

class DuplicateMemberInspectionTest : BasePlatformTestCase() {

    override fun getTestDataPath() = "src/test/testData"

    fun testDuplicateServiceMemberInspection() {
        val code = """
        service MessageService {
            enum Message
            {
                audio,
                video,
                text
            }

            data Message
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """

        checkInspection(code, "Duplicate service member: Message")
    }

    fun testDuplicateRequestFieldInspection() {
        val code = """
        service MessageService {
            method getMessage
            {
                offset: int32;
                limit: int32;
                limit: int32;
            }:
            {
                message: Message[];
            }

            data Message
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """

        checkInspection(code, "Duplicate request field: limit")
    }

    fun testDuplicateResponseFieldInspection() {
        val code = """
        service MessageService {
            method getMessage
            {
                offset: int32;
                limit: int32;
            }:
            {
                message: Message[];
                message: Message;
            }

            data Message
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """

        checkInspection(code, "Duplicate response field: message")
    }

    fun testDuplicateDataFieldInspection() {
        val code = """
        service MessageService {
            data Conversation
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                /// The conversation title
                title: string;

                title: string;
            }
        }
        """

        checkInspection(code, "Duplicate field: title")
    }

    fun testDuplicateEnumFieldInspection() {
        val code = """
        service MessageService {
            enum ConversationKind
            {
                person,
                group,
                group
            }
        }
        """

        checkInspection(code, "Duplicate enumerated value: group")
    }

    fun testDuplicatedErrorValue() {
        val code = """
        service MessageService {
            errors MessageError
            {
                accountBanned,
                accountBanned
            }
        }
        """

        checkInspection(code, "Duplicate error: accountBanned")
    }

    fun testDuplicateServiceMemberFix() {
        val before = """
        service MessageService {
            enum Message
            {
                audio,
                video,
                text
            }

            data Mes<caret>sage
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """.trimIndent()

        val after = """
        service MessageService {
            enum Message
            {
                audio,
                video,
                text
            }

            }
        """.trimIndent()

        checkFix(before, after)
    }

    fun testDuplicateRequestFieldFix() {
        val before = """
        service MessageService {
            method getMessage
            {
                offset: int32;
                limit: int32;
                <caret>limit: int32;
            }:
            {
                message: Message[];
            }

            data Message
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """

        val after = """
        service MessageService {
            method getMessage
            {
                offset: int32;
                limit: int32;
            }:
            {
                message: Message[];
            }

            data Message
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """

        checkFix(before, after)
    }

    fun testDuplicateResponseFieldFix() {
        val before = """
        service MessageService {
            method getMessage
            {
                offset: int32;
                limit: int32;
            }:
            {
                message: Message[];
                <caret>message: Message;
            }

            data Message
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """

        val after = """
        service MessageService {
            method getMessage
            {
                offset: int32;
                limit: int32;
            }:
            {
                message: Message[];
            }

            data Message
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                text: string;
            }
        }
        """

        checkFix(before, after)
    }

    fun testDuplicateDataFieldFix() {
        val before = """
        service MessageService {
            data Conversation
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                /// The conversation title
                title: string;

                <caret>title: string;
            }
        }
        """

        val after = """
        service MessageService {
            data Conversation
            {
                /// A unique identifier for the conversation
                [required]
                id: int64;

                /// The conversation title
                title: string;

            }
        }
        """

        checkFix(before, after)
    }

    fun testDuplicateEnumFieldFix() {
        val before = """
        service MessageService {
            enum ConversationKind
            {
                person,
                group,
                <caret>group
            }
        }
        """

        val after = """
        service MessageService {
            enum ConversationKind
            {
                person,
                group,
            }
        }
        """

        checkFix(before, after)
    }

    fun testDuplicatedErrorValueFix() {
        val before = """
        service MessageService {
            errors MessageError
            {
                accountBanned,
                <caret>accountBanned
            }
        }
        """

        val after = """
        service MessageService {
            errors MessageError
            {
                accountBanned,
            }
        }
        """

        checkFix(before, after)
    }

    private fun checkInspection(code: String, errorDescription: String) {
        myFixture.configureByText(FsdFileType, code)
        myFixture.enableInspections(DuplicateMemberInspection())
        val highlights = myFixture.doHighlighting()

        UsefulTestCase.assertSize(2, highlights)

        for (highlight in highlights) {
            assertEquals(errorDescription, highlight.description)
        }
    }

    private fun checkFix(before: String, after: String) {
        myFixture.configureByText(FsdFileType, before)
        myFixture.enableInspections(DuplicateMemberInspection())
        myFixture.doHighlighting()

        val intention = myFixture.findSingleIntention(DuplicateMemberInspection.FieldFix.NAME)
        myFixture.launchAction(intention)

        myFixture.checkResult(after)
    }
}
