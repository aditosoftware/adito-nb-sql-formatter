package de.adito.aditoweb.nbm.sqlformatter.api;

/**
 * Provides an interface, for implementing any kind of Interface
 *
 * @author p.neub, 12.01.2021
 */
public interface ITokenizer
{
  /**
   * Advances the next token
   *
   * @return the next token
   */
  IToken next();
}
