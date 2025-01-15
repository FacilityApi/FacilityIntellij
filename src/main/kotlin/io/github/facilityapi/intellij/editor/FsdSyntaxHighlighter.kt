package io.github.facilityapi.intellij.editor

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
import io.github.facilityapi.intellij.lexer.FsdLexerAdapter
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = FsdLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            FsdTypes.COMMENT -> COMMENT_KEYS

            FsdTypes.MARKDOWNHEADING -> MARKUP_HEADING_KEYS

            FsdTypes.ATTRIBUTENAME -> ATTRIBUTE_NAME_KEYS
            FsdTypes.ATTRIBUTEPARAMETERVALUE -> ATTRIBUTE_ARGUMENT_KEYS

            FsdTypes.STRING,
            FsdTypes.BOOLEAN,
            FsdTypes.FLOAT,
            FsdTypes.INT32,
            FsdTypes.INT64,
            FsdTypes.DOUBLE,
            FsdTypes.DECIMAL,
            FsdTypes.DATETIME,
            FsdTypes.BYTES,
            FsdTypes.OBJECT,
            FsdTypes.MAP,
            FsdTypes.RESULT,
            FsdTypes.NULLABLE,
            FsdTypes.ERROR,
            -> PRIMITIVE_TYPE_KEYS

            FsdTypes.TYPENAME -> TYPE_REFERENCE_KEYS

            FsdTypes.SERVICE,
            FsdTypes.DATA,
            FsdTypes.METHOD,
            FsdTypes.EVENT,
            FsdTypes.ENUM,
            FsdTypes.ERRORS,
            FsdTypes.EXTERN,
            -> KEYWORD_KEYS

            else -> emptyArray()
        }
    }

    class Factory : SyntaxHighlighterFactory() {
        override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
            return FsdSyntaxHighlighter()
        }
    }

    companion object {
        val COMMENT_KEYS = arrayOf(createTextAttributesKey("FSD.COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT))
        val MARKUP_HEADING_KEYS = arrayOf(createTextAttributesKey("FSD.MARKUP.HEADING", DefaultLanguageHighlighterColors.DOC_COMMENT))
        val ATTRIBUTE_NAME_KEYS = arrayOf(createTextAttributesKey("FSD.ATTRIBUTE.NAME", DefaultLanguageHighlighterColors.METADATA))
        val ATTRIBUTE_ARGUMENT_KEYS = arrayOf(createTextAttributesKey("FSD.ATTRIBUTE.ARGUMENT", DefaultLanguageHighlighterColors.STRING))
        val PRIMITIVE_TYPE_KEYS = arrayOf(createTextAttributesKey("FSD.TYPE.PRIMITIVE", DefaultLanguageHighlighterColors.KEYWORD))
        val TYPE_REFERENCE_KEYS = arrayOf(createTextAttributesKey("FSD.TYPE.REFERENCE", DefaultLanguageHighlighterColors.CLASS_REFERENCE))
        val KEYWORD_KEYS = arrayOf(createTextAttributesKey("FSD.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD))
    }
}
