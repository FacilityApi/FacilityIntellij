package io.github.facilityapi.intellij

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Ignore

class FsdRenameTypeTest : BasePlatformTestCase() {
    @Ignore
    fun `test rename type from reference`() {
        myFixture.configureByText(
            FsdLanguage.associatedFileType,
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wheel[];
                        spare: Wheel;
                    }

                    data Wh<caret>eel
                    {
                        id: int64;
                    }
                }
            """.trimIndent()
        )
        myFixture.renameElement(myFixture.elementAtCaret, "Rim")

        assertThat(myFixture.editor.document.text, "document text").all {
            doesNotContain("Wheel")
            contains("Rim")
        }
    }
}
