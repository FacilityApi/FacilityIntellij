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

public class FsdFieldImpl extends ASTWrapperPsiElement implements FsdField {

  public FsdFieldImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull FsdVisitor visitor) {
    visitor.visitField(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof FsdVisitor) accept((FsdVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<FsdAttributeList> getAttributeListList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, FsdAttributeList.class);
  }

  @Override
  @NotNull
  public FsdType getType() {
    return findNotNullChildByClass(FsdType.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
