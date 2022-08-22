package io.github.facilityapi.intellij

import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class FsdCommenterTest : BasePlatformTestCase() {
    fun `test comment out line`() {
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

                    data Wheel
                    {
                        <caret>id: int64;
                    }
                }
            """.trimIndent()
        )
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult(
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wheel[];
                        spare: Wheel;
                    }

                    data Wheel
                    {
                //        id: int64;
                    }
                }
            """.trimIndent()
        )
    }

    fun `test uncomment line`() {
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

                    data Wheel
                    {
                //        id: in<caret>t64;
                    }
                }
            """.trimIndent()
        )
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult(
            """
                service FsdFindUsages
                {
                    data Car
                    {
                        wheel: Wheel[];
                        spare: Wheel;
                    }

                    data Wheel
                    {
                        id: int64;
                    }
                }
            """.trimIndent()
        )
    }
}
