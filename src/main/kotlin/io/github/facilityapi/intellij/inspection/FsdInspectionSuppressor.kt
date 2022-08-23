package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.codeInspection.SuppressionUtil
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.siblings
import io.github.facilityapi.intellij.psi.FsdDecoratedElement

class FsdInspectionSuppressor : InspectionSuppressor {
    override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedElement::class.java) ?: return false

        return serviceItem.siblings(forward = false, withSelf = false)
            .dropWhile { it is PsiWhiteSpace && it.text.count { c -> c == '\n' } <= 1 }
            .takeWhile { it is PsiComment }
            .any { SuppressionUtil.isSuppressionComment(it) && it.text.matches(Regex("$toolId|all)", RegexOption.IGNORE_CASE)) }
    }

    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
        return arrayOf(FsdSuppressQuickFix(toolId, "Suppress \"$toolId\""))
    }
}
