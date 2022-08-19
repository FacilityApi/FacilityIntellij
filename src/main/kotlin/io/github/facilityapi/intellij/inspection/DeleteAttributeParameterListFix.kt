package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttributeParameters

class DeleteAttributeParameterListFix : LocalQuickFix {
    override fun getFamilyName() = NAME

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        (descriptor.psiElement.parent as FsdAttributeParameters).delete()
    }

    companion object {
        val NAME = FsdBundle.getMessage("inspections.bugs.attribute.parameters.delete.quickfix")
    }
}
