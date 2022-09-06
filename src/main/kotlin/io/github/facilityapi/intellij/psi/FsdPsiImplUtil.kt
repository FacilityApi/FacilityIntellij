package io.github.facilityapi.intellij.psi

import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import io.github.facilityapi.intellij.reference.FsdReference
import io.github.facilityapi.intellij.reference.createTypeDefinition

object FsdPsiImplUtil {
    @JvmStatic
    fun getName(identifierDeclaration: FsdIdentifierDeclaration): String = identifierDeclaration.identifier.text

    @JvmStatic
    fun setName(identifierDeclaration: FsdIdentifierDeclaration, name: String): PsiElement {
        val newDefinition = createTypeDefinition(identifierDeclaration.project, name)
        identifierDeclaration.replace(newDefinition)
        return identifierDeclaration
    }

    @JvmStatic
    fun getNameIdentifier(identifierDeclaration: FsdIdentifierDeclaration): PsiElement = identifierDeclaration.identifier

    @JvmStatic
    fun getReference(reference: FsdReferenceType): PsiReference = FsdReference(reference, reference.textRangeInParent)

    @JvmStatic
    fun getPresentation(element: FsdNamedElement) = object : ItemPresentation {
        override fun getPresentableText() = element.name ?: ""
        override fun getLocationString() = element.containingFile?.name
        override fun getIcon(unused: Boolean) = when (element.parent) {
            is FsdDataSpec -> IconLoader.getIcon("/icons/data.svg", FsdDataSpec::class.java)
            is FsdEnumSpec -> IconLoader.getIcon("/icons/enum.svg", FsdEnumSpec::class.java)
            is FsdErrorSetSpec -> IconLoader.getIcon("/icons/error-set.svg", FsdErrorSetSpec::class.java)
            is FsdMethodSpec -> IconLoader.getIcon("/icons/method.svg", FsdMethodSpec::class.java)
            is FsdServiceSpec -> IconLoader.getIcon("/icons/service.svg", FsdServiceSpec::class.java)
            else -> throw IllegalStateException(
                "Presentation is unsupported for ${element::class.simpleName}"
            )
        }
    }
}
