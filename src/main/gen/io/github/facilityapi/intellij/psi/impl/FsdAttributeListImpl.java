// This is a generated file. Not intended for manual editing.
package io.github.facilityapi.intellij.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static io.github.facilityapi.intellij.psi.FsdTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import io.github.facilityapi.intellij.psi.*;

public class FsdAttributeListImpl extends ASTWrapperPsiElement implements FsdAttributeList {

  public FsdAttributeListImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull FsdVisitor visitor) {
    visitor.visitAttributeList(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof FsdVisitor) accept((FsdVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<FsdAttribute> getAttributeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, FsdAttribute.class);
  }

}
