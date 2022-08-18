package io.github.facilityapi.intellij.inspection

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
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
import org.eclipse.xtext.xbase.lib.ByteExtensions

class ValidateAttributeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            val deleteAttributeFix = DeleteAttributeFix()

            override fun visitElement(element: PsiElement) {
                if (element !is FsdAttribute || element.attributename.text != "validate") return
                val decoratedElement = PsiTreeUtil.getParentOfType(element, FsdDecoratedElement::class.java) ?: return

                when (decoratedElement) {
                    is FsdDecoratedServiceItem -> {
                        // The only service item that supports [validate] are enums
                        if (decoratedElement.enumSpec == null) {
                            holder.registerProblem(
                                element,
                                "This has no effect.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix
                            )
                        }
                    }
                    is FsdDecoratedField -> {
                        val field = decoratedElement.field
                        if (!supportsValidate(decoratedElement.field)) {
                            holder.registerProblem(
                                element,
                                "This has no effect.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix
                            )
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
                        holder.registerProblem(
                            element,
                            "This has no effect.", // todo: i10n & consistent with facility
                            ProblemHighlightType.ERROR,
                            deleteAttributeFix
                        )
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
                if (attribute.attributeParameterList.size > 1) {
                    holder.registerProblem(
                        attribute, // todo: make the parameter list a PSI node to highlight just the list
                        "Parameters have no effect", // todo: i10n & consistent with facility
                        ProblemHighlightType.GENERIC_ERROR,
                        deleteAttributeFix // todo: maybe just delete the parameters?
                    )
                }
            }

            private fun checkAttributeParameters(attribute: FsdAttribute, parameterNames: Set<String>) {
                var foundRequiredParameter = false

                for (parameter in attribute.attributeParameterList) {
                    val parameterName = parameter.identifier.text

                    if (parameterNames.contains(parameterName)) {
                        foundRequiredParameter = true

                        checkParameterValue(parameter)
                    } else {
                        holder.registerProblem(
                            parameter,
                            "Invalid parameter: $parameterName", // todo: i10n & consistent with facility
                            ProblemHighlightType.ERROR,
                            deleteAttributeFix // todo: delete parameter fix
                        )

                        continue
                    }
                }

                if (!foundRequiredParameter) {
                    holder.registerProblem(
                        attribute,
                        "Missing parameters: ${parameterNames.joinToString()}.", // todo: i10n & consistent with facility
                        ProblemHighlightType.GENERIC_ERROR,
                        deleteAttributeFix // todo: replace with template?
                    )
                }
            }

            private fun checkParameterValue(parameter: FsdAttributeParameter) {
                when (parameter.identifier.text) {
                    "regex" -> {
                        if (!Regex("\".*\"").matches(parameter.attributeparametervalue.text)) {
                            holder.registerProblem(
                                parameter,
                                "Invalid ${parameter.identifier.text} value: ${parameter.attributeparametervalue.text}.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix // todo: replace with template?
                            )
                        }
                    }
                    "length", "count", "value" -> {
                        val valueText = parameter.attributeparametervalue.text
                        if (valueText.isEmpty()) {
                            holder.registerProblem(
                                parameter,
                                "Invalid ${parameter.identifier.text} value: ${parameter.attributeparametervalue.text}.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix // todo: replace with template?
                            )
                        }

                        val exactValue = valueText.toLongOrNull()
                        if (exactValue != null) return

                        val first = valueText.substringBefore("..", "")
                        val second = valueText.substringAfter("..", "")
                        val minimum = first.toLongOrNull()
                        val maximum = second.toLongOrNull()

                        if (first.isNotEmpty() && minimum == null) {
                            holder.registerProblem(
                                parameter,
                                "Invalid value: ${parameter.attributeparametervalue.text}.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix // todo: replace with template?
                            )
                        } else if (second.isNotEmpty() && maximum == null) {
                            holder.registerProblem(
                                parameter,
                                "Invalid value: ${parameter.attributeparametervalue.text}.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix // todo: replace with template?
                            )
                        }

                        if (minimum == null && maximum == null) {
                            holder.registerProblem(
                                parameter,
                                "Invalid value: ${parameter.attributeparametervalue.text}.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix // todo: replace with template?
                            )
                        }

                        if (minimum != null && maximum != null && minimum > maximum) {
                            holder.registerProblem(
                                parameter,
                                "Invalid value: ${parameter.attributeparametervalue.text}.", // todo: i10n & consistent with facility
                                ProblemHighlightType.ERROR,
                                deleteAttributeFix // todo: replace with template?
                            )
                        }
                    }
                }
            }
        }
    }
}
