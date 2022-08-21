package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.psi.util.siblings
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdInspectionSuppressor : InspectionSuppressor {
    override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
        val serviceItem = PsiTreeUtil.getParentOfType(element, FsdDecoratedServiceItem::class.java) ?: return false
        return serviceItem.siblings(false).filter { it.elementType == FsdTypes.COMMENT }.any { it.text.contains("suppress") }
    }

    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
        return emptyArray()
    }
}
