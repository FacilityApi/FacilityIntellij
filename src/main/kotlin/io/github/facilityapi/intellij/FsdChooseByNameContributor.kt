package io.github.facilityapi.intellij

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import io.github.facilityapi.intellij.psi.FsdNamedElement

class FsdChooseByNameContributor : ChooseByNameContributor {
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return findTypeDefinitions(project).mapNotNull(FsdNamedElement::getName).toList().toTypedArray()
    }

    override fun getItemsByName(
        name: String,
        pattern: String,
        project: Project,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        return findTypeDefinitions(project, name)
            .filterIsInstance<NavigationItem>()
            .toList()
            .toTypedArray()
    }
}
