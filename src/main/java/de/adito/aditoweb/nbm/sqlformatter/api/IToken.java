package de.adito.aditoweb.nbm.sqlformatter.api;

import org.jetbrains.annotations.NotNull;

/**
 * Representation for any kind of Token
 *
 * @param <T> The TokenType
 * @author p.neub, 12.01.2021
 */
public interface IToken<T>
{
  /**
   * Returns the type of the token
   *
   * @return the type of the token
   */
  @NotNull
  T getType();

  /**
   * Returns the original, unformatted text of the token
   *
   * @return the original, unformatted text of the token
   */
  @NotNull
  String getText();
}
