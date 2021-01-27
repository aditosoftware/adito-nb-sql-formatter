package de.adito.aditoweb.nbm.sqlformatter.impl.lexer;

import de.adito.aditoweb.nbm.sqlformatter.api.IToken;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a SQL literal
 *
 * @author p.neub, 01.12.2020
 */
public class Token implements IToken
{
  /**
   * the type of the token
   */
  private final ETokenType type;

  /**
   * the text of the token, usually the text representation of the token (not formatted)
   */
  private final String text;

  /**
   * Token constructor
   *
   * @param pType the type of the token
   * @param pText the text of the token, usually the text representation of the token (not formatted)
   */
  public Token(@NotNull ETokenType pType, @NotNull String pText)
  {
    type = pType;
    text = pText;
  }

  /**
   * Returns the type of the token
   *
   * @return the type of the token
   */
  @NotNull
  public ETokenType getType()
  {
    return type;
  }

  /**
   * Returns the text of the token, usually the text representation of the token (not formatted)
   *
   * @return the text of the token, usually the text representation of the token (not formatted)
   */
  @NotNull
  public String getText()
  {
    return text;
  }

  /**
   * Formats the SQL-Token following the formatting settings
   *
   * @param pSettings the formatting settings
   * @return the formatted token in text form
   */
  @NotNull
  public String format(@NotNull Settings pSettings)
  {
    switch (type)
    {
      case WORD:
        return pSettings.getWordCaseMode().format(text);
      case RESERVED:
      case RESERVED_TOPLEVEL:
      case RESERVED_WRAPPING:
        return pSettings.getKeywordCaseMode().format(text);
      default:
        return text;
    }
  }

  /**
   * Checks two tokens for equality
   *
   * @param pType the type of the second token
   * @param pText the text of the second token
   * @return true if the tokens equals each other, otherwise false
   */
  public boolean check(@NotNull ETokenType pType, @NotNull String pText)
  {
    return type == pType && text.equals(pText);
  }

  @Override
  public String toString()
  {
    return "Token(" + type + ":" + text + ")";
  }

  @Override
  public boolean equals(Object pO)
  {
    if (this == pO) return true;
    if (pO == null || getClass() != pO.getClass()) return false;
    Token token = (Token) pO;
    return type == token.type && Objects.equals(text, token.text);
  }
}
