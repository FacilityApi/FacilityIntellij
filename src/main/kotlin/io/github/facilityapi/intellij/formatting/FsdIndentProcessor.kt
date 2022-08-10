package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumValueList
import io.github.facilityapi.intellij.psi.FsdErrorList
import io.github.facilityapi.intellij.psi.FsdRequestFields
import io.github.facilityapi.intellij.psi.FsdResponseFields
import io.github.facilityapi.intellij.psi.FsdServiceItems
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdIndentProcessor {
    fun getIndent(node: ASTNode): Indent {
        val parent = node.psi.parent
        val elementType = node.elementType

        if (parent is FsdServiceItems && elementType !in setOf(FsdTypes.LEFT_BRACE, FsdTypes.RIGHT_BRACE)) return Indent.getNormalIndent()
        if (parent is FsdRequestFields && (elementType == FsdTypes.COMMENT || elementType == FsdTypes.DECORATED_FIELD)) return Indent.getNormalIndent()
        if (parent is FsdResponseFields && (elementType == FsdTypes.COMMENT || elementType == FsdTypes.DECORATED_FIELD)) return Indent.getNormalIndent()
        if (parent is FsdDataSpec && (elementType == FsdTypes.COMMENT || elementType == FsdTypes.DECORATED_FIELD)) return Indent.getNormalIndent()
        if (parent is FsdEnumValueList && (elementType == FsdTypes.COMMENT || elementType == FsdTypes.DECORATED_ENUM_VALUE)) return Indent.getNormalIndent()
        if (parent is FsdErrorList && (elementType == FsdTypes.COMMENT || elementType == FsdTypes.DECORATED_ERROR_SPEC)) return Indent.getNormalIndent()

        return Indent.getNoneIndent()
    }
}
