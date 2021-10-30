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

COMMENT="//"([^\r\n]*)(\r\n?|\n|\$)
IDENTIFIER=[a-zA-Z0-9_]+
ATTRIBUTEPARAMETERVALUE=\"(([^\"\\]+|\\[\"\\/bfnrt]|\\u[0-9a-fA-f]{4})*)\"|([0-9a-zA-Z.+_-]+)
NUMBER=[0-9]+(\.[0-9]*)?
MARKDOWN_HEADING=#[^\r\n]*
MARKDOWN_TEXT=.+

%state MARKDOWN_SECTION
%state SERVICE_ATTRIBUTE, SERVICE_ATTRIBUTE_PARAMETER_LIST, SERVICE_ATTRIBUTE_ARGUMENT
%state SERVICE_BODY, SERVICE_BODY_ATTRIBUTE, SERVICE_BODY_ATTRIBUTE_PARAMETER_LIST, SERVICE_BODY_ATTRIBUTE_ARGUMENT
%state METHOD_BODY, METHOD_BODY_TYPE, METHOD_BODY_TYPE_END, METHOD_BODY_ATTRIBUTE, METHOD_BODY_ATTRIBUTE_PARAMETER_LIST, METHOD_BODY_ATTRIBUTE_ARGUMENT
%state RESPONSE_SEPARATOR, RESPONSE_BODY, RESPONSE_BODY_TYPE, RESPONSE_BODY_TYPE_END, RESPONSE_BODY_ATTRIBUTE, RESPONSE_BODY_ATTRIBUTE_PARAMETER_LIST, RESPONSE_BODY_ATTRIBUTE_ARGUMENT
%state DATA_BODY, DATA_BODY_TYPE, DATA_BODY_TYPE_END, DATA_BODY_ATTRIBUTE, DATA_BODY_ATTRIBUTE_PARAMETER_LIST, DATA_BODY_ATTRIBUTE_ARGUMENT
%state LIST_BODY, LIST_BODY_ATTRIBUTE, LIST_BODY_ATTRIBUTE_PARAMETER_LIST, LIST_BODY_ATTRIBUTE_ARGUMENT

%%
<YYINITIAL> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "service"                      { yybegin(SERVICE_BODY); return SERVICE; }

  "["                            { yybegin(SERVICE_ATTRIBUTE); return LEFT_BRACKET; }
  "]"                            { return RIGHT_BRACKET; }

  {COMMENT}                      { return COMMENT; }
}

<SERVICE_ATTRIBUTE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "("                            { yybegin(SERVICE_ATTRIBUTE_PARAMETER_LIST); return LEFT_PAREN; }
  ")"                            { return RIGHT_PAREN; }

  {IDENTIFIER}                   { return ATTRIBUTENAME; }
  ,                              { return COMMA; }
  ]                              { yybegin(YYINITIAL); return RIGHT_BRACKET; }
}

<SERVICE_ATTRIBUTE_PARAMETER_LIST> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  ")"                            { yybegin(SERVICE_ATTRIBUTE); return RIGHT_PAREN; }
  ,                              { return COMMA; }

  {IDENTIFIER}                   { yybegin(SERVICE_ATTRIBUTE_ARGUMENT); return IDENTIFIER; }
}

<SERVICE_ATTRIBUTE_ARGUMENT> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }
  ":"                            { return COLON; }
  {ATTRIBUTEPARAMETERVALUE}      { yybegin(SERVICE_ATTRIBUTE_PARAMETER_LIST); return ATTRIBUTEPARAMETERVALUE; }
}

<SERVICE_BODY> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "method"                       { yybegin(METHOD_BODY); return METHOD; }
  "data"                         { yybegin(DATA_BODY); return DATA; }
  "errors"                       { yybegin(LIST_BODY); return ERRORS; }
  "enum"                         { yybegin(LIST_BODY); return ENUM; }

  "{"                            { return LEFT_BRACE; }
  "}"                            { yybegin(MARKDOWN_SECTION); return RIGHT_BRACE; }
  "["                            { yybegin(SERVICE_BODY_ATTRIBUTE); return LEFT_BRACKET; }
  "]"                            { return RIGHT_BRACKET; }

  {IDENTIFIER}                   { return IDENTIFIER; }
  {COMMENT}                      { return COMMENT; }
}

<SERVICE_BODY_ATTRIBUTE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "("                            { yybegin(SERVICE_BODY_ATTRIBUTE_PARAMETER_LIST); return LEFT_PAREN; }
  ")"                            { return RIGHT_PAREN; }

  {IDENTIFIER}                   { return ATTRIBUTENAME; }
  ,                              { return COMMA; }
  ]                              { yybegin(SERVICE_BODY); return RIGHT_BRACKET; }
}

