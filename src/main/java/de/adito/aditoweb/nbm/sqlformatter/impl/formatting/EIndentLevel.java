package de.adito.aditoweb.nbm.sqlformatter.impl.formatting;

/**
 * Specifies the IntentLevels
 * The order does matter
 * so the last element is essentially
 * the indent with the highest level
 *
 * @author p.neub, 28.01.2021
 */
public enum EIndentLevel
{
  /**
   * Indentation for Keywords e.g. after select or from
   */
  KEYWORD,

  /**
   * Indentation for the switch/case statement
   */
  SWITCH,

  /**
   * Indentation for blocks usually brackets
   */
  BLOCK,

  /**
   * Removes all indentation
   * Usually used to reset the indents afer the ';' token
   */
  ALL
}
