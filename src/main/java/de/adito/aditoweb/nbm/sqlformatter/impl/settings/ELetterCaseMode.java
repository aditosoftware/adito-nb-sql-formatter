package de.adito.aditoweb.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;
import org.openide.util.NbBundle;

import java.util.function.Function;

/**
 * @author p.neub, 12.01.2021
 */
public enum ELetterCaseMode
{
  UNALTERED(NbBundle.getMessage(ELetterCaseMode.class, "LBL_ELetterCaseMode_UNALTERED"), null),
  LOWERCASE(NbBundle.getMessage(ELetterCaseMode.class, "LBL_ELetterCaseMode_LOWERCASE"), String::toLowerCase),
  UPPERCASE(NbBundle.getMessage(ELetterCaseMode.class, "LBL_ELetterCaseMode_UPPERCASE"), String::toUpperCase);

  /**
   * Description of the settings option
   * This will be displayed in the options GUI
   */
  private final String description;

  /**
   * Function used to format tokens
   */
  private final Function<String, String> fmtFunc;

  /**
   * Constructs a new ELetterCaseMode
   *
   * @param pDescription Description of the settings option
   * @param pFmtFunc Function used to format tokens
   */
  ELetterCaseMode(@NotNull String pDescription, Function<String, String> pFmtFunc)
  {
    description = pDescription;
    fmtFunc = pFmtFunc;
  }

  /**
   * Formats text corrosponding to the mode
   *
   * @param target the target text
   * @return the formatted text
   */
  @NotNull
  public String format(@NotNull String target)
  {
    if (fmtFunc == null)
      return target;
    return fmtFunc.apply(target);
  }

  /**
   * Returns the description
   *
   * @return the mode description
   */
  @Override
  public String toString()
  {
    return description;
  }
}