<SERVICE_BODY_ATTRIBUTE_PARAMETER_LIST> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  ")"                            { yybegin(SERVICE_BODY_ATTRIBUTE); return RIGHT_PAREN; }
  ,                              { return COMMA; }

  {IDENTIFIER}                   { yybegin(SERVICE_BODY_ATTRIBUTE_ARGUMENT); return IDENTIFIER; }
}

<SERVICE_BODY_ATTRIBUTE_ARGUMENT> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }
  ":"                            { return COLON; }
  {ATTRIBUTEPARAMETERVALUE}      { yybegin(SERVICE_BODY_ATTRIBUTE_PARAMETER_LIST); return ATTRIBUTEPARAMETERVALUE; }
}

<METHOD_BODY> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "{"                            { return LEFT_BRACE; }
  "}"                            { yybegin(RESPONSE_SEPARATOR); return RIGHT_BRACE; }
  ":"                            { yybegin(METHOD_BODY_TYPE); return COLON; }
  "["                            { yybegin(METHOD_BODY_ATTRIBUTE); return LEFT_BRACKET; }
  "]"                            { return RIGHT_BRACKET; }
  {COMMENT}                      { return COMMENT; }
  {IDENTIFIER}                   { return IDENTIFIER; }
}

<METHOD_BODY_ATTRIBUTE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "("                            { yybegin(METHOD_BODY_ATTRIBUTE_PARAMETER_LIST); return LEFT_PAREN; }
  ")"                            { return RIGHT_PAREN; }

  {IDENTIFIER}                   { return ATTRIBUTENAME; }
  ,                              { return COMMA; }
  ]                              { yybegin(METHOD_BODY); return RIGHT_BRACKET; }
}

<METHOD_BODY_ATTRIBUTE_PARAMETER_LIST> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  ")"                            { yybegin(METHOD_BODY_ATTRIBUTE); return RIGHT_PAREN; }
  ,                              { return COMMA; }

  {IDENTIFIER}                   { yybegin(METHOD_BODY_ATTRIBUTE_ARGUMENT); return IDENTIFIER; }
}

<METHOD_BODY_ATTRIBUTE_ARGUMENT> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }
  ":"                            { return COLON; }
  {ATTRIBUTEPARAMETERVALUE}      { yybegin(METHOD_BODY_ATTRIBUTE_PARAMETER_LIST); return ATTRIBUTEPARAMETERVALUE; }
}

<METHOD_BODY_TYPE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "string"                       { yybegin(METHOD_BODY_TYPE_END); return STRING; }
  "boolean"                      { yybegin(METHOD_BODY_TYPE_END); return BOOLEAN; }
  "int32"                        { yybegin(METHOD_BODY_TYPE_END); return INT32; }
  "int64"                        { yybegin(METHOD_BODY_TYPE_END); return INT64; }
  "double"                       { yybegin(METHOD_BODY_TYPE_END); return DOUBLE; }
  "decimal"                      { yybegin(METHOD_BODY_TYPE_END); return DECIMAL; }
  "bytes"                        { yybegin(METHOD_BODY_TYPE_END); return BYTES; }
  "object"                       { yybegin(METHOD_BODY_TYPE_END); return OBJECT; }
  "map"                          { yybegin(METHOD_BODY_TYPE_END); return MAP; }
  "result"                       { yybegin(METHOD_BODY_TYPE_END); return RESULT; }
  "error"                        { yybegin(METHOD_BODY_TYPE_END); return ERROR; }

  {IDENTIFIER}                   { yybegin(METHOD_BODY_TYPE_END); return TYPENAME; }

  {COMMENT}                      { return COMMENT; }

  "}"                            { yybegin(RESPONSE_SEPARATOR); return RIGHT_BRACKET; }
}

<METHOD_BODY_TYPE_END> {
    {WHITE_SPACE}                  { return WHITE_SPACE; }
    {COMMENT}                      { return COMMENT; }

    "<"                            { yybegin(METHOD_BODY_TYPE); return LEFT_ANGLE; }
    ">"                            { return RIGHT_ANGLE; }
    "["                            { return LEFT_BRACKET; }
    "]"                            { return RIGHT_BRACKET; }

    ";"                            { yybegin(METHOD_BODY); return SEMI; }

    // Identifiers shouldn't be here, so break back into the body definition
    {IDENTIFIER}                   { yybegin(METHOD_BODY); return IDENTIFIER; }
    "}"                            { yybegin(RESPONSE_SEPARATOR); return RIGHT_BRACKET; }
}

<RESPONSE_SEPARATOR> {
  ":"                            { yybegin(RESPONSE_BODY); return COLON; }
}

