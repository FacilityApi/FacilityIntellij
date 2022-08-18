package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttribute
import io.github.facilityapi.intellij.psi.FsdAttributeParameter

class DeleteAttributeParameterFix : LocalQuickFix {
    override fun getFamilyName() = NAME

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val parameter = descriptor.psiElement as FsdAttributeParameter
        val attribute = parameter.parent as FsdAttribute
        val size = attribute.attributeParameterList.size

        if (size == 1) {
            attribute.deleteChildRange(
                attribute.attributeParameterList.first().prevSibling,
                attribute.attributeParameterList.last().nextSibling
            )
        } else {
            val index = attribute.attributeParameterList.indexOf(parameter)

            if (index == size - 1) {
                attribute.deleteChildRange(attribute.attributeParameterList[index - 1].nextSibling, parameter)
            } else {
                attribute.deleteChildRange(parameter, attribute.attributeParameterList[index + 1].prevSibling)
            }
        }
    }

    companion object {
        val NAME = FsdBundle.getMessage("inspections.bugs.attribute.parameter.delete.quickfix")
    }
}
