package io.github.facilityapi.intellij

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.IconLoader

class FsdColorSettingsPage : ColorSettingsPage {
    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null
    override fun getColorDescriptors(): Array<ColorDescriptor> = emptyArray()

    override fun getDisplayName() = "Facility Service Definition"
    override fun getIcon() = IconLoader.getIcon("/icons/fsd.svg", FsdFileType::class.java)
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS
    override fun getHighlighter(): SyntaxHighlighter = FsdSyntaxHighlighter()

    override fun getDemoText() = """
        /// Example service for widgets.
        [http(url: "http://local.example.com/v1")]
        [csharp(namespace: Facility.ExampleApi)]
        service ExampleApi
        {
        	/// Gets widgets.
        	[http(method: GET, path: "/widgets")]
        	method getWidgets
        	{
        		/// The query.
        		[http(from: query, name: "q")]
        		query: string;

        		/// The limit of returned results.
        		limit: int32;

        		/// The sort field.
        		sort: WidgetField;

        		/// True to sort descending.
        		desc: boolean;

        		/// The maximum weight.
        		[obsolete]
        		maxWeight: double;
        	}:
        	{
        		/// The widgets.
        		widgets: Widget[];

        		/// The total number of widgets.
        		total: int64;

        		/// The total weight.
        		[obsolete]
        		totalWeight: double;

        		/// The pending job.
        		[http(from: body, code: 202)]
        		job: WidgetJob;
        	}           
        }
        
        
        # ExampleApi

        Additional service remarks.

        ## Heading

        Use a primary heading to indicate the member name.
    """.trimIndent()

    companion object {
        private val DESCRIPTORS = arrayOf<AttributesDescriptor>(
            AttributesDescriptor("Markup heading", FsdSyntaxHighlighter.MARKUP_HEADING_KEYS[0]),
            AttributesDescriptor("Comment", FsdSyntaxHighlighter.COMMENT_KEYS[0]),
            AttributesDescriptor("Keyword", FsdSyntaxHighlighter.KEYWORD_KEYS[0]),
            AttributesDescriptor("Primitive type", FsdSyntaxHighlighter.PRIMITIVE_TYPE_KEYS[0]),
            AttributesDescriptor("Type reference", FsdSyntaxHighlighter.TYPE_REFERENCE_KEYS[0]),
            AttributesDescriptor("Attribute", FsdSyntaxHighlighter.ATTRIBUTE_NAME_KEYS[0]),
            AttributesDescriptor("Attribute parameter value", FsdSyntaxHighlighter.ATTRIBUTE_ARGUMENT_KEYS[0]),
        )
    }
}