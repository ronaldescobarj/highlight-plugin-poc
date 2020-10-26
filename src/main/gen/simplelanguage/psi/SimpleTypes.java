// This is a generated file. Not intended for manual editing.
package simplelanguage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import simplelanguage.psi.impl.*;

public interface SimpleTypes {

  IElementType PROPERTY = new SimpleElementType("PROPERTY");

  IElementType COMENTARIO = new SimpleTokenType("COMENTARIO");
  IElementType FINDELINEA = new SimpleTokenType("FINDELINEA");
  IElementType LLAVE = new SimpleTokenType("LLAVE");
  IElementType SEPARADOR = new SimpleTokenType("SEPARADOR");
  IElementType VALOR = new SimpleTokenType("VALOR");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new SimplePropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
