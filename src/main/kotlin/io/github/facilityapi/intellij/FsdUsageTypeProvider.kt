package io.github.facilityapi.intellij

import com.intellij.psi.PsiElement
import com.intellij.usages.impl.rules.UsageType
import com.intellij.usages.impl.rules.UsageTypeProvider

class FsdUsageTypeProvider : UsageTypeProvider {
    override fun getUsageType(element: PsiElement?): UsageType = UsageType.CLASS_FIELD_DECLARATION
}
