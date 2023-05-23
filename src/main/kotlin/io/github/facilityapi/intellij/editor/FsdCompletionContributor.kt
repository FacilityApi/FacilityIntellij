package io.github.facilityapi.intellij.editor

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import io.github.facilityapi.intellij.psi.FsdType
import io.github.facilityapi.intellij.psi.FsdTypes

class FsdCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(FsdTypes.TYPENAME).withSuperParent(2, FsdType::class.java),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    resultSet: CompletionResultSet,
                ) {
                    resultSet.addElement(LookupElementBuilder.create("string"))
                    resultSet.addElement(LookupElementBuilder.create("boolean"))
                    resultSet.addElement(LookupElementBuilder.create("int32"))
                    resultSet.addElement(LookupElementBuilder.create("int64"))
                    resultSet.addElement(LookupElementBuilder.create("double"))
                    resultSet.addElement(LookupElementBuilder.create("decimal"))
                    resultSet.addElement(LookupElementBuilder.create("bytes"))
                    resultSet.addElement(LookupElementBuilder.create("object"))
                    resultSet.addElement(LookupElementBuilder.create("map"))
                    resultSet.addElement(LookupElementBuilder.create("result"))
                    resultSet.addElement(LookupElementBuilder.create("nullable"))
                    resultSet.addElement(LookupElementBuilder.create("error"))
                }
            },
        )
    }
}
