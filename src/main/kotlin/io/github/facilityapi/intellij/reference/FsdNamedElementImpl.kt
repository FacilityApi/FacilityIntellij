package io.github.facilityapi.intellij.reference

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class FsdNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), FsdNamedElement