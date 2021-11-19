package io.github.facilityapi.intellij

import com.intellij.lang.Language

object FsdLanguage : Language("FSD", "text/fsd") {
    override fun isCaseSensitive(): Boolean = true
    override fun getDisplayName(): String = "Facility Service Definition"
}
