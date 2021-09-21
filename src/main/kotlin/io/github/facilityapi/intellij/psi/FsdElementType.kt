package io.github.facilityapi.intellij.psi

import io.github.facilityapi.intellij.FsdLanguage
import com.intellij.psi.tree.IElementType

class FsdElementType(name: String) : IElementType(name, FsdLanguage)