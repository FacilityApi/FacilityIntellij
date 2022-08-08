package io.github.facilityapi.intellij

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class DuplicateNameInspectionTest : BasePlatformTestCase() {

    override fun getTestDataPath() = "src/test/testData"

    fun testDuplicateRequestFieldName() {
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

    fun testDuplicateResponseFieldName() {
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

    fun testDuplicateDataFieldName() {
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

    fun testDuplicateEnumFieldName() {
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

    private fun checkInspection(code: String, errorDescription: String) {
        myFixture.configureByText(FsdFileType, code)
        myFixture.enableInspections(DuplicateNameInspection())
        val highlights = myFixture.doHighlighting()
        assertEquals(errorDescription, highlights.singleOrNull()?.description)
    }
}
