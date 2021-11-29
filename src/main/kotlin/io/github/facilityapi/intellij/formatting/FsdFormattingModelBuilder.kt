package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.Alignment
import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider
import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType

class FsdFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val block = FsdBlock(
            formattingContext.node,
            null,
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
