package de.adito.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;

/**
 * @author p.neub, 12.01.2021
 */
public enum LineEnding
{
  LF("\n", "Use LF (Linux / Unix / Mac)"),
  CR("\r", "Use CR (Mac)"),
  CRLF("\r\n", "Use CRLF (Windows)");

  private final String lineEnding;
  private final String description;

  LineEnding(@NotNull String pLineEnding, @NotNull String pDescription)
  {
    lineEnding = pLineEnding;
    description = pDescription;
  }

  @NotNull
  public String getLineEnding()
  {
    return lineEnding;
  }

  @Override
  public String toString()
  {
    return description;
  }
}
