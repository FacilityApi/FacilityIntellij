package io.github.facilityapi.intellij

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase
import io.github.facilityapi.intellij.lexer.FsdLexerAdapter

class FsdLexerTest : LexerTestCase() {
    override fun createLexer(): Lexer = FsdLexerAdapter()
    override fun getDirPath(): String = "src/test/testData"

    fun `test lexer`() {
        doTest(
            """
                [CSharp(namespace: test)]
                service WidgetAPI
                {
                }
            """.trimIndent(),
            """
                [ ('[')
                attributename ('CSharp')
                ( ('(')
                identifier ('namespace')
                : (':')
                WHITE_SPACE (' ')
                attributeparametervalue ('test')
                ) (')')
                ] (']')
                WHITE_SPACE ('\n')
                service ('service')
                WHITE_SPACE (' ')
                identifier ('WidgetAPI')
                WHITE_SPACE ('\n')
                { ('{')
                WHITE_SPACE ('\n')
                } ('}')
            """.trimIndent(),
        )
    }

    fun `test correct restart`() {
        checkCorrectRestartOnEveryToken(
            """
                [CSharp(namespace: test)]
                service WidgetAPI
                {
                }
            """.trimIndent(),
        )
    }

    fun `test correct restart on invalid token`() {
        checkCorrectRestartOnEveryToken(
            """
                servi
            """.trimIndent(),
        )
    }
}
