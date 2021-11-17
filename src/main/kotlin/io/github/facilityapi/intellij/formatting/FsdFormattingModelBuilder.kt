package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.*

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