<RESPONSE_BODY> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "{"                            { return LEFT_BRACE; }
  "}"                            { yybegin(SERVICE_BODY); return RIGHT_BRACE; }
  ":"                            { yybegin(RESPONSE_BODY_TYPE); return COLON; }
  "["                            { yybegin(RESPONSE_BODY_ATTRIBUTE); return LEFT_BRACKET; }
  "]"                            { return RIGHT_BRACKET; }
  {COMMENT}                      { return COMMENT; }
  {IDENTIFIER}                   { return IDENTIFIER; }
}

<RESPONSE_BODY_ATTRIBUTE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "("                            { yybegin(RESPONSE_BODY_ATTRIBUTE_PARAMETER_LIST); return LEFT_PAREN; }
  ")"                            { return RIGHT_PAREN; }

  {IDENTIFIER}                   { return ATTRIBUTENAME; }
  ,                              { return COMMA; }
  ]                              { yybegin(RESPONSE_BODY); return RIGHT_BRACKET; }
}

<RESPONSE_BODY_ATTRIBUTE_PARAMETER_LIST> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  ")"                            { yybegin(RESPONSE_BODY_ATTRIBUTE); return RIGHT_PAREN; }
  ,                              { return COMMA; }

  {IDENTIFIER}                   { yybegin(RESPONSE_BODY_ATTRIBUTE_ARGUMENT); return IDENTIFIER; }
}

<RESPONSE_BODY_ATTRIBUTE_ARGUMENT> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }
  ":"                            { return COLON; }
  {ATTRIBUTEPARAMETERVALUE}      { yybegin(RESPONSE_BODY_ATTRIBUTE_PARAMETER_LIST); return ATTRIBUTEPARAMETERVALUE; }
}

<RESPONSE_BODY_TYPE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "string"                       { yybegin(RESPONSE_BODY_TYPE_END); return STRING; }
  "boolean"                      { yybegin(RESPONSE_BODY_TYPE_END); return BOOLEAN; }
  "int32"                        { yybegin(RESPONSE_BODY_TYPE_END); return INT32; }
  "int64"                        { yybegin(RESPONSE_BODY_TYPE_END); return INT64; }
  "double"                       { yybegin(RESPONSE_BODY_TYPE_END); return DOUBLE; }
  "decimal"                      { yybegin(RESPONSE_BODY_TYPE_END); return DECIMAL; }
  "bytes"                        { yybegin(RESPONSE_BODY_TYPE_END); return BYTES; }
  "object"                       { yybegin(RESPONSE_BODY_TYPE_END); return OBJECT; }
  "map"                          { yybegin(RESPONSE_BODY_TYPE_END); return MAP; }
  "result"                       { yybegin(RESPONSE_BODY_TYPE_END); return RESULT; }
  "error"                        { yybegin(RESPONSE_BODY_TYPE_END); return ERROR; }

  {IDENTIFIER}                   { yybegin(RESPONSE_BODY_TYPE_END); return TYPENAME; }

  {COMMENT}                      { return COMMENT; }

  "}"                            { yybegin(SERVICE_BODY); return RIGHT_BRACKET; }
}

<RESPONSE_BODY_TYPE_END> {
    {WHITE_SPACE}                  { return WHITE_SPACE; }
    {COMMENT}                      { return COMMENT; }

    "<"                            { yybegin(RESPONSE_BODY_TYPE); return LEFT_ANGLE; }
    ">"                            { return RIGHT_ANGLE; }
    "["                            { return LEFT_BRACKET; }
    "]"                            { return RIGHT_BRACKET; }

    ";"                            { yybegin(RESPONSE_BODY); return SEMI; }

    // Identifiers shouldn't be here, so break back into the body definition
    {IDENTIFIER}                   { yybegin(RESPONSE_BODY); return IDENTIFIER; }
    "}"                            { yybegin(SERVICE_BODY); return RIGHT_BRACKET; }
}

<DATA_BODY> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "{"                            { return LEFT_BRACE; }
  "}"                            { yybegin(SERVICE_BODY); return RIGHT_BRACE; }
  ":"                            { yybegin(DATA_BODY_TYPE); return COLON; }
  "["                            { yybegin(DATA_BODY_ATTRIBUTE); return LEFT_BRACKET; }
  "]"                            { return RIGHT_BRACKET; }
  {COMMENT}                      { return COMMENT; }
  {IDENTIFIER}                   { return IDENTIFIER; }
}

<DATA_BODY_ATTRIBUTE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "("                            { yybegin(DATA_BODY_ATTRIBUTE_PARAMETER_LIST); return LEFT_PAREN; }
  ")"                            { return RIGHT_PAREN; }

  {IDENTIFIER}                   { return ATTRIBUTENAME; }
  ,                              { return COMMA; }
  ]                              { yybegin(DATA_BODY); return RIGHT_BRACKET; }
}

