package io.github.facilityapi.intellij

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FsdDocumentationProviderTest : BasePlatformTestCase() {
    fun `test documentation`() {
        myFixture.configureByText(
            FsdLanguage.associatedFileType,
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wheel[];
                        spare: Whe<caret>el;
                    }

                    /// It's circular
                    data Wheel
                    {
                        id: int64;
                    }
                }
            """.trimIndent()
        )
        val originalElement = myFixture.elementAtCaret
        var element = DocumentationManager
            .getInstance(project)
            .findTargetElement(myFixture.editor, originalElement.containingFile, originalElement)

        if (element == null) {
            element = originalElement
        }

        val documentationProvider = DocumentationManager.getProviderFromElement(element)
        val generateDoc = documentationProvider.generateDoc(element, originalElement)

        assertThat(generateDoc, "generatedDoc").isNotNull().isEqualTo(
            """
                <div class='definition'><pre>data Wheel</pre></div>
                <div class='content'>It's circular</div>
                <table class='sections'>
                <tr><td valign='top' class='section'>
                <p>File:</td><td valign='top'><p>aaa.fsd</td>
                </table>
            """.trimIndent().replace("\n", "")
        )
    }

    fun `test multi-line documentation`() {
        myFixture.configureByText(
            FsdLanguage.associatedFileType,
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wheel[];
                        spare: Whe<caret>el;
                    }

                    /// It's circular.
                    /// It rolls.
                    /// Used to transport things faster.
                    data Wheel
                    {
                        id: int64;
                    }
                }
            """.trimIndent()
        )
        val originalElement = myFixture.elementAtCaret
        var element = DocumentationManager
            .getInstance(project)
            .findTargetElement(myFixture.editor, originalElement.containingFile, originalElement)

        if (element == null) {
            element = originalElement
        }

        val documentationProvider = DocumentationManager.getProviderFromElement(element)
        val generateDoc = documentationProvider.generateDoc(element, originalElement)

        assertThat(generateDoc, "generatedDoc").isNotNull()
            .isEqualTo(
                """
                <div class='definition'><pre>data Wheel</pre></div>
                <div class='content'>It's circular. It rolls. Used to transport things faster.</div>
                <table class='sections'>
                <tr><td valign='top' class='section'>
                <p>File:</td><td valign='top'><p>aaa.fsd</td>
                </table>
                """.trimIndent().replace("\n", "")
            )
    }
}
