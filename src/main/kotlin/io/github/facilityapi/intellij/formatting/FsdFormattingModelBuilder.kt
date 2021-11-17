package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.*
import com.intellij.psi.formatter.DocumentBasedFormattingModel
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.FsdLanguage
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val block = FsdBlock(
            formattingContext.node,
            Wrap.createWrap(WrapType.NORMAL, false),
            Alignment.createAlignment(),
            formattingContext.codeStyleSettings,
        )

        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            block,
            formattingContext.codeStyleSettings,
        )
    }
}