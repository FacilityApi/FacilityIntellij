package io.github.facilityapi.intellij.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import io.github.facilityapi.intellij.FsdLanguage
import io.github.facilityapi.intellij.lexer.FsdLexerAdapter
import io.github.facilityapi.intellij.psi.FsdFile
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = FsdLexerAdapter()
    override fun createParser(project: Project?): PsiParser = FsdParser()
    override fun getFileNodeType(): IFileElementType = object : IFileElementType(FsdLanguage) {}
    override fun getCommentTokens(): TokenSet = TokenSet.create(FsdTypes.COMMENT)
    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY
    override fun createElement(node: ASTNode?): PsiElement = FsdTypes.Factory.createElement(node)
    override fun createFile(viewProvider: FileViewProvider): PsiFile = FsdFile(viewProvider)
}
