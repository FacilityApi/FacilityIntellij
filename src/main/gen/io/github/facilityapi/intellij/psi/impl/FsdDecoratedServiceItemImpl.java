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

public class FsdDecoratedServiceItemImpl extends ASTWrapperPsiElement implements FsdDecoratedServiceItem {

  public FsdDecoratedServiceItemImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull FsdVisitor visitor) {
    visitor.visitDecoratedServiceItem(this);
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
  @Nullable
  public FsdDataSpec getDataSpec() {
    return findChildByClass(FsdDataSpec.class);
  }

  @Override
  @Nullable
  public FsdEnumSpec getEnumSpec() {
    return findChildByClass(FsdEnumSpec.class);
  }

  @Override
  @Nullable
  public FsdErrorSetSpec getErrorSetSpec() {
    return findChildByClass(FsdErrorSetSpec.class);
  }

  @Override
  @Nullable
  public FsdMethodSpec getMethodSpec() {
    return findChildByClass(FsdMethodSpec.class);
  }

}
