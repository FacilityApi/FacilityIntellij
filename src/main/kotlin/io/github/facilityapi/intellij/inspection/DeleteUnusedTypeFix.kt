package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdTypes

class DeleteUnusedTypeFix : LocalQuickFix {
    override fun getFamilyName() = NAME

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val serviceItem = PsiTreeUtil.getParentOfType(descriptor.psiElement, FsdDecoratedServiceItem::class.java)
        checkNotNull(serviceItem) { "This fix expects the problem element to be the child of a decorated service item" }

        var previousSibling = serviceItem.prevSibling

        if (previousSibling is PsiWhiteSpace) {
            val whitespace = previousSibling
            previousSibling = previousSibling.prevSibling
            whitespace.delete()
        }

        while (previousSibling?.elementType in setOf(FsdTypes.COMMENT)) {
            val comment = previousSibling
            previousSibling = previousSibling.prevSibling
            comment.delete()
        }

        serviceItem.delete()
    }

    companion object {
        val NAME = FsdBundle.getMessage("inspections.hints.unused.fix")
    }
}
