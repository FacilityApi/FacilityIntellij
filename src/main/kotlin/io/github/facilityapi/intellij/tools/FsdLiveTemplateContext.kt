package io.github.facilityapi.intellij.tools

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import io.github.facilityapi.intellij.FsdLanguage
import io.github.facilityapi.intellij.psi.FsdFile

class FsdLiveTemplateContext : TemplateContextType(FsdLanguage.id, FsdLanguage.displayName) {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        return templateActionContext.file is FsdFile
    }
}
