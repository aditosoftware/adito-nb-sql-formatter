package de.adito.aditoweb.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;
import org.openide.util.NbBundle;

/**
 * @author p.neub, 12.01.2021
 */
public enum ELineEnding
{
  LF("\n", NbBundle.getMessage(ELineEnding.class, "LBL_ELineEnding_LF")),
  CR("\r", NbBundle.getMessage(ELineEnding.class, "LBL_ELineEnding_CR")),
  CRLF("\r\n", NbBundle.getMessage(ELineEnding.class, "LBL_ELineEnding_CRLF"));

  private final String lineEnding;
  private final String description;

  ELineEnding(@NotNull String pLineEnding, @NotNull String pDescription)
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
