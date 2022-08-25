package io.github.facilityapi.intellij

import com.intellij.codeInsight.template.Template
import com.intellij.codeInsight.template.impl.TemplateSettings
import io.github.facilityapi.intellij.psi.FsdEnumSpec
import io.github.facilityapi.intellij.psi.FsdField
import io.github.facilityapi.intellij.psi.FsdType

fun supportsValidate(field: FsdField): Boolean {
    return getValidateTemplateForField(field) != null || field.type.isEnum
}

fun getValidateTemplateForField(field: FsdField): Template? {
    val type = field.type

    val templateKey = when {
        type.textMatches("string") -> "svalid"
        type.isCollection -> "cvalid"
        type.isNumber -> "nvalid"
        else -> return null
    }

    return TemplateSettings.getInstance().getTemplate(templateKey, FsdLanguage.displayName)
}

val FsdType.isCollection: Boolean
    get() = textMatches("bytes") || text.startsWith("map<") || text.endsWith("[]")

val FsdType.isNumber: Boolean
    get() {
        return textMatches("int32") ||
            textMatches("int64") ||
            textMatches("float") ||
            textMatches("double") ||
            textMatches("decimal")
    }

val FsdType.isEnum: Boolean
    get() = referenceType?.reference?.resolve()?.parent is FsdEnumSpec
