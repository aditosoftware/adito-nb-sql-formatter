package de.adito.aditoweb.nbm.sqlformatter.impl.formatting;

import de.adito.aditoweb.nbm.sqlformatter.api.*;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import de.adito.aditoweb.nbm.sqlformatter.impl.lexer.Token;
import de.adito.aditoweb.nbm.sqlformatter.impl.lexer.ETokenType;
import de.adito.aditoweb.nbm.sqlformatter.impl.lexer.Tokenizer;
import org.jetbrains.annotations.NotNull;

/**
 * Implements SQL-Formatting functionality
 *
 * @author p.neub, 01.12.2020
 */
public class Formatter implements IFormatter
{
  /**
   * The tokenizer holding the input SQL
   */
  private final ITokenizer tokenizer;

  /**
   * The settings which are needed for formatting the SQL
   */
  private final Settings settings;

  /**
   * The TextBuilder which is used to write the formatted SQL into a central object
   */
  private final TextBuilder text;

  /**
   * The current Token
   */
  private Token curr = null;

  /**
   * The last token
   */
  private Token last = null;

  private boolean parenListStyle = true;

  /**
   * Constructor of the Formatter
   *
   * @param pTokenizer The Tokenizer holding the input SQL
   * @param pSettings  The settings which are needed for formatting the SQL
   */
  public Formatter(@NotNull ITokenizer pTokenizer, @NotNull Settings pSettings)
  {
    tokenizer = pTokenizer;
    settings = pSettings;
    text = new TextBuilder(settings);
  }

  /**
   * This function gets called after each token has been written to the output TextBuilder
   * and is used for writing a proper spacing between each token
   * <p>
   * For example operator tokens need a space on both sides, but commas only need a space after the token
   */
  private void _writeSpacing()
  {
    if (!text.spacingAllowed())
      return;

    if (curr.check(ETokenType.SYMBOL, ";"))
      return;
    if (last.check(ETokenType.SYMBOL, ";"))
    {
      text.decIndent(Integer.MAX_VALUE);
      text.singleNewline();
      text.newline();
      text.newline();
      return;
    }

    if (curr.check(ETokenType.SYMBOL, ".") || last.check(ETokenType.SYMBOL, "."))
      return;
    if (curr.check(ETokenType.SYMBOL, ","))
      return;

    if (last.getType().isText() && curr.check(ETokenType.SYMBOL, "("))
      return;
    if(last.check(ETokenType.SYMBOL, "(")) {
      if(parenListStyle) {
        text.singleNewline();
      }
      return;
    }
    if (curr.check(ETokenType.SYMBOL, ")")) {
      text.singleNewline();
      text.decIndent(2);
      if (!parenListStyle)
        text.incIndent(0);
      parenListStyle = false;
      return;
    }

    if(curr.getType().isText() && last.check(ETokenType.SYMBOL, "`") ||
        last.getType().isText() && curr.check(ETokenType.SYMBOL, "`"))
      return;

    if (last.check(ETokenType.SYMBOL, ","))
      text.singleNewline();
    else
      text.write(" ");
  }

  /**
   * Implementation of the core formatting algorithm
   * <p>
   * This function loops through all tokens and decides if and how many spaces/line breaks
   * needs to be written before and after the token
   *
   * @return The formatted SQL
   */
  @NotNull
  public String format()
  {
    while (true)
    {
      curr = (Token) tokenizer.next();
      if (curr == null)
        return text.finish();
      _handle();

      if (curr.check(ETokenType.SYMBOL, "("))
      {
        if (last.getType().isText()) parenListStyle = true;
        else
        {
          text.decIndent(0);
          parenListStyle = false;
        }
        text.incIndent(2);
      }
      last = curr;
    }
  }

  private void _handle()
  {
    switch (curr.getType())
    {
      case RESERVED:
        _handleReserved();
        break;
      case RESERVED_TOPLEVEL:
        _handleReservedTopLevel();
        break;
      case RESERVED_WRAPPING:
        _handleReservedWrapping();
        break;
      default:
        _writeSpacing();
        text.write(curr.format(settings));
        break;
    }
  }

  private void _handleReserved()
  {
    _writeSpacing();
    switch (curr.getText().toUpperCase()) {
      case "CASE":
        text.write(curr.format(settings));
        text.incIndent(2);
        break;
      case "END":
        text.decIndent(2);
        text.singleNewline();
        text.write(curr.format(settings));
        break;
      default:
        text.write(curr.format(settings));
        break;
    }
  }

  private void _handleReservedTopLevel()
  {
    text.decIndent(0);
    if (last == null || !last.check(ETokenType.SYMBOL, "("))
      text.singleNewline();
    text.write(curr.format(settings));
    text.incIndent(0);
    text.singleNewline();
  }

  private void _handleReservedWrapping()
  {
    text.singleNewline();
    text.write(curr.format(settings));
  }
}
