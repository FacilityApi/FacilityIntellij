// This is a generated file. Not intended for manual editing.
package io.github.facilityapi.intellij.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static io.github.facilityapi.intellij.psi.FsdTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class FsdParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return definition(b, l + 1);
  }

  /* ********************************************************** */
  // attributename [ '(' attribute_parameter (',' attribute_parameter)* ')' ]
  public static boolean attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute")) return false;
    if (!nextTokenIs(b, ATTRIBUTENAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ATTRIBUTENAME);
    r = r && attribute_1(b, l + 1);
    exit_section_(b, m, ATTRIBUTE, r);
    return r;
  }

  // [ '(' attribute_parameter (',' attribute_parameter)* ')' ]
  private static boolean attribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1")) return false;
    attribute_1_0(b, l + 1);
    return true;
  }

  // '(' attribute_parameter (',' attribute_parameter)* ')'
  private static boolean attribute_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_PAREN);
    r = r && attribute_parameter(b, l + 1);
    r = r && attribute_1_0_2(b, l + 1);
    r = r && consumeToken(b, RIGHT_PAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' attribute_parameter)*
  private static boolean attribute_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attribute_1_0_2", c)) break;
    }
    return true;
  }

  // ',' attribute_parameter
  private static boolean attribute_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && attribute_parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '[' attribute (',' attribute)* ']'
  public static boolean attribute_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_list")) return false;
    if (!nextTokenIs(b, LEFT_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_BRACKET);
    r = r && attribute(b, l + 1);
    r = r && attribute_list_2(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACKET);
    exit_section_(b, m, ATTRIBUTE_LIST, r);
    return r;
  }

  // (',' attribute)*
  private static boolean attribute_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_list_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attribute_list_2", c)) break;
    }
    return true;
  }

  // ',' attribute
  private static boolean attribute_list_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_list_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && attribute(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier ':' attribute_parameter_value
  public static boolean attribute_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_parameter")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENTIFIER, COLON);
    r = r && attribute_parameter_value(b, l + 1);
    exit_section_(b, m, ATTRIBUTE_PARAMETER, r);
    return r;
  }

  /* ********************************************************** */
  // attributeparametervalue
  static boolean attribute_parameter_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_parameter_value")) return false;
    if (!nextTokenIs(b, "<attribute parameter value>", ATTRIBUTEPARAMETERVALUE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<attribute parameter value>");
    r = consumeToken(b, ATTRIBUTEPARAMETERVALUE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* data type_identifier '{' field* '}'
  public static boolean data_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_spec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DATA_SPEC, "<data spec>");
    r = data_spec_0(b, l + 1);
    r = r && data_spec_1(b, l + 1);
    r = r && data_spec_2(b, l + 1);
    r = r && consumeToken(b, DATA);
    r = r && type_identifier(b, l + 1);
    r = r && consumeToken(b, LEFT_BRACE);
    r = r && data_spec_6(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean data_spec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_spec_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "data_spec_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean data_spec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_spec_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "data_spec_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean data_spec_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_spec_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "data_spec_2", c)) break;
    }
    return true;
  }

  // field*
  private static boolean data_spec_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_spec_6")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "data_spec_6", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // service_spec comment* markdown_remarks*
  static boolean definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = service_spec(b, l + 1);
    r = r && definition_1(b, l + 1);
    r = r && definition_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // comment*
  private static boolean definition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "definition_1", c)) break;
    }
    return true;
  }

  // markdown_remarks*
  private static boolean definition_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!markdown_remarks(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "definition_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* enum type_identifier enum_value_list
  public static boolean enum_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_spec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENUM_SPEC, "<enum spec>");
    r = enum_spec_0(b, l + 1);
    r = r && enum_spec_1(b, l + 1);
    r = r && enum_spec_2(b, l + 1);
    r = r && consumeToken(b, ENUM);
    r = r && type_identifier(b, l + 1);
    r = r && enum_value_list(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean enum_spec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_spec_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "enum_spec_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean enum_spec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_spec_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enum_spec_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean enum_spec_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_spec_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "enum_spec_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* identifier
  public static boolean enum_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENUM_VALUE, "<enum value>");
    r = enum_value_0(b, l + 1);
    r = r && enum_value_1(b, l + 1);
    r = r && enum_value_2(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean enum_value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "enum_value_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean enum_value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enum_value_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean enum_value_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "enum_value_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // '{' enum_value (',' (enum_value | &'}'))* '}'
  public static boolean enum_value_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ENUM_VALUE_LIST, null);
    r = consumeToken(b, LEFT_BRACE);
    p = r; // pin = 1
    r = r && report_error_(b, enum_value(b, l + 1));
    r = p && report_error_(b, enum_value_list_2(b, l + 1)) && r;
    r = p && consumeToken(b, RIGHT_BRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' (enum_value | &'}'))*
  private static boolean enum_value_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!enum_value_list_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enum_value_list_2", c)) break;
    }
    return true;
  }

  // ',' (enum_value | &'}')
  private static boolean enum_value_list_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && enum_value_list_2_0_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // enum_value | &'}'
  private static boolean enum_value_list_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = enum_value(b, l + 1);
    if (!r) r = enum_value_list_2_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'}'
  private static boolean enum_value_list_2_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_2_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RIGHT_BRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '{' error_spec (',' (error_spec | &'}'))* '}'
  public static boolean error_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ERROR_LIST, null);
    r = consumeToken(b, LEFT_BRACE);
    p = r; // pin = 1
    r = r && report_error_(b, error_spec(b, l + 1));
    r = p && report_error_(b, error_list_2(b, l + 1)) && r;
    r = p && consumeToken(b, RIGHT_BRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' (error_spec | &'}'))*
  private static boolean error_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!error_list_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "error_list_2", c)) break;
    }
    return true;
  }

  // ',' (error_spec | &'}')
  private static boolean error_list_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && error_list_2_0_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // error_spec | &'}'
  private static boolean error_list_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = error_spec(b, l + 1);
    if (!r) r = error_list_2_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'}'
  private static boolean error_list_2_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_2_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RIGHT_BRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* errors type_identifier error_list
  public static boolean error_set_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_set_spec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ERROR_SET_SPEC, "<error set spec>");
    r = error_set_spec_0(b, l + 1);
    r = r && error_set_spec_1(b, l + 1);
    r = r && error_set_spec_2(b, l + 1);
    r = r && consumeToken(b, ERRORS);
    r = r && type_identifier(b, l + 1);
    r = r && error_list(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean error_set_spec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_set_spec_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "error_set_spec_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean error_set_spec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_set_spec_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "error_set_spec_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean error_set_spec_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_set_spec_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "error_set_spec_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* identifier
  public static boolean error_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_spec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ERROR_SPEC, "<error spec>");
    r = error_spec_0(b, l + 1);
    r = r && error_spec_1(b, l + 1);
    r = r && error_spec_2(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean error_spec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_spec_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "error_spec_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean error_spec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_spec_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "error_spec_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean error_spec_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_spec_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "error_spec_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* identifier ':' type ';'
  public static boolean field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD, "<field>");
    r = field_0(b, l + 1);
    r = r && field_1(b, l + 1);
    r = r && field_2(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, COLON);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, SEMI);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean field_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "field_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean field_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "field_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean field_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "field_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // markdownheading | markdowntext
  public static boolean markdown_remarks(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "markdown_remarks")) return false;
    if (!nextTokenIs(b, "<markdown remarks>", MARKDOWNHEADING, MARKDOWNTEXT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MARKDOWN_REMARKS, "<markdown remarks>");
    r = consumeToken(b, MARKDOWNHEADING);
    if (!r) r = consumeToken(b, MARKDOWNTEXT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* method identifier '{' field* '}' ':' '{' field* '}'
  public static boolean method_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_spec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_SPEC, "<method spec>");
    r = method_spec_0(b, l + 1);
    r = r && method_spec_1(b, l + 1);
    r = r && method_spec_2(b, l + 1);
    r = r && consumeTokens(b, 0, METHOD, IDENTIFIER, LEFT_BRACE);
    r = r && method_spec_6(b, l + 1);
    r = r && consumeTokens(b, 0, RIGHT_BRACE, COLON, LEFT_BRACE);
    r = r && method_spec_10(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean method_spec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_spec_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "method_spec_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean method_spec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_spec_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_spec_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean method_spec_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_spec_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "method_spec_2", c)) break;
    }
    return true;
  }

  // field*
  private static boolean method_spec_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_spec_6")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_spec_6", c)) break;
    }
    return true;
  }

  // field*
  private static boolean method_spec_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_spec_10")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_spec_10", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // typename
  public static boolean reference_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reference_type")) return false;
    if (!nextTokenIs(b, TYPENAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPENAME);
    exit_section_(b, m, REFERENCE_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // enum_spec | data_spec | method_spec | error_set_spec
  static boolean service_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_item")) return false;
    boolean r;
    r = enum_spec(b, l + 1);
    if (!r) r = data_spec(b, l + 1);
    if (!r) r = method_spec(b, l + 1);
    if (!r) r = error_set_spec(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // '{' service_item* '}' | service_item
  public static boolean service_items(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_items")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SERVICE_ITEMS, "<service items>");
    r = service_items_0(b, l + 1);
    if (!r) r = service_item(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '{' service_item* '}'
  private static boolean service_items_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_items_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_BRACE);
    r = r && service_items_0_1(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // service_item*
  private static boolean service_items_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_items_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!service_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "service_items_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // comment* attribute_list* comment* service identifier service_items
  public static boolean service_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_spec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SERVICE_SPEC, "<service spec>");
    r = service_spec_0(b, l + 1);
    r = r && service_spec_1(b, l + 1);
    r = r && service_spec_2(b, l + 1);
    r = r && consumeTokens(b, 0, SERVICE, IDENTIFIER);
    r = r && service_items(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment*
  private static boolean service_spec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_spec_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "service_spec_0", c)) break;
    }
    return true;
  }

  // attribute_list*
  private static boolean service_spec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_spec_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "service_spec_1", c)) break;
    }
    return true;
  }

  // comment*
  private static boolean service_spec_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_spec_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "service_spec_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (string | boolean | int32 | int64 | double | decimal | bytes | object | map | result | error | reference_type) [ '<' type '>' ] [ ('['']')* ]
  public static boolean type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, TYPE, "<type>");
    r = type_0(b, l + 1);
    r = r && type_1(b, l + 1);
    r = r && type_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // string | boolean | int32 | int64 | double | decimal | bytes | object | map | result | error | reference_type
  private static boolean type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0")) return false;
    boolean r;
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = consumeToken(b, INT32);
    if (!r) r = consumeToken(b, INT64);
    if (!r) r = consumeToken(b, DOUBLE);
    if (!r) r = consumeToken(b, DECIMAL);
    if (!r) r = consumeToken(b, BYTES);
    if (!r) r = consumeToken(b, OBJECT);
    if (!r) r = consumeToken(b, MAP);
    if (!r) r = consumeToken(b, RESULT);
    if (!r) r = consumeToken(b, ERROR);
    if (!r) r = reference_type(b, l + 1);
    return r;
  }

  // [ '<' type '>' ]
  private static boolean type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_1")) return false;
    type_1_0(b, l + 1);
    return true;
  }

  // '<' type '>'
  private static boolean type_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_ANGLE);
    r = r && type(b, l + 1);
    r = r && consumeToken(b, RIGHT_ANGLE);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ ('['']')* ]
  private static boolean type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_2")) return false;
    type_2_0(b, l + 1);
    return true;
  }

  // ('['']')*
  private static boolean type_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_2_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!type_2_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_2_0", c)) break;
    }
    return true;
  }

  // '['']'
  private static boolean type_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LEFT_BRACKET, RIGHT_BRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  public static boolean type_identifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_identifier")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, TYPE_IDENTIFIER, r);
    return r;
  }

}
