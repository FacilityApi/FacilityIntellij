package io.github.facilityapi.intellij

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdBraceMatcher : PairedBraceMatcher {
    override fun getPairs() = arrayOf(
        BracePair(FsdTypes.LEFT_BRACE, FsdTypes.RIGHT_BRACE, false),
        BracePair(FsdTypes.LEFT_BRACKET, FsdTypes.RIGHT_BRACKET, false),
        BracePair(FsdTypes.LEFT_ANGLE, FsdTypes.RIGHT_ANGLE, false),
    )

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?) = true

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int) = openingBraceOffset
}
