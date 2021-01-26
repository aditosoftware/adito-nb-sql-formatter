package de.adito.aditoweb.nbm.sqlformatter.impl.lexer;

/**
 * List of TokenTypes
 */
public enum ETokenType
{
  /**
   * A line comment e.g. // some comment
   */
  LINE_COMMENT(false),

  /**
   * A block comment
   */
  BLOCK_COMMENT(false),

  /**
   * A String e.g. "some text"
   */
  STRING(false),

  /**
   * A number e.g. 123
   */
  NUMBER(false),

  /**
   * A word e.g. CONTACT_ID
   */
  WORD(true),

  /**
   * A reserved word e.g. max
   */
  RESERVED(true),

  /**
   * A reserved toplevel word e.g. select
   */
  RESERVED_TOPLEVEL(true),

  /**
   * A reserved wrapping word e.g. where
   */
  RESERVED_WRAPPING(true),

  /**
   * A operator e.g. +
   */
  OPERATOR(false),

  /**
   * Any other character which isn't captured by any of the other rules
   */
  SYMBOL(false);

  private final boolean isText;

  ETokenType(boolean pIsText)
  {
    isText = pIsText;
  }

  public boolean isText()
  {
    return isText;
  }
}
