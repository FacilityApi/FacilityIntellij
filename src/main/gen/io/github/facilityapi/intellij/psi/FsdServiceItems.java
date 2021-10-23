// This is a generated file. Not intended for manual editing.
package io.github.facilityapi.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface FsdServiceItems extends PsiElement {

  @NotNull
  List<FsdDtoSpec> getDtoSpecList();

  @NotNull
  List<FsdEnumSpec> getEnumSpecList();

  @NotNull
  List<FsdErrorSetSpec> getErrorSetSpecList();

  @NotNull
  List<FsdMethodSpec> getMethodSpecList();

}
