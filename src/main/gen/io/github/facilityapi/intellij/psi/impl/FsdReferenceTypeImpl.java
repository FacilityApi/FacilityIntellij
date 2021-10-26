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
import com.intellij.psi.PsiReference;

public class FsdReferenceTypeImpl extends ASTWrapperPsiElement implements FsdReferenceType {

  public FsdReferenceTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull FsdVisitor visitor) {
    visitor.visitReferenceType(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof FsdVisitor) accept((FsdVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getTypename() {
    return findNotNullChildByType(TYPENAME);
  }

  @Override
  @NotNull
  public PsiReference getReference() {
    return FsdPsiImplUtil.getReference(this);
  }

}
