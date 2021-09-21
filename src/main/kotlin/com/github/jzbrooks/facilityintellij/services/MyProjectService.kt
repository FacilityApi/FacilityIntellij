package com.github.jzbrooks.facilityintellij.services

import com.intellij.openapi.project.Project
import com.github.jzbrooks.facilityintellij.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
