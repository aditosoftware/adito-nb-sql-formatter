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
public class Tokenizer implements ITokenizer<Token>
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

  /**
   * Returns the next token
   * Returns null if the end of text is reached
   *
   * @return the next token
   */
  @Override
  @NotNull
  public Token next()
  {
    // Checks weather the end of text is reached
    if (pos >= text.length())
      return new Token(ETokenType.EOF, "");

    // Read current char
    char currChar = text.charAt(pos);

    // Skip whitespaces
    if (Character.isWhitespace(currChar))
    {
      pos++;
      return next();
    }

    // Tokenize strings
    if (ISQLConstants.STRING_CHARS.contains(currChar))
      return _readEncapsulatedWord(currChar, currChar, ETokenType.STRING);

    // Tokenize field char words
    if (ISQLConstants.FIELD_CHARS_START.contains(currChar)) {
      char endChar = ISQLConstants.FIELD_CHARS_END.get(ISQLConstants.FIELD_CHARS_START.indexOf(currChar));
      return _readEncapsulatedWord(currChar, endChar, ETokenType.WORD);
    }

    // Tokenize comments (ETokenType.COMMENT)
    if (pos + 1 < text.length())
    {
      switch (text.substring(pos, pos + 2))
      {
        case "--":
          String lineComment = _readWhile(ch -> ch != '\n', false);
          return new Token(ETokenType.LINE_COMMENT, lineComment.trim());
        case "/*":
          String blockComment = _readWhile(ch -> !text.startsWith("*/", pos - 2), false);
          return new Token(ETokenType.BLOCK_COMMENT, blockComment.trim());
      }
    }

    // Tokenize operators (ETokenType.OPERATOR)
    if (ISQLConstants.OPERATORS.contains(currChar))
      return new Token(ETokenType.OPERATOR, _readWhile(ISQLConstants.OPERATORS::contains, true));

    if (Character.isDigit(currChar))
      return new Token(ETokenType.NUMBER, _readWhile(ch -> ch == '.' || Character.isDigit(ch), true));

    // Temporary Token.
    // The result of the 'Nullable' keyword check will be written to this
    Token maybeKeyword;

    // Check for TOPLEVEL_KEYWORDS
    maybeKeyword = _maybeKeyword(ISQLConstants.TOPLEVEL_KEYWORDS, ETokenType.KW_TOPLEVEL);
    if (maybeKeyword != null)
      return maybeKeyword;

    // Check for LAZYTOPLEVEL_KEYWORDS
    maybeKeyword = _maybeKeyword(ISQLConstants.LAZYTOPLEVEL_KEYWORDS, ETokenType.KW_LAZYTOPLEVEL);
    if (maybeKeyword != null)
      return maybeKeyword;

    // Check for WRAPPING_KEYWORDS
    maybeKeyword = _maybeKeyword(ISQLConstants.WRAPPING_KEYWORDS, ETokenType.KW_WRAPPING);
    if (maybeKeyword != null)
      return maybeKeyword;


    // Read words (ETokenType.WORD) and keywords (ETokenType.KEYWORD)
    if (currChar == '_' || Character.isAlphabetic(currChar))
    {
      String word = _readWhile(ch -> ch == '_' ||
          Character.isAlphabetic(ch) || Character.isDigit(ch), true);

      // Check for ETokenType.FUNCTION_KEYWORD
      if (ISQLConstants.FUNCTION_KEYWORDS.contains(word.toUpperCase()))
        return new Token(ETokenType.FUNCTION_KEYWORD, word);

      // Check for ETokenType.KEYWORD
      if (ISQLConstants.KEYWORDS.contains(word.toUpperCase()))
        return new Token(ETokenType.KEYWORD, word);

      // Otherwise return the word Token
      return new Token(ETokenType.WORD, word);
    }

    // Default rule (ETokenType.SYMBOL)
    // if the character isn't captured  by any of the other rules
    pos++;
    return new Token(ETokenType.SYMBOL, Character.toString(currChar));
  }

  /**
   * Tokenizes a encapsulated word e.g. a string or `FIELD_NAME`
   *
   * @param pStart the start character
   * @param pEnd the end character
   * @param pTokenType the ETokenType witch should be used for the returned Token
   * @return the Token with the specified type and text
   */
  @NotNull
  private Token _readEncapsulatedWord(char pStart, char pEnd, @NotNull ETokenType pTokenType)
  {
    pos++;
    String strText = _readWhile(ch -> ch != pEnd, false);
    pos++;
    return new Token(pTokenType, pStart + strText + pEnd);
  }

  /**
   * Reads while the condition checkFunc is satisfied
   *
   * @param pCheckFunc the condition
   * @param comments   whether the function stop if it reaches a comment
   * @return returns the read text
   */
  @NotNull
  private String _readWhile(@NotNull Predicate<Character> pCheckFunc, boolean comments)
  {
    StringBuilder result = new StringBuilder();
    while (pos < text.length())
    {
      // Exit on comments
      if (comments && pos + 1 < text.length())
      {
        switch (text.substring(pos, pos + 2))
        {
          case "--":
          case "/*":
            break;
        }
      }

      // Check currChar using checkFunc
      char currChar = text.charAt(pos);
      if (pCheckFunc.test(currChar))
      {
        result.append(currChar);
        pos++;
      }
      else
        break;
    }
    return result.toString();
  }

  /**
   * Reads a keyword (present in pKeywords) from the text
   * The keyword might include whitespaces
   * If the function returns null, no keyword was found
   *
   * @param pKeywords  list of keywords witch might include whitespaces
   * @param pTokenType token type of the returned Token
   * @return returns a Token produces by pTokenType and the keyword (if a keyword as been found)
   */
  @Nullable
  private Token _maybeKeyword(@NotNull List<String> pKeywords, @NotNull ETokenType pTokenType)
  {
    for (String keyword : pKeywords)
    {
      if (text.substring(pos).toUpperCase().startsWith(keyword))
      {
        if (pos + keyword.length() < text.length())
        {
          char followUpChar = text.charAt(pos + keyword.length());
          if (followUpChar == '_' || Character.isDigit(followUpChar) || Character.isAlphabetic(followUpChar))
            continue;
        }
        pos += keyword.length();
        return new Token(pTokenType, text.substring(pos - keyword.length(), pos));
      }
    }
    return null;
  }
}
