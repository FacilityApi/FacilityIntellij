package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttribute

class DeleteAttributeParameterListFix : LocalQuickFix {
    override fun getFamilyName() = NAME

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val attribute = descriptor.psiElement as FsdAttribute

        attribute.deleteChildRange(
            attribute.attributeParameterList.first().prevSibling,
            attribute.attributeParameterList.last().nextSibling
        )
    }

    companion object {
        val NAME = FsdBundle.getMessage("inspections.bugs.attribute.parameters.delete.quickfix")
    }
}

