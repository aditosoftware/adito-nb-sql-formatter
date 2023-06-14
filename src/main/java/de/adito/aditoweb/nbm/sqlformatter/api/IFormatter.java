package de.adito.aditoweb.nbm.sqlformatter.api;

import lombok.NonNull;

/**
 * Provides an interface, for implementing any kind of Formatter
 *
 * @author p.neub, 01.12.2020
 */
public interface IFormatter
{
  /**
   * This function should return the formatted 'code'
   *
   * @return returns the formatted 'code'
   */
  @NonNull
  String format();
}
