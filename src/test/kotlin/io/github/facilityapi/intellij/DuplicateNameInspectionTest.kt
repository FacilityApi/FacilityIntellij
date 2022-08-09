package io.github.facilityapi.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class DuplicateNameInspectionTest : BasePlatformTestCase() {

    override fun getTestDataPath() = "src/test/testData"

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
        myFixture.enableInspections(DuplicateNameInspection())
        val highlights = myFixture.doHighlighting()
        assertEquals(errorDescription, highlights.singleOrNull()?.description)
    }

    private fun checkFix(before: String, after: String) {
        myFixture.configureByText(FsdFileType, before)
        myFixture.enableInspections(DuplicateNameInspection())
        myFixture.doHighlighting()

        val intention = myFixture.findSingleIntention(DuplicateNameInspection.Fix.NAME)
        myFixture.launchAction(intention)

        myFixture.checkResult(after)
    }
}
