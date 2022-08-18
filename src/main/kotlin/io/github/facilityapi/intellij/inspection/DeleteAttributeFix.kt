package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.psi.FsdAttribute
import io.github.facilityapi.intellij.psi.FsdAttributeList

class DeleteAttributeFix : LocalQuickFix {
    override fun getFamilyName() = NAME

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val attribute = descriptor.psiElement as FsdAttribute
        val attributeList = attribute.parent as FsdAttributeList
        val size = attributeList.attributeList.size

        if (size == 1) {
            attributeList.delete()
        } else {
            val index = attributeList.attributeList.indexOf(attribute)

            if (index == size - 1) {
                attributeList.deleteChildRange(attributeList.attributeList[index - 1].nextSibling, attribute)
            } else {
                attributeList.deleteChildRange(attribute, attributeList.attributeList[index + 1].prevSibling)
            }
        }
    }

    companion object {
        val NAME = FsdBundle.getMessage("inspections.bugs.attribute.delete.quickfix")
    }
}
