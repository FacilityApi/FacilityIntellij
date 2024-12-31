package io.github.facilityapi.intellij.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static io.github.facilityapi.intellij.psi.FsdTypes.*;

%%

%{
  public _FsdLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _FsdLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

COMMENT="//"[^\r\n]*
SPACE=[ \t\n\x0B\f\r]+
IDENTIFIER=[a-zA-Z_0-9]+
TYPENAME=[a-zA-Z_0-9]+
ATTRIBUTENAME=[a-zA-Z_0-9]+
ATTRIBUTEPARAMETERVALUE=\"(([^\"\\]+|\\[\"\\/bfnrt]|\\u[0-9a-fA-f]{4})*)\"|([0-9a-zA-Z.+_-]+)
NUMBER=[0-9]+(\.[0-9]*)?
MARKDOWNHEADING=#[^\r\n]*
MARKDOWNTEXT=.+

%%
<YYINITIAL> {
  {WHITE_SPACE}                   { return WHITE_SPACE; }

  "("                             { return LEFT_PAREN; }
  ")"                             { return RIGHT_PAREN; }
  "{"                             { return LEFT_BRACE; }
  "}"                             { return RIGHT_BRACE; }
  "["                             { return LEFT_BRACKET; }
  "]"                             { return RIGHT_BRACKET; }
  "<"                             { return LEFT_ANGLE; }
  ">"                             { return RIGHT_ANGLE; }
  ","                             { return COMMA; }
  ";"                             { return SEMI; }
  ":"                             { return COLON; }
  "!"                             { return BANG; }
  "service"                       { return SERVICE; }
  "extern"                        { return EXTERN; }
  "enum"                          { return ENUM; }
  "data"                          { return DATA; }
  "errors"                        { return ERRORS; }
  "method"                        { return METHOD; }
  "string"                        { return STRING; }
  "boolean"                       { return BOOLEAN; }
  "int32"                         { return INT32; }
  "int64"                         { return INT64; }
  "double"                        { return DOUBLE; }
  "decimal"                       { return DECIMAL; }
  "datetime"                      { return DATETIME; }
  "bytes"                         { return BYTES; }
  "object"                        { return OBJECT; }
  "map"                           { return MAP; }
  "result"                        { return RESULT; }
  "error"                         { return ERROR; }
  "nullable"                      { return NULLABLE; }

  {COMMENT}                       { return COMMENT; }
  {SPACE}                         { return SPACE; }
  {IDENTIFIER}                    { return IDENTIFIER; }
  {TYPENAME}                      { return TYPENAME; }
  {ATTRIBUTENAME}                 { return ATTRIBUTENAME; }
  {ATTRIBUTEPARAMETERVALUE}       { return ATTRIBUTEPARAMETERVALUE; }
  {NUMBER}                        { return NUMBER; }
  {MARKDOWNHEADING}               { return MARKDOWNHEADING; }
  {MARKDOWNTEXT}                  { return MARKDOWNTEXT; }

}

[^] { return BAD_CHARACTER; }
