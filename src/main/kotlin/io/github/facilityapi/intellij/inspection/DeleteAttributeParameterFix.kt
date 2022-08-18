package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttributeParameter
import io.github.facilityapi.intellij.psi.FsdAttributeParameters

class DeleteAttributeParameterFix : LocalQuickFix {
    override fun getFamilyName() = NAME

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val parameter = descriptor.psiElement as FsdAttributeParameter
        val parameterList = parameter.parent as FsdAttributeParameters
        val size = parameterList.attributeParameterList.size

        if (size == 1) {
            parameterList.delete()
        } else {
            val index = parameterList.attributeParameterList.indexOf(parameter)

            if (index == size - 1) {
                parameterList.deleteChildRange(parameterList.attributeParameterList[index - 1].nextSibling, parameter)
            } else {
                parameterList.deleteChildRange(parameter, parameterList.attributeParameterList[index + 1].prevSibling)
            }
        }
    }

    companion object {
        val NAME = FsdBundle.getMessage("inspections.bugs.attribute.parameter.delete.quickfix")
    }
}
