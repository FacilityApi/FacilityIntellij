package io.github.facilityapi.intellij

import assertk.assertThat
import assertk.assertions.hasSize
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FsdFindUsageProviderTest: BasePlatformTestCase() {
    fun `test find usages works from type definition`() {
        myFixture.configureByText(
            FsdFileType,
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wheel[];
                        spare: Wheel;
                    }

                    data Whe<caret>el
                    {
                        id: int64;
                    }
                }
            """.trimIndent()
        )
        val usages = myFixture.findUsages(myFixture.elementAtCaret)
        assertThat(usages, "usages").hasSize(2)
    }

    fun `test find usages works from type reference on arrays`() {
        myFixture.configureByText(
            FsdFileType,
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wh<caret>eel[];
                        spare: Wheel;
                    }

                    data Wheel
                    {
                        id: int64;
                    }
                }
            """.trimIndent()
        )
        val usages = myFixture.findUsages(myFixture.elementAtCaret)
        assertThat(usages, "usages").hasSize(2)
    }

    fun `test find usages works from type reference`() {
        myFixture.configureByText(
            FsdFileType,
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wheel[];
                        spare: Wh<caret>eel;
                    }

                    data Wheel
                    {
                        id: int64;
                    }
                }
            """.trimIndent()
        )
        val usages = myFixture.findUsages(myFixture.elementAtCaret)
        assertThat(usages, "usages").hasSize(2)
    }
}