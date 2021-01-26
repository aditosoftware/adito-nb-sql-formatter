package de.adito.aditoweb.nbm.sqlformatter.impl.lexer;

import de.adito.aditoweb.nbm.sqlformatter.api.*;
import org.jetbrains.annotations.*;

import java.util.List;
import java.util.function.*;

/**
 * Utility class used to split the input SQL into tokens/literals
 *
 * @author p.neub, 01.12.2020
 */
public class Tokenizer implements ITokenizer
{
  private final String text;
  private int pos = 0;

  /**
   * Constructor of the tokenizer
   *
   * @param pText the text witch needs to be tokenized
   */
  public Tokenizer(@NotNull String pText)
  {
    text = pText;
  }

  @Nullable
  private Token _nextReserved(@NotNull List<String> pList, @NotNull ETokenType pTokenType)
  {
    for (String reservedWord : pList)
    {
      if (text.substring(pos).toUpperCase().startsWith(reservedWord))
      {
        if (pos + reservedWord.length() < text.length())
        {
          char followUpChar = text.charAt(pos + reservedWord.length());
          if (followUpChar == '_' || Character.isDigit(followUpChar) || Character.isAlphabetic(followUpChar))
            continue;
        }
        pos += reservedWord.length();
        return new Token(pTokenType, text.substring(pos - reservedWord.length(), pos));
      }
    }
    return null;
  }

  /**
   * Returns the next token
   *
   * @return the next token
   */
  @Nullable
  public Token next()
  {
    if (pos >= text.length())
      return null;
    char currChar = text.charAt(pos);
    if (Character.isWhitespace(currChar))
    {
      pos++;
      return next();
    }

    if ("'\"".indexOf(currChar) != -1)
    {
      pos++;
      String strText = _readWhile(ch -> ch != currChar);
      pos++;
      return new Token(ETokenType.STRING, currChar + strText + currChar);
    }

    if (pos + 1 < text.length()) {
      switch (text.substring(pos, pos + 2))
      {
        case "--":
          String lineComment = _readWhile(ch -> ch != '\n').trim();
          return new Token(ETokenType.LINE_COMMENT, lineComment);
        case "/*":
          String blockComment = _readWhile(ch -> !text.startsWith("*/", pos)).trim();
          pos += 2;
          return new Token(ETokenType.BLOCK_COMMENT, blockComment + "*/");
      }
    }

    if (ISQLConstants.OPERATORS.contains(currChar))
    {
      String operator = _readWhile(ISQLConstants.OPERATORS::contains);
      return new Token(ETokenType.OPERATOR, operator);
    }
    if (Character.isDigit(currChar))
    {
      String number = _readWhile(ch -> ch == '.' ||
          Character.isDigit(ch));
      return new Token(ETokenType.NUMBER, number);
    }

    Token nextReserved = _nextReserved(ISQLConstants.RESERVED_TOPLEVEL_WORDS, ETokenType.RESERVED_TOPLEVEL);
    if (nextReserved != null)
      return nextReserved;
    nextReserved = _nextReserved(ISQLConstants.RESERVED_WRAPPING_WORDS, ETokenType.RESERVED_WRAPPING);
    if (nextReserved != null)
      return nextReserved;
    nextReserved = _nextReserved(ISQLConstants.RESERVED_WORDS, ETokenType.RESERVED);
    if (nextReserved != null)
      return nextReserved;

    if (currChar == '_' || Character.isAlphabetic(currChar))
    {
      String word = _readWhile(ch -> ch == '_' ||
          Character.isAlphabetic(ch) || Character.isDigit(ch));
      return new Token(ETokenType.WORD, word);
    }

    pos++;
    return new Token(ETokenType.SYMBOL, Character.toString(currChar));
  }

  /**
   * Reads while the condition checkFunc is satisfied
   *
   * @param pCheckFunc the condition
   * @return returns the read text
   */
  @NotNull
  private String _readWhile(@NotNull Predicate<Character> pCheckFunc)
  {
    StringBuilder result = new StringBuilder();
    while (pos < text.length())
    {
      if (pos + 1 < text.length()) {
        switch (text.substring(pos, pos + 2))
        {
          case "--":
          case "/*":
            break;
        }
      }
      char currChar = text.charAt(pos);
      if (pCheckFunc.test(currChar))
      {
        result.append(currChar);
        pos++;
      }
      else break;
    }
    return result.toString();
  }
}
