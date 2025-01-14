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

public class FsdEventSpecImpl extends ASTWrapperPsiElement implements FsdEventSpec {

  public FsdEventSpecImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull FsdVisitor visitor) {
    visitor.visitEventSpec(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof FsdVisitor) accept((FsdVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public FsdIdentifierDeclaration getIdentifierDeclaration() {
    return findChildByClass(FsdIdentifierDeclaration.class);
  }

  @Override
  @Nullable
  public FsdRequestFields getRequestFields() {
    return findChildByClass(FsdRequestFields.class);
  }

  @Override
  @Nullable
  public FsdResponseFields getResponseFields() {
    return findChildByClass(FsdResponseFields.class);
  }

}
