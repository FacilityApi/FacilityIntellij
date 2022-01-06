package io.github.facilityapi.intellij.psi

import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import io.github.facilityapi.intellij.reference.FsdReference
import io.github.facilityapi.intellij.reference.createTypeDefinition

object FsdPsiImplUtil {
    @JvmStatic
    fun FsdIdentifierDeclaration.getName(): String = identifier.text

    @JvmStatic
    fun FsdIdentifierDeclaration.setName(name: String): PsiElement {
        val declType = parent.node.firstChildNode.text
        val newDefinition = createTypeDefinition(project, name, declType)
        val newNode = newDefinition.node
        node.replaceChild(identifier.node, newNode)
        return this
    }

    @JvmStatic
    fun FsdIdentifierDeclaration.getNameIdentifier(): PsiElement {
        return identifier
    }

    @JvmStatic
    fun FsdReferenceType.getReference(): PsiReference = FsdReference(this, textRangeInParent)

    @JvmStatic
    fun FsdNamedElement.getPresentation(): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText() = this@getPresentation.name ?: ""
            override fun getLocationString() = this@getPresentation.containingFile?.name
            override fun getIcon(unused: Boolean) = when (parent) {
                is FsdDataSpec -> IconLoader.getIcon("/icons/data.svg", FsdDataSpec::class.java)
                is FsdEnumSpec -> IconLoader.getIcon("/icons/enum.svg", FsdEnumSpec::class.java)
                is FsdErrorSetSpec -> IconLoader.getIcon("/icons/error-set.svg", FsdErrorSetSpec::class.java)
                is FsdMethodSpec -> IconLoader.getIcon("/icons/method.svg", FsdMethodSpec::class.java)
                is FsdServiceSpec -> IconLoader.getIcon("/icons/service.svg", FsdServiceSpec::class.java)
                else -> throw IllegalStateException(
                    "Presentation is unsupported for ${this@getPresentation::class.simpleName}"
                )
            }
        }
    }
}
