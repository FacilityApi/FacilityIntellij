package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.isCollection
import io.github.facilityapi.intellij.isEnum
import io.github.facilityapi.intellij.isNumber
import io.github.facilityapi.intellij.psi.FsdAttribute
import io.github.facilityapi.intellij.psi.FsdAttributeParameter
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdErrorSpec
import io.github.facilityapi.intellij.supportsValidate

class ValidateAttributeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            val deleteAttributeFix = DeleteAttributeFix()
            val deleteParameterFix = DeleteAttributeParameterFix()
            val deleteParameterListFix = DeleteAttributeParameterListFix()

            override fun visitElement(element: PsiElement) {
                if (element !is FsdAttribute || element.attributename.text != "validate") return
                val decoratedElement = PsiTreeUtil.getParentOfType(element, FsdDecoratedElement::class.java) ?: return

                when (decoratedElement) {
                    is FsdDecoratedServiceItem -> {
                        // The only service item that supports [validate] are enums
                        if (decoratedElement.enumSpec != null) {
                            checkEnumValidate(element)
                        } else {
                            reportUnexpectedAttribute(element)
                        }
                    }
                    is FsdDecoratedField -> {
                        val field = decoratedElement.field
                        if (!supportsValidate(decoratedElement.field)) {
                            reportUnexpectedAttribute(element)
                        }

                        if (field.type.text == "string") {
                            checkStringValidate(element)
                        } else if (field.type.isCollection) {
                            checkCollectionValidate(element)
                        } else if (field.type.isNumber) {
                            checkNumberValidate(element)
                        } else if (field.type.isEnum) {
                            checkEnumValidate(element)
                        }
                    }
                    is FsdEnumSpec -> {
                        checkEnumValidate(element)
                    }
                    is FsdErrorSpec -> {
                        reportUnexpectedAttribute(element)
                    }
                }
            }

            private fun checkStringValidate(attribute: FsdAttribute) {
                checkAttributeParameters(attribute, setOf("regex", "length"))
            }

            private fun checkCollectionValidate(attribute: FsdAttribute) {
                checkAttributeParameters(attribute, setOf("count"))
            }

            private fun checkNumberValidate(attribute: FsdAttribute) {
                checkAttributeParameters(attribute, setOf("value"))
            }

            private fun checkEnumValidate(attribute: FsdAttribute) {
                if (attribute.attributeParameterList.isNotEmpty()) {
                    holder.registerProblem(
                        attribute, // todo: make the parameter list a PSI node to highlight just the list
                        FsdBundle.getMessage("inspections.bugs.attribute.parameters.unexpected"),
                        ProblemHighlightType.GENERIC_ERROR,
                        deleteParameterListFix
                    )
                }
            }

            private fun checkAttributeParameters(attribute: FsdAttribute, parameterNames: Set<String>) {
                var foundRequiredParameter = false

                for (parameter in attribute.attributeParameterList) {
                    val parameterName = parameter.identifier.text

                    if (parameterNames.contains(parameterName)) {
                        foundRequiredParameter = true

                        checkParameterValue(attribute, parameter)
                    } else {
                        val message = FsdBundle.getMessage(
                            "inspections.bugs.attribute.parameter.unexpected",
                            attribute.attributename.text,
                            parameterName
                        )

                        holder.registerProblem(
                            parameter,
                            message,
                            ProblemHighlightType.ERROR,
                            deleteParameterFix
                        )

                        continue
                    }
                }

                if (!foundRequiredParameter) {
                    val message = FsdBundle.getMessage(
                        "inspections.bugs.attribute.parameter.missing",
                        attribute.attributename.text,
                        parameterNames.joinToString()
                    )

                    holder.registerProblem(
                        attribute,
                        message,
                        ProblemHighlightType.GENERIC_ERROR,
                        deleteAttributeFix // todo: replace with template?
                    )
                }
            }

            private fun checkParameterValue(attribute: FsdAttribute, parameter: FsdAttributeParameter) {
                when (parameter.identifier.text) {
                    "regex" -> {
                        if (!Regex("\".*\"").matches(parameter.attributeparametervalue.text)) {
                            reportInvalidParameterValue(attribute, parameter)
                        }
                    }
                    "length", "count", "value" -> {
                        val valueText = parameter.attributeparametervalue.text
                        if (valueText.isEmpty()) {
                            reportInvalidParameterValue(attribute, parameter)
                        }

                        val exactValue = valueText.toLongOrNull()
                        if (exactValue != null) return

                        val first = valueText.substringBefore("..", "")
                        val second = valueText.substringAfter("..", "")
                        val minimum = first.toLongOrNull()
                        val maximum = second.toLongOrNull()

                        val noNumberSpecified = minimum == null && maximum == null
                        val firstIsNotNumber = first.isNotEmpty() && minimum == null
                        val secondIsNotNumber = second.isNotEmpty() && maximum == null
                        val invertedRange = minimum != null && maximum != null && minimum > maximum

                        val isInvalid = noNumberSpecified || firstIsNotNumber || secondIsNotNumber || invertedRange
                        if (isInvalid) {
                            reportInvalidParameterValue(attribute, parameter)
                        }
                    }
                }
            }

            private fun reportUnexpectedAttribute(attribute: FsdAttribute) {
                val message = FsdBundle.getMessage(
                    "inspections.bugs.attribute.unexpected",
                    attribute.attributename.text
                )

                holder.registerProblem(
                    attribute,
                    message,
                    ProblemHighlightType.ERROR,
                    deleteAttributeFix
                )
            }

            private fun reportInvalidParameterValue(attribute: FsdAttribute, parameter: FsdAttributeParameter) {
                val message = FsdBundle.getMessage(
                    "inspections.bugs.attribute.parameter.value.invalid",
                    parameter.identifier.text,
                    parameter.attributeparametervalue.text,
                    attribute.attributename.text
                )

                holder.registerProblem(
                    parameter,
                    message,
                    ProblemHighlightType.ERROR,
                    deleteParameterFix
                )
            }
        }
    }
}
