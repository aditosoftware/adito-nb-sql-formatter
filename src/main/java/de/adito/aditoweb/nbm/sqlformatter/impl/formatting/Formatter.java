package de.adito.aditoweb.nbm.sqlformatter.impl.formatting;

import de.adito.aditoweb.nbm.sqlformatter.api.*;
import de.adito.aditoweb.nbm.sqlformatter.impl.lexer.*;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
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
  private final ITokenizer<Token> tokenizer;

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
   * Constructor of the Formatter
   *
   * @param pTokenizer The Tokenizer holding the input SQL
   * @param pSettings  The settings which are needed for formatting the SQL
   */
  public Formatter(@NotNull ITokenizer<Token> pTokenizer, @NotNull Settings pSettings)
  {
    tokenizer = pTokenizer;
    settings = pSettings;
    text = new TextBuilder(settings);
  }

  /**
   * Entry point of the formatting algorithm
   *
   * This function loops through all tokens and sets 'curr'
   * It also finishes if curr is an EOF Token
   * It also calls '_handle' each cycle
   *
   * @return The formatted SQL
   */
  @NotNull
  public String format()
  {
    while (true)
    {
      curr = tokenizer.next();
      if (curr.getType() == ETokenType.EOF)
        return text.finish();
      curr.getType().formattingHandler.accept(this);
    }
  }

  public static void handleOpen(@NotNull Formatter pFmt)
  {
    pFmt.text.write(pFmt.curr.getText());
    pFmt.text.incIndent(EIndentLevel.BLOCK);
    pFmt.text.singleNewline();
  }

  public static void handleClose(@NotNull Formatter pFmt)
  {
    pFmt.text.decIndent(EIndentLevel.BLOCK);
    pFmt.text.singleNewline();
    pFmt.text.write(pFmt.curr.getText());
  }

  public static void handleOperator(@NotNull Formatter pFmt)
  {
    pFmt.text.singleSpace();
    pFmt.text.write(pFmt.curr.getText());
  }

  public static void handleSymbol(@NotNull Formatter pFmt)
  {
    switch (pFmt.curr.getText())
    {
      case ".":
        pFmt.text.write(pFmt.curr.getText());
        pFmt.text.noSpace();
        break;
      case ",":
        pFmt.text.write(pFmt.curr.getText());
        pFmt.text.singleNewline();
        break;
      case ";":
        pFmt.text.write(pFmt.curr.getText());
        pFmt.text.singleNewline();
        pFmt. text.newline();
        break;
      default:
        pFmt.text.write(pFmt.curr.getText());
        break;
    }
  }

  public static void handleKeyword(@NotNull Formatter pFmt)
  {
    switch (pFmt.curr.getText().toUpperCase())
    {
      case "CASE":
        pFmt.text.write(pFmt.curr.format(pFmt.settings));
        pFmt.text.incIndent(EIndentLevel.SWITCH);
        break;
      case "END":
        pFmt.text.decIndent(EIndentLevel.SWITCH);
        pFmt.text.singleNewline();
        pFmt.text.write(pFmt.curr.format(pFmt.settings));
        break;
      default:
        handleDefault(pFmt);
        break;
    }
  }

  public static void handleKWTopLevel(@NotNull Formatter pFmt)
  {
    pFmt.text.singleNewline();
    pFmt.text.decIndent(EIndentLevel.KEYWORD);
    pFmt.text.write(pFmt.curr.format(pFmt.settings));
    pFmt.text.singleNewline();
    pFmt.text.incIndent(EIndentLevel.KEYWORD);
  }

  public static void handleKWLazyTopLevel(@NotNull Formatter pFmt)
  {
    pFmt.text.singleNewline();
    pFmt.text.decIndent(EIndentLevel.KEYWORD);
    pFmt.text.write(pFmt.curr.format(pFmt.settings));

    pFmt.curr = pFmt.tokenizer.next();
    pFmt.curr.getType().formattingHandler.accept(pFmt);

    pFmt.text.singleNewline();
    pFmt.text.incIndent(EIndentLevel.KEYWORD);
  }

  public static void handleKWWrapping(@NotNull Formatter pFmt)
  {
    pFmt.text.singleNewline();
    pFmt.text.write(pFmt.curr.format(pFmt.settings));
  }

  public static void handleDefault(@NotNull Formatter pFmt)
  {
    pFmt.text.singleSpace();
    pFmt.text.write(pFmt.curr.format(pFmt.settings));
  }
}
