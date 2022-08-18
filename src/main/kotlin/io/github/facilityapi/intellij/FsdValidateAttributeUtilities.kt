package io.github.facilityapi.intellij

import com.intellij.codeInsight.template.Template
import com.intellij.codeInsight.template.impl.TemplateSettings
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdField

fun supportsValidate(field: FsdField): Boolean {
    val isEnumTyped = field.type.referenceType?.reference?.resolve()?.parent is FsdEnumSpec

    return isEnumTyped || getValidateTemplateForField(field) != null
}

fun getValidateTemplateForField(field: FsdField): Template? {
    val type = field.type.text

    val templateKey = when {
        type == "string" -> "svalid"
        isCollection(type) -> "cvalid"
        isNumber(type) -> "nvalid"
        else -> return null
    }

    return TemplateSettings.getInstance().getTemplate(templateKey, FsdLanguage.displayName)
}

private fun isCollection(typeName: String) = typeName == "bytes" ||
    typeName.startsWith("map<") ||
    typeName.endsWith("[]")

private fun isNumber(typeName: String) = typeName == "int32" || typeName == "int64"
