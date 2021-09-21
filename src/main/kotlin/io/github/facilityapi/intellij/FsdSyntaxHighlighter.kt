package io.github.facilityapi.intellij

import io.github.facilityapi.intellij.lexer.FsdLexerAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.tree.IElementType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = FsdLexerAdapter()

    // NOTE: these colors are not final
    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            FsdTypes.COMMENT -> arrayOf(createTextAttributesKey("FSD.COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT))
            FsdTypes.IDENTIFIER -> arrayOf(createTextAttributesKey("FSD.NAME", DefaultLanguageHighlighterColors.IDENTIFIER))
            FsdTypes.ATTRIBUTENAME -> arrayOf(createTextAttributesKey("FSD.ATTRIBUTE.NAME", DefaultLanguageHighlighterColors.METADATA))
            FsdTypes.ATTRIBUTEPARAMETERVALUE -> arrayOf(createTextAttributesKey("FSD.ATTRIBUTE.ARGUMENT", DefaultLanguageHighlighterColors.STRING))

            FsdTypes.MARKDOWNHEADING -> arrayOf(createTextAttributesKey("FSD.MARKUP.HEADING", DefaultLanguageHighlighterColors.DOC_COMMENT))

            FsdTypes.STRING,
            FsdTypes.BOOLEAN,
            FsdTypes.INT32,
            FsdTypes.INT64,
            FsdTypes.DOUBLE,
            FsdTypes.DECIMAL,
            FsdTypes.BYTES,
            FsdTypes.OBJECT,
            FsdTypes.MAP,
            FsdTypes.RESULT,
            FsdTypes.ERROR -> arrayOf(createTextAttributesKey("FSD.TYPE.PRIMITIVE", DefaultLanguageHighlighterColors.KEYWORD))

            FsdTypes.SERVICE,
            FsdTypes.DATA,
            FsdTypes.METHOD,
            FsdTypes.ENUM,
            FsdTypes.ERRORS -> arrayOf(createTextAttributesKey("FSD.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD))
            else -> emptyArray()
        }
    }

    class Factory : SyntaxHighlighterFactory() {
        override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
            return FsdSyntaxHighlighter()
        }
    }
}