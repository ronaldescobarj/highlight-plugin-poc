// This is a generated file. Not intended for manual editing.
package simplelanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static simplelanguage.psi.SimpleTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import simplelanguage.psi.*;

public class SimpleDifflineImpl extends ASTWrapperPsiElement implements SimpleDiffline {

  public SimpleDifflineImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SimpleVisitor visitor) {
    visitor.visitDiffline(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SimpleVisitor) accept((SimpleVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public String getNotModified() {
    return SimplePsiImplUtil.getNotModified(this);
  }

  @Override
  public String getInserted() {
    return SimplePsiImplUtil.getInserted(this);
  }

  @Override
  public String getUpdated() {
    return SimplePsiImplUtil.getUpdated(this);
  }

  @Override
  public String getUpdatedMulitipleTimes() {
    return SimplePsiImplUtil.getUpdatedMulitipleTimes(this);
  }

  @Override
  public String getMoved() {
    return SimplePsiImplUtil.getMoved(this);
  }

}
