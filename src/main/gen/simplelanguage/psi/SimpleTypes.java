// This is a generated file. Not intended for manual editing.
package simplelanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import simplelanguage.psi.impl.*;

public interface SimpleTypes {

  IElementType DIFFLINE = new SimpleElementType("DIFFLINE");

  IElementType COMMENT = new SimpleTokenType("COMMENT");
  IElementType CRLF = new SimpleTokenType("CRLF");
  IElementType INSERTED = new SimpleTokenType("INSERTED");
  IElementType MOVED = new SimpleTokenType("MOVED");
  IElementType NOTMODIFIED = new SimpleTokenType("NOTMODIFIED");
  IElementType UPDATED = new SimpleTokenType("UPDATED");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == DIFFLINE) {
        return new SimpleDifflineImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
