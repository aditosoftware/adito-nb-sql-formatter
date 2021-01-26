package de.adito.aditoweb.nbm.sqlformatter.api;

import org.jetbrains.annotations.NotNull;

/**
 * Representation for any kind of Token
 *
 * @author p.neub, 12.01.2021
 */
public interface IToken
{
  /**
   * Returns the text of the token, usually the text representation of the token
   *
   * @return the text of the token, usually the text representation of the token
   */
  @NotNull
  String getText();
}
