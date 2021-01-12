package de.adito.nbm.sqlformatter.api;

import org.jetbrains.annotations.NotNull;

/**
 * An interface for handling the indentation and text concatenation (using StringBuilder)
 *
 * @author p.neub, 12.01.2021
 */
public interface ITextBuilder
{
  /**
   * Writes a new line
   */
  void newline();

  /**
   * Writes a new line but if the last char is already a new line it does nothing
   */
  void singleNewline();

  /**
   * Writes some text, this should not be used for writing new lines
   *
   * @param pText the text which will be written to the StringBuilder
   */
  void write(@NotNull String pText);

  /**
   * Convert the underlying StringBuilder to an String an returns it
   *
   * @return the processed text as String
   */
  @NotNull
  String finish();

  /**
   * Checks if spacing is allowed at the current state of the TextBuilder
   * - Checks if the last char is a newline if so spacing is not allowed
   *
   * @return is spacing allowed
   */
  boolean spacingAllowed();

  /**
   * Increases the indents
   *
   * @param pLevel The level is used for decreasing indents. Higher level indents always decrease the lower level indents too.
   */
  void incIndent(int pLevel);

  /**
   * Decreases the indents
   *
   * @param pLevel Higher level indents always decrease the lower level indents too.
   */
  void decIndent(int pLevel);
}
