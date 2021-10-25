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

public class FsdServiceItemsImpl extends ASTWrapperPsiElement implements FsdServiceItems {

  public FsdServiceItemsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull FsdVisitor visitor) {
    visitor.visitServiceItems(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof FsdVisitor) accept((FsdVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<FsdDataSpec> getDataSpecList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, FsdDataSpec.class);
  }

  @Override
  @NotNull
  public List<FsdEnumSpec> getEnumSpecList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, FsdEnumSpec.class);
  }

  @Override
  @NotNull
  public List<FsdErrorSetSpec> getErrorSetSpecList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, FsdErrorSetSpec.class);
  }

  @Override
  @NotNull
  public List<FsdMethodSpec> getMethodSpecList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, FsdMethodSpec.class);
  }

}
