package de.adito.aditoweb.nbm.sqlformatter.impl.formatting;

import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 * Writes the formatted sql to an StringBuilder and handles the indentation
 *
 * @author p.neub, 01.12.2020
 */
public class TextBuilder
{
  /**
   * The settings witch are needed to insert the correct indentation
   * and line break characters
   */
  private final Settings settings;

  /**
   * The formatting result will be written to this StringBuilder
   */
  private final StringBuilder builder = new StringBuilder();

  /**
   * Indicates if the last character is a space
   * Note: This can also be manually modified to prevent whitespacing
   */
  private boolean spaceFlag = true;

  /**
   * Indicates if the last character is a new line
   * Note: This can also be manually modified to prevent new lining
   */
  private boolean newlineFlag = true;

  /**
   * The indentation stack
   * the size of this stack is equal to the amount of indents
   */
  private final Stack<EIndentLevel> indents = new Stack<>();

  /**
   * Creates a new TextBuilder instance
   *
   * @param pSettings Formatting Settings for the TextBuilder
   */
  public TextBuilder(@NotNull Settings pSettings)
  {
    settings = pSettings;
  }

  /**
   * Writes a space
   */
  public void space()
  {
    spaceFlag = true;
    builder.append(" ");
  }

  /**
   * Writes a space but if the last char is already a space or a new line it does nothing
   */
  public void singleSpace()
  {
    if (!newlineFlag && !spaceFlag)
      space();
  }

  /**
   * Disables spacing until the next write
   */
  public void noSpace()
  {
    spaceFlag = true;
  }

  /**
   * Writes a new line
   */
  public void newline()
  {
    newlineFlag = true;
    builder.append(settings.getLineEnding().getLineEnding());
  }

  /**
   * Writes a new line but if the last char is already a new line it does nothing
   */
  public void singleNewline()
  {
    if (!newlineFlag)
      newline();
  }

  /**
   * Disables new lines until the next write
   */
  public void noNewLine()
  {
    newlineFlag = true;
  }

  /**
   * Disables all whitespace characters until the next write
   */
  public void noWhitespace()
  {
    noSpace();
    noNewLine();
  }

  /**
   * Writes some text, this should not be used for writing new lines or spaces
   *
   * @param pText the text which will be written to the StringBuilder
   */
  public void write(@NotNull String pText)
  {
    spaceFlag = false;
    if (newlineFlag)
    {
      newlineFlag = false;
      String indentStr = new String(new char[indents.size()])
          .replace("\0", settings.getIndentMode().getIndent());
      builder.append(indentStr);
    }
    builder.append(pText);
  }

  /**
   * Convert the underlying StringBuilder to an String an returns it
   *
   * @return the formatted SQL as String
   */
  @NotNull
  public String finish()
  {
    return builder.toString().trim();
  }

  /**
   * Increases the indents
   *
   * @param pLevel The level is used for increasing indents.
   */
  public void incIndent(EIndentLevel pLevel)
  {
    indents.push(pLevel);
  }

  /**
   * Decreases the indents
   * Higher level indents always decrease the lower level indents too.
   *
   * @param pLevel The level is used for decreasing indents.
   */
  public void decIndent(EIndentLevel pLevel)
  {
    while (!indents.isEmpty())
    {
      if (indents.peek() == pLevel)
      {
        indents.pop();
        break;
      }
      else if (indents.peek().ordinal() < pLevel.ordinal())
        indents.pop();
      else
        break;
    }
  }
}
