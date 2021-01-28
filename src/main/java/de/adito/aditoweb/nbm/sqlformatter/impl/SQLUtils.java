package de.adito.aditoweb.nbm.sqlformatter.impl;

import de.adito.aditoweb.nbm.sqlformatter.api.*;
import de.adito.aditoweb.nbm.sqlformatter.impl.formatting.Formatter;
import de.adito.aditoweb.nbm.sqlformatter.impl.lexer.*;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
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
    ITokenizer<Token> tokenizer = new Tokenizer(pTarget);
    IFormatter formatter = new Formatter(tokenizer, pSettings);
    return formatter.format();
  }
}
