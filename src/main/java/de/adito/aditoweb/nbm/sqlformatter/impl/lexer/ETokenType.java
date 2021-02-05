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
   * A reserved word e.g. in
   */
  KEYWORD(true, Formatter::handleKeyword),

  /**
   * SQL-Function keyword e.g. count
   */
  FUNCTION_KEYWORD(true, Formatter::handleFunctionKeyword),

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
   * A operator e.g. +
   */
  OPERATOR(null, Formatter::handleDefault),

  /**
   * Any other character which isn't captured by any of the other rules
   */
  SYMBOL(null, Formatter::handleSymbol),

  /**
   * Indicates the EndOfFile
   * Returned if no more text is aviable for lexing
   */
  EOF(null, Formatter::handleDefault);

  /**
   * Null = No formatting is needed
   * False = Should be formatted like a word
   * True = Should be formatted like a keyword
   */
  public final Boolean isKeyword;

  /**
   * Defines the formatting handler
   */
  public final Consumer<Formatter> formattingHandler;

  /**
   * Constructs a new ETokenType
   *
   * @param pIsKeyword         defines the way how this token should be formatted
   * @param pFormattingHandler defines the entry point for formatting this token
   */
  ETokenType(@Nullable Boolean pIsKeyword, @NotNull Consumer<Formatter> pFormattingHandler)
  {
    isKeyword = pIsKeyword;
    formattingHandler = pFormattingHandler;
  }
}
