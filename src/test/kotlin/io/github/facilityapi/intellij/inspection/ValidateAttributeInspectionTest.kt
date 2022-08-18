package io.github.facilityapi.intellij.inspection

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.facilityapi.intellij.FsdFileType

class ValidateAttributeInspectionTest : BasePlatformTestCase() {
    fun `test invalid string parameters`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(count: 10)]
                id: string;
            }
        }
        """

        checkInspection(
            code,
            listOf(
                "Missing validate parameters: regex, length",
                "Invalid validate parameter \"count\" for type string"
            )
        )
    }

    fun `test invalid int parameters`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(count: 10)]
                id: int32;
            }
        }
        """

        checkInspection(
            code,
            listOf(
                "Missing validate parameters: value",
                "Invalid validate parameter \"count\" for type int32"
            )
        )
    }

    fun `test invalid int array parameters`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(value: 10)]
                id: int32[];
            }
        }
        """

        checkInspection(
            code,
            listOf(
                "Missing validate parameters: count",
                "Invalid validate parameter \"value\" for type int32[]"
            )
        )
    }

    fun `test invalid enum parameters`() {
        val code = """
        service MessageService {
            enum MessageKind { image, text }

            data Message
            {
                [required, validate(value: 10)]
                kind: MessageKind;
            }
        }
        """

        checkInspection(
            code,
            listOf(
                "Invalid validate parameter \"value\" for type MessageKind"
            )
        )
    }

    fun `test validate unsupported service item`() {
        val code = """
        service MessageService {
            [validate]
            data Message
            {
                [required]
                kind: MessageKind;
            }
        }
        """

        checkInspection(code, listOf("Unexpected attribute: validate"))
    }

    fun `test invalid regex value`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(regex: 10)]
                id: string;
            }
        }
        """

        checkInspection(code, listOf("Invalid regex value \"10\" for attribute validate"))
    }

    fun `test invalid length value`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(length: ten)]
                id: string;
            }
        }
        """

        checkInspection(code, listOf("Invalid length value \"ten\" for attribute validate"))
    }

    fun `test invalid value value`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(value: ten)]
                id: int32;
            }
        }
        """

        checkInspection(code, listOf("Invalid value value \"ten\" for attribute validate"))
    }

    fun `test invalid count value`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(count: ten)]
                id: int32[];
            }
        }
        """

        checkInspection(
            code,
            listOf(
                "Invalid count value \"ten\" for attribute validate",
            )
        )
    }

    fun `test invalid range`() {
        val code = """
        service MessageService {
            data Message
            {
                [required, validate(count: 1..0)]
                id: int32[];
            }
        }
        """

        checkInspection(
            code,
            listOf(
                "Invalid count value \"1..0\" for attribute validate",
            )
        )
    }

    private fun checkInspection(code: String, errorDescriptions: List<String>) {
        myFixture.configureByText(FsdFileType, code)
        myFixture.enableInspections(ValidateAttributeInspection())
        val highlights = myFixture.doHighlighting()

        assertThat(highlights.map { it.description }, "inspection failures")
            .isEqualTo(errorDescriptions)
    }
}
