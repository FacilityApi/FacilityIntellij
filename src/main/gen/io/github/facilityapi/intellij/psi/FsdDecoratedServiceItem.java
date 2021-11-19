// This is a generated file. Not intended for manual editing.
package io.github.facilityapi.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface FsdDecoratedServiceItem extends PsiElement {

  @NotNull
  List<FsdAttributeList> getAttributeListList();

  @Nullable
  FsdDataSpec getDataSpec();

  @Nullable
  FsdEnumSpec getEnumSpec();

  @Nullable
  FsdErrorSetSpec getErrorSetSpec();

  @Nullable
  FsdMethodSpec getMethodSpec();

}
