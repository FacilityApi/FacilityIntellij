package io.github.facilityapi.intellij.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class FsdNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), FsdNamedElement