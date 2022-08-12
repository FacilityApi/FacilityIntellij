package io.github.facilityapi.intellij

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType

class FsdLiveTemplateContext : TemplateContextType(FsdLanguage.id, FsdLanguage.displayName) {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        return templateActionContext.file is FsdFile
    }
}
