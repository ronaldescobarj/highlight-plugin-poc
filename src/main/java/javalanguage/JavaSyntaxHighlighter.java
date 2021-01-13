package javalanguage;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import javalanguage.psi.JavaTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class JavaSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey FIRSTTYPE =
            createTextAttributesKey("JAVA_FIRSTTYPE", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey SECONDTYPE =
            createTextAttributesKey("JAVA_SECONDTYPE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("JAVA_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("JAVA_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);


    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] FIRSTTYPE_KEYS = new TextAttributesKey[]{FIRSTTYPE};
    private static final TextAttributesKey[] SECONDTYPE_KEYS = new TextAttributesKey[]{SECONDTYPE};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new JavaLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(JavaTypes.FIRSTTYPE)) {
            return FIRSTTYPE_KEYS;
        } else if (tokenType.equals(JavaTypes.SECONDTYPE)) {
            return SECONDTYPE_KEYS;
        } else if (tokenType.equals(JavaTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
