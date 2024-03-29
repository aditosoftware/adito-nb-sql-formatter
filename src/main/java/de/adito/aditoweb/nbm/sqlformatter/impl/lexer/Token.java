package de.adito.aditoweb.nbm.sqlformatter.impl.lexer;

import de.adito.aditoweb.nbm.sqlformatter.api.IToken;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import lombok.NonNull;

import java.util.Objects;

/**
 * Represents a SQL literal
 *
 * @author p.neub, 01.12.2020
 */
public class Token implements IToken<ETokenType>
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
  public Token(@NonNull ETokenType pType, @NonNull String pText)
  {
    type = pType;
    text = pText;
  }

  /**
   * Returns the type of the token
   *
   * @return the type of the token
   */
  @Override
  @NonNull
  public ETokenType getType()
  {
    return type;
  }

  /**
   * Returns the text of the token, usually the text representation of the token (not formatted)
   *
   * @return the text of the token, usually the text representation of the token (not formatted)
   */
  @Override
  @NonNull
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
  @NonNull
  public String format(@NonNull Settings pSettings)
  {
    if (type.isKeyword == null)
      return text;
    if (type.isKeyword)
      return pSettings.keywordCaseMode.format(text);
    else
      return pSettings.wordCaseMode.format(text);
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

  @Override
  public int hashCode()
  {
    return Objects.hash(type, text);
  }
}
