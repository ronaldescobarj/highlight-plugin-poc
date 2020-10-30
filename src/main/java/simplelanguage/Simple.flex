// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package simplelanguage;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import simplelanguage.psi.SimpleTypes;
import com.intellij.psi.TokenType;import java.io.FileReader;import java.util.ArrayList;

%%

%class SimpleLexer
%implements FlexLexer
%unicode
%char
%line
%function advance
%type IElementType
%eof{  return;
%eof}

%init{
      emulatedCsv = new ArrayList<>();
      emulatedCsv.add(0);
      emulatedCsv.add(5);
      emulatedCsv.add(10);
      emulatedCsv.add(15);
%init}

%{
  ArrayList<Integer> emulatedCsv;

  private IElementType getCorrespondingToken() {
      if (yyline < emulatedCsv.get(1)) {
          return SimpleTypes.LLAVE;
      } else if (yyline < emulatedCsv.get(2)) {
          return SimpleTypes.SEPARADOR;
      } else {
          return SimpleTypes.VALOR;
      }
    }
%}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
FIRST_VALUE_CHARACTER=[^ \n\f\\] | "\\"{CRLF} | "\\".
VALUE_CHARACTER=[^\n\f\\] | "\\"{CRLF} | "\\".
END_OF_LINE_COMMENT=("5#"|"!")[^\r\n]*
SEPARATOR=[:=]
KEY_CHARACTER=[^:=\ \n\t\f\\] | "\\ "

%state WAITING_VALUE

%%
<YYINITIAL> .                                               { yybegin(YYINITIAL); return getCorrespondingToken(); }

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return SimpleTypes.COMENTARIO; }

/*
<YYINITIAL> {KEY_CHARACTER}+                                { yybegin(YYINITIAL); return SimpleTypes.LLAVE; }

<YYINITIAL> {SEPARATOR}                                     { yybegin(WAITING_VALUE); return SimpleTypes.SEPARADOR; }

<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return SimpleTypes.VALOR; }
*/

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
