package difflogic;

import com.intellij.psi.tree.IElementType;
import simplelanguage.psi.SimpleTypes;

import java.util.HashMap;

public class DiffHighlighter {
    HashMap<Integer, String> diffMap;

    public DiffHighlighter() {

    }

    IElementType getLineHighlight(int line) {
        return SimpleTypes.SEPARADOR;
    }
}
