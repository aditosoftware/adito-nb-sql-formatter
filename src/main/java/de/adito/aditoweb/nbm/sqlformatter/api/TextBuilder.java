package de.adito.aditoweb.nbm.sqlformatter.api;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 * Writes the formatted sql to an StringBuilder and handles the indentation
 *
 * @param <T> IndentLevel enum
 * @author p.neub, 01.12.2020
 */
public class TextBuilder<T extends Enum<?>>
{
  /**
   * The formatting result will be written to this StringBuilder
   */
  private final StringBuilder builder = new StringBuilder();

  /**
   * String used for indentation
   */
  private final String indentStr;

  /**
   * String used for new lines
   */
  private final String newlineStr;

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
  private final Stack<T> indents = new Stack<>();

  /**
   * Creates a new TextBuilder instance
   *
   * @param pIndentStr String used for indentation
   * @param pNewlineStr String used for new lines
   */
  public TextBuilder(@NotNull String pIndentStr, @NotNull String pNewlineStr)
  {
    indentStr = pIndentStr;
    newlineStr = pNewlineStr;
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
    builder.append(newlineStr);
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
      String indentText = new String(new char[indents.size()])
          .replace("\0", indentStr);
      builder.append(indentText);
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
  public void incIndent(T pLevel)
  {
    indents.push(pLevel);
  }

  /**
   * Decreases the indents
   * Higher level indents always decrease the lower level indents too.
   *
   * @param pLevel The level is used for decreasing indents.
   */
  public void decIndent(T pLevel)
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
