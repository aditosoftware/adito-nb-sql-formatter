package de.adito.nbm.sqlformatter.impl;

import de.adito.nbm.sqlformatter.impl.formatting.Formatter;
import de.adito.nbm.sqlformatter.impl.lexer.Tokenizer;
import de.adito.nbm.sqlformatter.impl.settings.Settings;
import org.jetbrains.annotations.NotNull;

/**
 * @author p.neub, 01.12.2020
 */
public class SQLUtils
{
  /**
   * Formats the given SQL String with the given settings
   *
   * @param pTarget   the given SQL String
   * @param pSettings the given settings
   * @return the formatted SQL String
   */
  @NotNull
  public static String format(@NotNull String pTarget, @NotNull Settings pSettings)
  {
    Tokenizer tokenizer = new Tokenizer(pTarget);
    Formatter formatter = new Formatter(tokenizer, pSettings);
    return formatter.format();
  }
}
