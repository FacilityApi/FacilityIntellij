package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import io.github.facilityapi.intellij.psi.FsdDataSpec
import io.github.facilityapi.intellij.psi.FsdEnumValueList
import io.github.facilityapi.intellij.psi.FsdErrorList
import io.github.facilityapi.intellij.psi.FsdMethodSpec
import io.github.facilityapi.intellij.psi.FsdServiceItems
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdIndentProcessor {
    fun getIndent(node: ASTNode): Indent {
        if (node.psi.parent is FsdServiceItems) return Indent.getNormalIndent()

        // todo: figure out comments
        if (node.psi.parent is FsdMethodSpec && (node.elementType == FsdTypes.COMMENT || node.elementType == FsdTypes.DECORATED_FIELD)) return Indent.getNormalIndent()
        if (node.psi.parent is FsdDataSpec && (node.elementType == FsdTypes.COMMENT || node.elementType == FsdTypes.DECORATED_FIELD)) return Indent.getNormalIndent()
        if (node.psi.parent is FsdEnumValueList && (node.elementType == FsdTypes.COMMENT || node.elementType == FsdTypes.DECORATED_ENUM_VALUE)) return Indent.getNormalIndent()
        if (node.psi.parent is FsdErrorList && (node.elementType == FsdTypes.COMMENT || node.elementType == FsdTypes.DECORATED_ERROR_SPEC)) return Indent.getNormalIndent()

        return Indent.getNoneIndent()
    }
}
