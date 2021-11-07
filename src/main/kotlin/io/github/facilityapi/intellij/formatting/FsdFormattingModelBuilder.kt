package io.github.facilityapi.intellij.formatting

import com.intellij.formatting.*
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.FsdLanguage
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings

        val spaceBuilder = SpacingBuilder(codeStyleSettings, FsdLanguage)
            .between(FsdTypes.LEFT_BRACE, FsdTypes.RIGHT_BRACE)
            .spaces(codeStyleSettings.getIndentSize(FsdFileType))

        val block = FsdBlock(formattingContext.node,
            Wrap.createWrap(WrapType.NONE, false),
            Alignment.createAlignment(),
            spaceBuilder,
        )

        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            block,
            codeStyleSettings,
        )
    }
}