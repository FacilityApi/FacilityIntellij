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
  // attributename [ attribute_parameters ]
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

  // [ attribute_parameters ]
  private static boolean attribute_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_1")) return false;
    attribute_parameters(b, l + 1);
    return true;
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
  // '(' attribute_parameter (',' attribute_parameter)* ')'
  public static boolean attribute_parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_parameters")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_PAREN);
    r = r && attribute_parameter(b, l + 1);
    r = r && attribute_parameters_2(b, l + 1);
    r = r && consumeToken(b, RIGHT_PAREN);
    exit_section_(b, m, ATTRIBUTE_PARAMETERS, r);
    return r;
  }

  // (',' attribute_parameter)*
  private static boolean attribute_parameters_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_parameters_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_parameters_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attribute_parameters_2", c)) break;
    }
    return true;
  }

  // ',' attribute_parameter
  private static boolean attribute_parameters_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_parameters_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && attribute_parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // data identifier_declaration field_list
  public static boolean data_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_spec")) return false;
    if (!nextTokenIs(b, DATA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DATA_SPEC, null);
    r = consumeToken(b, DATA);
    p = r; // pin = 1
    r = r && report_error_(b, identifier_declaration(b, l + 1));
    r = p && field_list(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // comment+ | attribute_list* enum_value
  public static boolean decorated_enum_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_enum_value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECORATED_ENUM_VALUE, "<decorated enum value>");
    r = decorated_enum_value_0(b, l + 1);
    if (!r) r = decorated_enum_value_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment+
  private static boolean decorated_enum_value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_enum_value_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "decorated_enum_value_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // attribute_list* enum_value
  private static boolean decorated_enum_value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_enum_value_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = decorated_enum_value_1_0(b, l + 1);
    r = r && enum_value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // attribute_list*
  private static boolean decorated_enum_value_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_enum_value_1_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "decorated_enum_value_1_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // comment+ | attribute_list* error_spec
  public static boolean decorated_error_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_error_spec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECORATED_ERROR_SPEC, "<decorated error spec>");
    r = decorated_error_spec_0(b, l + 1);
    if (!r) r = decorated_error_spec_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // comment+
  private static boolean decorated_error_spec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_error_spec_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "decorated_error_spec_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // attribute_list* error_spec
  private static boolean decorated_error_spec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_error_spec_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = decorated_error_spec_1_0(b, l + 1);
    r = r && error_spec(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // attribute_list*
  private static boolean decorated_error_spec_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_error_spec_1_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "decorated_error_spec_1_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // attribute_list* field
  public static boolean decorated_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_field")) return false;
    if (!nextTokenIs(b, "<decorated field>", IDENTIFIER, LEFT_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECORATED_FIELD, "<decorated field>");
    r = decorated_field_0(b, l + 1);
    r = r && field(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // attribute_list*
  private static boolean decorated_field_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_field_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "decorated_field_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // attribute_list* service_item
  public static boolean decorated_service_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_service_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECORATED_SERVICE_ITEM, "<decorated service item>");
    r = decorated_service_item_0(b, l + 1);
    r = r && service_item(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // attribute_list*
  private static boolean decorated_service_item_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "decorated_service_item_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "decorated_service_item_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (attribute_list* service_spec) markdown_remarks*
  static boolean definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition")) return false;
    if (!nextTokenIs(b, "", LEFT_BRACKET, SERVICE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = definition_0(b, l + 1);
    r = r && definition_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // attribute_list* service_spec
  private static boolean definition_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = definition_0_0(b, l + 1);
    r = r && service_spec(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // attribute_list*
  private static boolean definition_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_list(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "definition_0_0", c)) break;
    }
    return true;
  }

  // markdown_remarks*
  private static boolean definition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!markdown_remarks(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "definition_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // enum identifier_declaration enum_value_list
  public static boolean enum_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_spec")) return false;
    if (!nextTokenIs(b, ENUM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ENUM_SPEC, null);
    r = consumeToken(b, ENUM);
    p = r; // pin = 1
    r = r && report_error_(b, identifier_declaration(b, l + 1));
    r = p && enum_value_list(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // identifier
  public static boolean enum_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, ENUM_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // '{' [ decorated_enum_value (',' (decorated_enum_value | &'}'))* ] '}'
  public static boolean enum_value_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ENUM_VALUE_LIST, null);
    r = consumeToken(b, LEFT_BRACE);
    p = r; // pin = 1
    r = r && report_error_(b, enum_value_list_1(b, l + 1));
    r = p && consumeToken(b, RIGHT_BRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ decorated_enum_value (',' (decorated_enum_value | &'}'))* ]
  private static boolean enum_value_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_1")) return false;
    enum_value_list_1_0(b, l + 1);
    return true;
  }

  // decorated_enum_value (',' (decorated_enum_value | &'}'))*
  private static boolean enum_value_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = decorated_enum_value(b, l + 1);
    p = r; // pin = 1
    r = r && enum_value_list_1_0_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' (decorated_enum_value | &'}'))*
  private static boolean enum_value_list_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!enum_value_list_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enum_value_list_1_0_1", c)) break;
    }
    return true;
  }

  // ',' (decorated_enum_value | &'}')
  private static boolean enum_value_list_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_1_0_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && enum_value_list_1_0_1_0_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // decorated_enum_value | &'}'
  private static boolean enum_value_list_1_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_1_0_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = decorated_enum_value(b, l + 1);
    if (!r) r = enum_value_list_1_0_1_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'}'
  private static boolean enum_value_list_1_0_1_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_value_list_1_0_1_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RIGHT_BRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '{' [ decorated_error_spec (',' (decorated_error_spec | &'}'))* ] '}'
  public static boolean error_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ERROR_LIST, null);
    r = consumeToken(b, LEFT_BRACE);
    p = r; // pin = 1
    r = r && report_error_(b, error_list_1(b, l + 1));
    r = p && consumeToken(b, RIGHT_BRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ decorated_error_spec (',' (decorated_error_spec | &'}'))* ]
  private static boolean error_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_1")) return false;
    error_list_1_0(b, l + 1);
    return true;
  }

  // decorated_error_spec (',' (decorated_error_spec | &'}'))*
  private static boolean error_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = decorated_error_spec(b, l + 1);
    p = r; // pin = 1
    r = r && error_list_1_0_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' (decorated_error_spec | &'}'))*
  private static boolean error_list_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!error_list_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "error_list_1_0_1", c)) break;
    }
    return true;
  }

  // ',' (decorated_error_spec | &'}')
  private static boolean error_list_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_1_0_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && error_list_1_0_1_0_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // decorated_error_spec | &'}'
  private static boolean error_list_1_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_1_0_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = decorated_error_spec(b, l + 1);
    if (!r) r = error_list_1_0_1_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &'}'
  private static boolean error_list_1_0_1_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_list_1_0_1_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RIGHT_BRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // errors identifier_declaration error_list
  public static boolean error_set_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_set_spec")) return false;
    if (!nextTokenIs(b, ERRORS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ERROR_SET_SPEC, null);
    r = consumeToken(b, ERRORS);
    p = r; // pin = 1
    r = r && report_error_(b, identifier_declaration(b, l + 1));
    r = p && error_list(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // identifier
  public static boolean error_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "error_spec")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, ERROR_SPEC, r);
    return r;
  }

  /* ********************************************************** */
  // extern (data | enum) identifier ';'
  public static boolean extern_decl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extern_decl")) return false;
    if (!nextTokenIs(b, EXTERN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTERN);
    r = r && extern_decl_1(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, SEMI);
    exit_section_(b, m, EXTERN_DECL, r);
    return r;
  }

  // data | enum
  private static boolean extern_decl_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extern_decl_1")) return false;
    boolean r;
    r = consumeToken(b, DATA);
    if (!r) r = consumeToken(b, ENUM);
    return r;
  }

  /* ********************************************************** */
  // identifier ':' type [ '!' ] ';'
  public static boolean field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FIELD, null);
    r = consumeTokens(b, 0, IDENTIFIER, COLON);
    r = r && type(b, l + 1);
    p = r; // pin = 3
    r = r && report_error_(b, field_3(b, l + 1));
    r = p && consumeToken(b, SEMI) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [ '!' ]
  private static boolean field_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_3")) return false;
    consumeToken(b, BANG);
    return true;
  }

  /* ********************************************************** */
  // '{' (comment+ | decorated_field)* '}'
  static boolean field_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LEFT_BRACE);
    p = r; // pin = 1
    r = r && report_error_(b, field_list_1(b, l + 1));
    r = p && consumeToken(b, RIGHT_BRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (comment+ | decorated_field)*
  private static boolean field_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "field_list_1", c)) break;
    }
    return true;
  }

  // comment+ | decorated_field
  private static boolean field_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_list_1_0_0(b, l + 1);
    if (!r) r = decorated_field(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // comment+
  private static boolean field_list_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_list_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "field_list_1_0_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  public static boolean identifier_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_declaration")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, IDENTIFIER_DECLARATION, r);
    return r;
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
  // method identifier_declaration request_fields ':' response_fields
  public static boolean method_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_spec")) return false;
    if (!nextTokenIs(b, METHOD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, METHOD_SPEC, null);
    r = consumeToken(b, METHOD);
    p = r; // pin = 1
    r = r && report_error_(b, identifier_declaration(b, l + 1));
    r = p && report_error_(b, request_fields(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && response_fields(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // field_list
  public static boolean request_fields(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "request_fields")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_list(b, l + 1);
    exit_section_(b, m, REQUEST_FIELDS, r);
    return r;
  }

  /* ********************************************************** */
  // field_list
  public static boolean response_fields(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "response_fields")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = field_list(b, l + 1);
    exit_section_(b, m, RESPONSE_FIELDS, r);
    return r;
  }

  /* ********************************************************** */
  // extern_decl | enum_spec | data_spec | method_spec | error_set_spec
  static boolean service_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = extern_decl(b, l + 1);
    if (!r) r = enum_spec(b, l + 1);
    if (!r) r = data_spec(b, l + 1);
    if (!r) r = method_spec(b, l + 1);
    if (!r) r = error_set_spec(b, l + 1);
    exit_section_(b, l, m, r, false, FsdParser::service_item_recover);
    return r;
  }

  /* ********************************************************** */
  // !('[' | '}' | extern | enum | data | errors | method)
  static boolean service_item_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_item_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !service_item_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '[' | '}' | extern | enum | data | errors | method
  private static boolean service_item_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_item_recover_0")) return false;
    boolean r;
    r = consumeToken(b, LEFT_BRACKET);
    if (!r) r = consumeToken(b, RIGHT_BRACE);
    if (!r) r = consumeToken(b, EXTERN);
    if (!r) r = consumeToken(b, ENUM);
    if (!r) r = consumeToken(b, DATA);
    if (!r) r = consumeToken(b, ERRORS);
    if (!r) r = consumeToken(b, METHOD);
    return r;
  }

  /* ********************************************************** */
  // ('{' decorated_service_item* '}') | decorated_service_item
  public static boolean service_items(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_items")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SERVICE_ITEMS, "<service items>");
    r = service_items_0(b, l + 1);
    if (!r) r = decorated_service_item(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '{' decorated_service_item* '}'
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

  // decorated_service_item*
  private static boolean service_items_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_items_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!decorated_service_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "service_items_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // service identifier service_items
  public static boolean service_spec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "service_spec")) return false;
    if (!nextTokenIs(b, SERVICE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SERVICE_SPEC, null);
    r = consumeTokens(b, 1, SERVICE, IDENTIFIER);
    p = r; // pin = 1
    r = r && service_items(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (string | boolean | int32 | int64 | double | decimal | datetime | bytes | object | map | result | error | nullable | reference_type) [ type_parameter ] [ ('['']')* ]
  public static boolean type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE, "<type>");
    r = type_0(b, l + 1);
    r = r && type_1(b, l + 1);
    r = r && type_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // string | boolean | int32 | int64 | double | decimal | datetime | bytes | object | map | result | error | nullable | reference_type
  private static boolean type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_0")) return false;
    boolean r;
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = consumeToken(b, INT32);
    if (!r) r = consumeToken(b, INT64);
    if (!r) r = consumeToken(b, DOUBLE);
    if (!r) r = consumeToken(b, DECIMAL);
    if (!r) r = consumeToken(b, DATETIME);
    if (!r) r = consumeToken(b, BYTES);
    if (!r) r = consumeToken(b, OBJECT);
    if (!r) r = consumeToken(b, MAP);
    if (!r) r = consumeToken(b, RESULT);
    if (!r) r = consumeToken(b, ERROR);
    if (!r) r = consumeToken(b, NULLABLE);
    if (!r) r = reference_type(b, l + 1);
    return r;
  }

  // [ type_parameter ]
  private static boolean type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_1")) return false;
    type_parameter(b, l + 1);
    return true;
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
  // '<' type '>'
  public static boolean type_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_parameter")) return false;
    if (!nextTokenIs(b, LEFT_ANGLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TYPE_PARAMETER, null);
    r = consumeToken(b, LEFT_ANGLE);
    r = r && type(b, l + 1);
    p = r; // pin = 2
    r = r && consumeToken(b, RIGHT_ANGLE);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
