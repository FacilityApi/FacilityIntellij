package io.github.facilityapi.intellij.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import io.github.facilityapi.intellij.reference.FsdReference
import io.github.facilityapi.intellij.reference.createDataType

object FsdPsiImplUtil {
    @JvmStatic
    fun FsdDtoSpec.getName(): String = identifier.text

    @JvmStatic
    fun FsdDtoSpec.setName(name: String): PsiElement {
        val dataType = createDataType(project, name)
        val newNode = dataType.identifier.node
        node.replaceChild(identifier.node, newNode)
        return this
    }

    @JvmStatic
    fun FsdDtoSpec.getNameIdentifier(): PsiElement = identifier

    @JvmStatic
    fun FsdReferenceType.getReference(): PsiReference = FsdReference(this, textRange)
}