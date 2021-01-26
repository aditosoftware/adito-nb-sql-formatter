package de.adito.aditoweb.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;
import org.openide.util.NbBundle;

/**
 * @author p.neub, 12.01.2021
 */
public enum EIndentMode
{
  TAB("\t", NbBundle.getMessage(EIndentMode.class, "LBL_EIndentMode_TAB")),
  SPACE_2("  ", NbBundle.getMessage(EIndentMode.class, "LBL_EIndentMode_SPACE_2")),
  SPACE_4("    ", NbBundle.getMessage(EIndentMode.class, "LBL_EIndentMode_SPACE_4"));

  private final String indent;
  private final String description;

  EIndentMode(@NotNull String pIndents, @NotNull String pDescription)
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
