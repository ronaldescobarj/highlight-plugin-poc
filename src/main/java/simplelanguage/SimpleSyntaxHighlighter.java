package simplelanguage;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import simplelanguage.psi.SimpleTypes;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class SimpleSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey NOTMODIFIED =
            createTextAttributesKey("SIMPLE_NOTMODIFIED", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey INSERTED =
            createTextAttributesKey("SIMPLE_INSERTED", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey UPDATED =
            createTextAttributesKey("SIMPLE_UPDATED", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey MOVED =
            createTextAttributesKey("SIMPLE_MOVED", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);


    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] NOTMODIFIED_KEYS = new TextAttributesKey[]{NOTMODIFIED};
    private static final TextAttributesKey[] INSERTED_KEYS = new TextAttributesKey[]{INSERTED};
    private static final TextAttributesKey[] UPDATED_KEYS = new TextAttributesKey[]{UPDATED};
    private static final TextAttributesKey[] MOVED_KEYS = new TextAttributesKey[]{MOVED};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new SimpleLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(SimpleTypes.NOTMODIFIED)) {
            return NOTMODIFIED_KEYS;
        } else if (tokenType.equals(SimpleTypes.INSERTED)) {
            return INSERTED_KEYS;
        } else if (tokenType.equals(SimpleTypes.UPDATED)) {
            return UPDATED_KEYS;
        } else if (tokenType.equals(SimpleTypes.MOVED)) {
            return MOVED_KEYS;
        } else if (tokenType.equals(SimpleTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}