<DATA_BODY_ATTRIBUTE_PARAMETER_LIST> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  ")"                            { yybegin(DATA_BODY_ATTRIBUTE); return RIGHT_PAREN; }
  ,                              { return COMMA; }

  {IDENTIFIER}                   { yybegin(DATA_BODY_ATTRIBUTE_ARGUMENT); return IDENTIFIER; }
}

<DATA_BODY_ATTRIBUTE_ARGUMENT> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }
  ":"                            { return COLON; }
  {ATTRIBUTEPARAMETERVALUE}      { yybegin(DATA_BODY_ATTRIBUTE_PARAMETER_LIST); return ATTRIBUTEPARAMETERVALUE; }
}

<DATA_BODY_TYPE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "string"                       { yybegin(DATA_BODY_TYPE_END); return STRING; }
  "boolean"                      { yybegin(DATA_BODY_TYPE_END); return BOOLEAN; }
  "int32"                        { yybegin(DATA_BODY_TYPE_END); return INT32; }
  "int64"                        { yybegin(DATA_BODY_TYPE_END); return INT64; }
  "double"                       { yybegin(DATA_BODY_TYPE_END); return DOUBLE; }
  "decimal"                      { yybegin(DATA_BODY_TYPE_END); return DECIMAL; }
  "bytes"                        { yybegin(DATA_BODY_TYPE_END); return BYTES; }
  "object"                       { yybegin(DATA_BODY_TYPE_END); return OBJECT; }
  "map"                          { yybegin(DATA_BODY_TYPE_END); return MAP; }
  "result"                       { yybegin(DATA_BODY_TYPE_END); return RESULT; }
  "error"                        { yybegin(DATA_BODY_TYPE_END); return ERROR; }

  {IDENTIFIER}                   { yybegin(DATA_BODY_TYPE_END); return TYPENAME; }

  "}"                            { yybegin(SERVICE_BODY); return RIGHT_BRACKET; }
}

<DATA_BODY_TYPE_END> {
    {WHITE_SPACE}                  { return WHITE_SPACE; }
    {COMMENT}                      { return COMMENT; }

    "<"                            { yybegin(DATA_BODY_TYPE); return LEFT_ANGLE; }
    ">"                            { return RIGHT_ANGLE; }
    "["                            { return LEFT_BRACKET; }
    "]"                            { return RIGHT_BRACKET; }

    ";"                            { yybegin(DATA_BODY); return SEMI; }

    // Identifiers shouldn't be here, so break back into the body definition
    {IDENTIFIER}                   { yybegin(DATA_BODY); return IDENTIFIER; }
    "}"                            { yybegin(SERVICE_BODY); return RIGHT_BRACKET; }
}

<LIST_BODY> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "{"                            { return LEFT_BRACE; }
  "}"                            { yybegin(SERVICE_BODY); return RIGHT_BRACE; }
  ","                            { return COMMA; }
  "["                            { yybegin(LIST_BODY_ATTRIBUTE); return LEFT_BRACKET; }
  "]"                            { return RIGHT_BRACKET; }
  {COMMENT}                      { return COMMENT; }
  {IDENTIFIER}                   { return IDENTIFIER; }
}

<LIST_BODY_ATTRIBUTE> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "("                            { yybegin(LIST_BODY_ATTRIBUTE_PARAMETER_LIST); return LEFT_PAREN; }
  ")"                            { return RIGHT_PAREN; }

  {IDENTIFIER}                   { return ATTRIBUTENAME; }
  ,                              { return COMMA; }
  ]                              { yybegin(LIST_BODY); return RIGHT_BRACKET; }
}

<LIST_BODY_ATTRIBUTE_PARAMETER_LIST> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  ")"                            { yybegin(LIST_BODY_ATTRIBUTE); return RIGHT_PAREN; }
  ,                              { return COMMA; }

  {IDENTIFIER}                   { yybegin(LIST_BODY_ATTRIBUTE_ARGUMENT); return IDENTIFIER; }
}

<LIST_BODY_ATTRIBUTE_ARGUMENT> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }
  ":"                            { return COLON; }
  {ATTRIBUTEPARAMETERVALUE}      { yybegin(LIST_BODY_ATTRIBUTE_PARAMETER_LIST); return ATTRIBUTEPARAMETERVALUE; }
}

<MARKDOWN_SECTION> {
  \r                              { return WHITE_SPACE; }
  \n                              { return WHITE_SPACE; }
  {MARKDOWN_HEADING}              { return MARKDOWNHEADING; }
  {MARKDOWN_TEXT}                 { return MARKDOWNTEXT; }
}

[^] { return BAD_CHARACTER; }
