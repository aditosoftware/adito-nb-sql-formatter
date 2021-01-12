package de.adito.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @author p.neub, 12.01.2021
 */
public enum LetterCaseMode
{
  UNALTERED("Don't alter anything", null),
  LOWERCASE("Use lowercase", String::toLowerCase),
  UPPERCASE("Use uppercase", String::toUpperCase);

  private final String description;
  private final Function<String, String> fmtFunc;

  LetterCaseMode(@NotNull String pDescription, Function<String, String> pFmtFunc)
  {
    description = pDescription;
    fmtFunc = pFmtFunc;
  }

  @NotNull
  public String format(@NotNull String target)
  {
    if (fmtFunc == null) return target;
    return fmtFunc.apply(target);
  }

  @Override
  public String toString()
  {
    return description;
  }
}
