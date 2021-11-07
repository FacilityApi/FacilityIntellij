package io.github.facilityapi.intellij.psi

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiReference
import io.github.facilityapi.intellij.FsdFile
import io.github.facilityapi.intellij.FsdFileType
import io.github.facilityapi.intellij.reference.FsdReference
import io.github.facilityapi.intellij.reference.createTypeDefinition


object FsdPsiImplUtil {
    @JvmStatic
    fun FsdTypeIdentifier.getName(): String = identifier.text

    @JvmStatic
    fun FsdTypeIdentifier.setName(name: String): PsiElement {
        val dataType = createTypeDefinition(project, name)
        val newNode = dataType.node
        node.replaceChild(identifier.node, newNode)
        return this
    }

    @JvmStatic
    fun FsdTypeIdentifier.getNameIdentifier(): PsiElement {
        return identifier
    }

    @JvmStatic
    fun FsdReferenceType.getReference(): PsiReference = FsdReference(this, textRangeInParent)

    @JvmStatic
    fun FsdNamedElement.getPresentation(): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText() = this@getPresentation.name ?: ""
            override fun getLocationString() = this@getPresentation.containingFile?.name
            override fun getIcon(unused: Boolean) = FsdFileType.icon
        }
    }
}