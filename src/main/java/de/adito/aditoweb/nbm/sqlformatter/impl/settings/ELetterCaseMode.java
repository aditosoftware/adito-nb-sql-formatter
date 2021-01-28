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

  private final String description;
  private final Function<String, String> fmtFunc;

  ELetterCaseMode(@NotNull String pDescription, Function<String, String> pFmtFunc)
  {
    description = pDescription;
    fmtFunc = pFmtFunc;
  }

  @NotNull
  public String format(@NotNull String target)
  {
    if (fmtFunc == null)
      return target;
    return fmtFunc.apply(target);
  }

  @Override
  public String toString()
  {
    return description;
  }
}
