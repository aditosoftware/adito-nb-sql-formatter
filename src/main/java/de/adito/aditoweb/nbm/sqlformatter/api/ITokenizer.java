package de.adito.aditoweb.nbm.sqlformatter.api;

import lombok.NonNull;

/**
 * Provides an interface, for implementing any kind of Interface
 *
 * @param <T> Type of the Token, that can be processed by the Tokenizer
 * @author p.neub, 12.01.2021
 */
public interface ITokenizer<T extends IToken<?>>
{
  /**
   * Advances the next token
   *
   * @return the next token
   */
  @NonNull
  T next();
}
