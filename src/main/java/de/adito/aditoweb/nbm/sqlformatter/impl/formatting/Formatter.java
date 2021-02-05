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
  private final TextBuilder<EIndentLevel> text;

  /**
   * The current Token
   */
  private Token curr = null;

  /**
   * Constructor of the Formatter
   *
   * @param pInputSQL The input SQL
   * @param pSettings The settings which are needed for formatting the SQL
   */
  public Formatter(@NotNull String pInputSQL, @NotNull Settings pSettings)
  {
    this(pInputSQL, pSettings, new TextBuilder<>(Settings.getIndentStr(), Settings.getNewlineStr()));
  }

  /**
   * Constructor of the Formatter with custom TextBuilder
   *
   * @param pInputSQL The input SQL
   * @param pSettings The settings which are needed for formatting the SQL
   * @param pText     Overwrites the default TextBuilder
   */
  public Formatter(@NotNull String pInputSQL, @NotNull Settings pSettings, @NotNull TextBuilder<EIndentLevel> pText)
  {
    tokenizer = new Tokenizer(pInputSQL);
    settings = pSettings;
    text = pText;
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
  @Override
  @NotNull
  public String format()
  {
    while (true)
    {
      // Advance the next Token
      curr = tokenizer.next();

      // Exit if the curr token is 'EOF'
      if (curr.getType() == ETokenType.EOF)
        return text.finish();

      // Call the formatting handler
      curr.getType().formattingHandler.accept(this);
    }
  }

  /**
   * Handles the Symbol Tocken
   * Inserts whitespaces dependent on the type of Symbol
   *
   * @param pFmt the formatter
   */
  public static void handleSymbol(@NotNull Formatter pFmt)
  {
    switch (pFmt.curr.getText())
    {
      case "(":
        pFmt.text.singleSpace();
        pFmt.text.write(pFmt.curr.getText());
        pFmt.text.incIndent(EIndentLevel.BLOCK);
        pFmt.text.singleNewline();
        break;
      case ")":
        pFmt.text.decIndent(EIndentLevel.BLOCK);
        pFmt.text.singleNewline();
        pFmt.text.write(pFmt.curr.getText());
        break;

      case ".":
        pFmt.text.write(pFmt.curr.getText());
        pFmt.text.noSpace();
        break;
      case ",":
        if (pFmt.settings.newlineBeforeComma)
        {
          pFmt.text.singleNewline();
          pFmt.text.write(pFmt.curr.getText());
          pFmt.text.noSpace();
        }
        else
        {
          pFmt.text.write(pFmt.curr.getText());
          pFmt.text.singleNewline();
        }
        break;
      case ";":
        pFmt.text.resetSingleLineMode();
        pFmt.text.decIndent(EIndentLevel.ALL);
        pFmt.text.write(pFmt.curr.getText());
        pFmt.text.singleNewline();
        pFmt.text.newline();
        break;
      default:
        pFmt.text.write(pFmt.curr.getText());
        break;
    }
  }

  /**
   * Handles Keywordswitch need special formatting
   * e.g. CASE and END
   * It defaults to 'handleDefault'
   *
   * @param pFmt the formatter
   */
  public static void handleKeyword(@NotNull Formatter pFmt)
  {
    switch (pFmt.curr.getText().toUpperCase())
    {
      case "CASE":
        if(pFmt.settings.caseWhenInSingleLine)
          pFmt.text.increaseSingleLineMode();
        pFmt.text.singleSpace();
        pFmt.text.write(pFmt.curr.format(pFmt.settings));
        pFmt.text.incIndent(EIndentLevel.SWITCH);
        break;
      case "END":
        pFmt.text.decIndent(EIndentLevel.SWITCH);
        pFmt.text.singleNewline();
        pFmt.text.write(pFmt.curr.format(pFmt.settings));
        if(pFmt.settings.caseWhenInSingleLine)
          pFmt.text.decreaseSingleLineMode();
        break;
      default:
        handleDefault(pFmt);
        break;
    }
  }

  /**
   * Handles SQL-Function keywords
   * and stops inserting spaces after them
   *
   * @param pFmt the formatter
   */
  public static void handleFunctionKeyword(@NotNull Formatter pFmt)
  {
    handleDefault(pFmt);
    pFmt.text.noSpace();
  }

  /**
   * Handles TopLevel Keywords like switch and from
   * Increases the indentation by a KEYWORD level
   * and inserts a new line
   *
   * @param pFmt the formatter
   */
  public static void handleKWTopLevel(@NotNull Formatter pFmt)
  {
    pFmt.text.singleNewline();
    pFmt.text.decIndent(EIndentLevel.KEYWORD);
    pFmt.text.write(pFmt.curr.format(pFmt.settings));
    pFmt.text.singleNewline();
    pFmt.text.incIndent(EIndentLevel.KEYWORD);
  }

  /**
   * Same as TopLevel but it skips
   * one token befor inserting the new line
   *
   * @param pFmt the formatter
   */
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

  /**
   * Handles wrapping keywords
   * inserts a new line after the keyword
   * without indenting anything
   *
   * @param pFmt the formatter
   */
  public static void handleKWWrapping(@NotNull Formatter pFmt)
  {
    pFmt.text.singleNewline();
    pFmt.text.write(pFmt.curr.format(pFmt.settings));
  }

  /**
   * Handles all other TokenTypes
   * witch don't need a special formatting
   * It just inserts a whitespace befor each Token
   *
   * @param pFmt the formatter
   */
  public static void handleDefault(@NotNull Formatter pFmt)
  {
    pFmt.text.singleSpace();
    pFmt.text.write(pFmt.curr.format(pFmt.settings));
  }
}
