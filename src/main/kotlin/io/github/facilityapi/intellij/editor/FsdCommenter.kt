package io.github.facilityapi.intellij.editor

import com.intellij.lang.Commenter

class FsdCommenter : Commenter {
    override fun getLineCommentPrefix() = "//"
    override fun getBlockCommentPrefix() = ""
    override fun getBlockCommentSuffix(): String? = null
    override fun getCommentedBlockCommentPrefix(): String? = null
    override fun getCommentedBlockCommentSuffix(): String? = null
}
