package io.github.facilityapi.intellij

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase
import io.github.facilityapi.intellij.lexer.FsdLexerAdapter

class FsdLexerTest : LexerTestCase() {
    override fun createLexer(): Lexer = FsdLexerAdapter()
    override fun getDirPath(): String = "src/test/testData"

    fun `test correct restart`() {
        checkCorrectRestartOnEveryToken(
            """
                [CSharp(namespace: test)]
                service WidgetAPI
                {
                }
            """.trimIndent()
        )
    }
}
