package io.github.facilityapi.intellij

import com.intellij.codeInsight.template.Template
import com.intellij.codeInsight.template.impl.TemplateSettings
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdField
import io.github.facilityapi.intellij.psi.FsdType

fun supportsValidate(field: FsdField): Boolean {
    return field.type.isEnum || getValidateTemplateForField(field) != null
}

fun getValidateTemplateForField(field: FsdField): Template? {
    val type = field.type

    val templateKey = when {
        type.text == "string" -> "svalid"
        type.isCollection -> "cvalid"
        type.isNumber -> "nvalid"
        else -> return null
    }

    return TemplateSettings.getInstance().getTemplate(templateKey, FsdLanguage.displayName)
}

val FsdType.isCollection: Boolean
    get() = text == "bytes" || text.startsWith("map<") || text.endsWith("[]")

val FsdType.isNumber: Boolean
    get() = text in setOf("int32", "int64", "float", "double", "decimal")

val FsdType.isEnum: Boolean
    get() = referenceType?.reference?.resolve()?.parent is FsdEnumSpec
