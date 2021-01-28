package de.adito.aditoweb.nbm.sqlformatter.impl.lexer;

import de.adito.aditoweb.nbm.sqlformatter.impl.formatting.Formatter;
import org.jetbrains.annotations.*;

import java.util.function.*;

/**
 * List of TokenTypes
 */
public enum ETokenType
{
  /**
   * A line comment e.g. // some comment
   */
  LINE_COMMENT(null, Formatter::handleDefault),

  /**
   * A block comment
   */
  BLOCK_COMMENT(null, Formatter::handleDefault),

  /**
   * A String e.g. "some text"
   */
  STRING(null, Formatter::handleDefault),

  /**
   * A number e.g. 123
   */
  NUMBER(null, Formatter::handleDefault),

  /**
   * A word e.g. CONTACT_ID
   */
  WORD(false, Formatter::handleDefault),

  /**
   * A reserved word e.g. max
   */
  KEYWORD(true, Formatter::handleKeyword),

  /**
   * A reserved toplevel word e.g. select
   */
  KW_TOPLEVEL(true, Formatter::handleKWTopLevel),

  /**
   * A reserved lazytoplevel word e.g. 'from' or any form of 'join'
   */
  KW_LAZYTOPLEVEL(true, Formatter::handleKWLazyTopLevel),

  /**
   * A reserved wrapping word e.g. where
   */
  KW_WRAPPING(true, Formatter::handleKWWrapping),

  /**
   * A opening symbol e.g. '(' or '['
   */
  OPEN(null, Formatter::handleOpen),

  /**
   * A closing symbol e.g. ')' or ']'
   */
  CLOSE(null, Formatter::handleClose),

  /**
   * A operator e.g. +
   */
  OPERATOR(null, Formatter::handleOperator),

  /**
   * Any other character which isn't captured by any of the other rules
   */
  SYMBOL(null, Formatter::handleSymbol),

  /**
   * Indicates the EndOfFile
   * Returned if no more text is aviable for lexing
   */
  EOF(null, Formatter::handleDefault);

  public final Boolean isKeyword;
  public final Consumer<Formatter> formattingHandler;

  ETokenType(@Nullable Boolean pIsKeyword, @NotNull Consumer<Formatter> pFormattingHandler)
  {
    isKeyword = pIsKeyword;
    formattingHandler = pFormattingHandler;
  }
}
