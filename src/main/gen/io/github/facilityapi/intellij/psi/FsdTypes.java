// This is a generated file. Not intended for manual editing.
package io.github.facilityapi.intellij.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import io.github.facilityapi.intellij.psi.impl.*;

public interface FsdTypes {

  IElementType ATTRIBUTE = new FsdElementType("ATTRIBUTE");
  IElementType ATTRIBUTE_LIST = new FsdElementType("ATTRIBUTE_LIST");
  IElementType ATTRIBUTE_PARAMETER = new FsdElementType("ATTRIBUTE_PARAMETER");
  IElementType DATA_SPEC = new FsdElementType("DATA_SPEC");
  IElementType DECORATED_ENUM_VALUE = new FsdElementType("DECORATED_ENUM_VALUE");
  IElementType DECORATED_ERROR_SPEC = new FsdElementType("DECORATED_ERROR_SPEC");
  IElementType DECORATED_FIELD = new FsdElementType("DECORATED_FIELD");
  IElementType DECORATED_SERVICE_ITEM = new FsdElementType("DECORATED_SERVICE_ITEM");
  IElementType ENUM_SPEC = new FsdElementType("ENUM_SPEC");
  IElementType ENUM_VALUE = new FsdElementType("ENUM_VALUE");
  IElementType ENUM_VALUE_LIST = new FsdElementType("ENUM_VALUE_LIST");
  IElementType ERROR_LIST = new FsdElementType("ERROR_LIST");
  IElementType ERROR_SET_SPEC = new FsdElementType("ERROR_SET_SPEC");
  IElementType ERROR_SPEC = new FsdElementType("ERROR_SPEC");
  IElementType FIELD = new FsdElementType("FIELD");
  IElementType IDENTIFIER_DECLARATION = new FsdElementType("IDENTIFIER_DECLARATION");
  IElementType MARKDOWN_REMARKS = new FsdElementType("MARKDOWN_REMARKS");
  IElementType METHOD_SPEC = new FsdElementType("METHOD_SPEC");
  IElementType REFERENCE_TYPE = new FsdElementType("REFERENCE_TYPE");
  IElementType SERVICE_ITEMS = new FsdElementType("SERVICE_ITEMS");
  IElementType SERVICE_SPEC = new FsdElementType("SERVICE_SPEC");
  IElementType TYPE = new FsdElementType("TYPE");
  IElementType TYPE_PARAMETER = new FsdElementType("TYPE_PARAMETER");

  IElementType ATTRIBUTENAME = new FsdElementType("attributename");
  IElementType ATTRIBUTEPARAMETERVALUE = new FsdElementType("attributeparametervalue");
  IElementType BANG = new FsdElementType("!");
  IElementType BOOLEAN = new FsdElementType("boolean");
  IElementType BYTES = new FsdElementType("bytes");
  IElementType COLON = new FsdElementType(":");
  IElementType COMMA = new FsdElementType(",");
  IElementType COMMENT = new FsdElementType("comment");
  IElementType DATA = new FsdElementType("data");
  IElementType DECIMAL = new FsdElementType("decimal");
  IElementType DOUBLE = new FsdElementType("double");
  IElementType ENUM = new FsdElementType("enum");
  IElementType ERROR = new FsdElementType("error");
  IElementType ERRORS = new FsdElementType("errors");
  IElementType IDENTIFIER = new FsdElementType("identifier");
  IElementType INT32 = new FsdElementType("int32");
  IElementType INT64 = new FsdElementType("int64");
  IElementType LEFT_ANGLE = new FsdElementType("<");
  IElementType LEFT_BRACE = new FsdElementType("{");
  IElementType LEFT_BRACKET = new FsdElementType("[");
  IElementType LEFT_PAREN = new FsdElementType("(");
  IElementType MAP = new FsdElementType("map");
  IElementType MARKDOWNHEADING = new FsdElementType("markdownheading");
  IElementType MARKDOWNTEXT = new FsdElementType("markdowntext");
  IElementType METHOD = new FsdElementType("method");
  IElementType NUMBER = new FsdElementType("number");
  IElementType OBJECT = new FsdElementType("object");
  IElementType RESULT = new FsdElementType("result");
  IElementType RIGHT_ANGLE = new FsdElementType(">");
  IElementType RIGHT_BRACE = new FsdElementType("}");
  IElementType RIGHT_BRACKET = new FsdElementType("]");
  IElementType RIGHT_PAREN = new FsdElementType(")");
  IElementType SEMI = new FsdElementType(";");
  IElementType SERVICE = new FsdElementType("service");
  IElementType STRING = new FsdElementType("string");
  IElementType TYPENAME = new FsdElementType("typename");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ATTRIBUTE) {
        return new FsdAttributeImpl(node);
      }
      else if (type == ATTRIBUTE_LIST) {
        return new FsdAttributeListImpl(node);
      }
      else if (type == ATTRIBUTE_PARAMETER) {
        return new FsdAttributeParameterImpl(node);
      }
      else if (type == DATA_SPEC) {
        return new FsdDataSpecImpl(node);
      }
      else if (type == DECORATED_ENUM_VALUE) {
        return new FsdDecoratedEnumValueImpl(node);
      }
      else if (type == DECORATED_ERROR_SPEC) {
        return new FsdDecoratedErrorSpecImpl(node);
      }
      else if (type == DECORATED_FIELD) {
        return new FsdDecoratedFieldImpl(node);
      }
      else if (type == DECORATED_SERVICE_ITEM) {
        return new FsdDecoratedServiceItemImpl(node);
      }
      else if (type == ENUM_SPEC) {
        return new FsdEnumSpecImpl(node);
      }
      else if (type == ENUM_VALUE) {
        return new FsdEnumValueImpl(node);
      }
      else if (type == ENUM_VALUE_LIST) {
        return new FsdEnumValueListImpl(node);
      }
      else if (type == ERROR_LIST) {
        return new FsdErrorListImpl(node);
      }
      else if (type == ERROR_SET_SPEC) {
        return new FsdErrorSetSpecImpl(node);
      }
      else if (type == ERROR_SPEC) {
        return new FsdErrorSpecImpl(node);
      }
      else if (type == FIELD) {
        return new FsdFieldImpl(node);
      }
      else if (type == IDENTIFIER_DECLARATION) {
        return new FsdIdentifierDeclarationImpl(node);
      }
      else if (type == MARKDOWN_REMARKS) {
        return new FsdMarkdownRemarksImpl(node);
      }
      else if (type == METHOD_SPEC) {
        return new FsdMethodSpecImpl(node);
      }
      else if (type == REFERENCE_TYPE) {
        return new FsdReferenceTypeImpl(node);
      }
      else if (type == SERVICE_ITEMS) {
        return new FsdServiceItemsImpl(node);
      }
      else if (type == SERVICE_SPEC) {
        return new FsdServiceSpecImpl(node);
      }
      else if (type == TYPE) {
        return new FsdTypeImpl(node);
      }
      else if (type == TYPE_PARAMETER) {
        return new FsdTypeParameterImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
