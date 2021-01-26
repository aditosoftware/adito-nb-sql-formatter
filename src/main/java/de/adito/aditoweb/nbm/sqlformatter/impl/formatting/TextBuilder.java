package de.adito.aditoweb.nbm.sqlformatter.impl.formatting;

import de.adito.aditoweb.nbm.sqlformatter.api.ITextBuilder;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 * Writes the formatted sql to an StringBuilder and handles the indentation
 *
 * @author p.neub, 01.12.2020
 */
public class TextBuilder implements ITextBuilder
{
  private final Settings settings;
  private final StringBuilder builder = new StringBuilder();
  private boolean newlineFlag = true;
  private final Stack<Integer> indents = new Stack<>();

  public TextBuilder(@NotNull Settings pSettings)
  {
    settings = pSettings;
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
   * Writes some text, this should not be used for writing new lines
   *
   * @param pText the text which will be written to the StringBuilder
   */
  public void write(@NotNull String pText)
  {
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
    return builder.toString();
  }

  /**
   * Checks if spacing is allowed at the current state of the TextBuilder
   * - Checks if the last char is a newline if so spacing is not allowed
   *
   * @return is spacing allowed
   */
  public boolean spacingAllowed()
  {
    return !newlineFlag;
  }

  /**
   * Increases the indents
   *
   * @param pLevel The level is used for decreasing indents. Higher level indents always decrease the lower level indents too.
   */
  public void incIndent(int pLevel)
  {
    indents.push(pLevel);
  }

  /**
   * Decreases the indents
   *
   * @param pLevel Higher level indents always decrease the lower level indents too.
   */
  public void decIndent(int pLevel)
  {
    while (!indents.isEmpty())
    {
      if (indents.peek() == pLevel)
      {
        indents.pop();
        break;
      }
      else if (indents.peek() < pLevel)
        indents.pop();
      else
        break;
    }
  }
}
