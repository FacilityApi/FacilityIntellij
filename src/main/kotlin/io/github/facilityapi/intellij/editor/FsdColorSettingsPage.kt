package io.github.facilityapi.intellij.editor

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.IconLoader
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.FsdLanguage

class FsdColorSettingsPage : ColorSettingsPage {
    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null
    override fun getColorDescriptors(): Array<ColorDescriptor> = emptyArray()

    override fun getDisplayName() = FsdLanguage.displayName
    override fun getIcon() = IconLoader.getIcon("/icons/fsd.svg", FsdFileType::class.java)
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS
    override fun getHighlighter(): SyntaxHighlighter = FsdSyntaxHighlighter()

    override fun getDemoText() = FsdBundle.getMessage("settings.color.demo")

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor(
                FsdBundle.getMessage("settings.color.displayname.markup"),
                FsdSyntaxHighlighter.MARKUP_HEADING_KEYS[0]
            ),
            AttributesDescriptor(
                FsdBundle.getMessage("settings.color.displayname.comment"),
                FsdSyntaxHighlighter.COMMENT_KEYS[0]
            ),
            AttributesDescriptor(
                FsdBundle.getMessage("settings.color.displayname.keyword"),
                FsdSyntaxHighlighter.KEYWORD_KEYS[0]
            ),
            AttributesDescriptor(
                FsdBundle.getMessage("settings.color.displayname.primitive"),
                FsdSyntaxHighlighter.PRIMITIVE_TYPE_KEYS[0]
            ),
            AttributesDescriptor(
                FsdBundle.getMessage("settings.color.displayname.typeref"),
                FsdSyntaxHighlighter.TYPE_REFERENCE_KEYS[0]
            ),
            AttributesDescriptor(
                FsdBundle.getMessage("settings.color.displayname.attribute"),
                FsdSyntaxHighlighter.ATTRIBUTE_NAME_KEYS[0]
            ),
            AttributesDescriptor(
                FsdBundle.getMessage("settings.color.displayname.attributeparametervalue"),
                FsdSyntaxHighlighter.ATTRIBUTE_ARGUMENT_KEYS[0]
            ),
        )
    }
}
