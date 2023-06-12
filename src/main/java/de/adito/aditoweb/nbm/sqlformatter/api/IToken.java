package de.adito.aditoweb.nbm.sqlformatter.api;

import lombok.NonNull;

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
  @NonNull
  T getType();

  /**
   * Returns the original, unformatted text of the token
   *
   * @return the original, unformatted text of the token
   */
  @NonNull
  String getText();
}
