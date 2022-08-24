package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.codeInspection.SuppressionUtil
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.siblings
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdDecoratedElement

class FsdInspectionSuppressor : InspectionSuppressor {
    override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedElement::class.java) ?: return false

        return serviceItem.siblings(forward = false, withSelf = false)
            .dropWhile { it is PsiWhiteSpace && it.text.count { c -> c == '\n' } <= 1 }
            .takeWhile { it is PsiComment }
            .filter { SuppressionUtil.isSuppressionComment(it) }
            .any { SuppressionUtil.isInspectionToolIdMentioned(it.text, toolId) }
    }

    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
        val message = FsdBundle.getMessage("inspections.suppression.fix.byid", toolId)
        return arrayOf(FsdSuppressQuickFix(toolId, message))
    }
}
