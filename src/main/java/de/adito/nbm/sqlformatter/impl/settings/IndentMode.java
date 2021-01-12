package de.adito.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;

/**
 * @author p.neub, 12.01.2021
 */
public enum IndentMode
{
  TAB("  ", "Use 2 Space Characters"),
  SPACE_2("    ", "Use 4 Space Characters"),
  SPACE_4("\t", "Use Tab Characters");

  private final String indent;
  private final String description;

  IndentMode(@NotNull String pIndents, @NotNull String pDescription)
  {
    indent = pIndents;
    description = pDescription;
  }

  @NotNull
  public String getIndent()
  {
    return indent;
  }

  @Override
  public String toString()
  {
    return description;
  }
}
