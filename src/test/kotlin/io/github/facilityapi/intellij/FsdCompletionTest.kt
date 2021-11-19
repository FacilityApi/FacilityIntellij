package io.github.facilityapi.intellij

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isNotNull
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FsdCompletionTest : BasePlatformTestCase() {
    fun `test completion`() {
        myFixture.configureByText(
            FsdFileType,
            """
                service FsdFindUsages
                {
                    data Widget
                    {
                        name: string;
                    }

                    data WidgetJob
                    {
                        id: int64;
                    }

                    data Interface
                    {
                        widgets: Wid<caret>
                    }
                }
            """.trimIndent()
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings, "completion suggestions")
            .isNotNull()
            .containsExactlyInAnyOrder("Widget", "WidgetJob")
    }

    fun `test built-in type completion`() {
        myFixture.configureByText(
            FsdFileType,
            """
                service FsdFindUsages
                {
                    data Widget
                    {
                        name: string;
                    }

                    data WidgetJob
                    {
                        id: int64;
                    }

                    data Interface
                    {
                        widgets: d<caret>
                    }
                }
            """.trimIndent()
        )
        myFixture.completeBasic()
        assertThat(myFixture.lookupElementStrings, "completion suggestions")
            .isNotNull()
            .containsExactlyInAnyOrder("double", "decimal")
    }
}
