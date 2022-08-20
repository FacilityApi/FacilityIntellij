package io.github.facilityapi.intellij.inspection

import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import io.github.facilityapi.intellij.FsdBundle
import io.github.facilityapi.intellij.isCollection
import io.github.facilityapi.intellij.isEnum
import io.github.facilityapi.intellij.isNumber
import io.github.facilityapi.intellij.psi.FsdAttribute
import io.github.facilityapi.intellij.psi.FsdAttributeParameter
import io.github.facilityapi.intellij.psi.FsdDecoratedElement
import io.github.facilityapi.intellij.psi.FsdDecoratedEnumValue
import io.github.facilityapi.intellij.psi.FsdDecoratedErrorSpec
import io.github.facilityapi.intellij.psi.FsdDecoratedField
import io.github.facilityapi.intellij.psi.FsdDecoratedServiceItem
import io.github.facilityapi.intellij.psi.FsdField
import io.github.facilityapi.intellij.supportsValidate

class UnusedTypeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
            HighlightInfoType.UNUSED_SYMBOL
            }
        }
    }
}
