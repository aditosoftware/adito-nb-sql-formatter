package de.adito.aditoweb.nbm.sqlformatter.impl.lexer;

import org.junit.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author p.neub, 04.02.2021
 */
public class Lexer
{
  @Test
  public void symbols()
  {
    _testFile("symbols", Arrays.asList(
        new Token(ETokenType.OPERATOR, "**"),
        new Token(ETokenType.SYMBOL, "("),
        new Token(ETokenType.OPERATOR, "+"),
        new Token(ETokenType.SYMBOL, ")"),
        new Token(ETokenType.SYMBOL, ")"),
        new Token(ETokenType.SYMBOL, ")"),
        new Token(ETokenType.SYMBOL, ")"),
        new Token(ETokenType.OPERATOR, "+*"),
        new Token(ETokenType.SYMBOL, "."),
        new Token(ETokenType.SYMBOL, "."),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.SYMBOL, ";"),
        new Token(ETokenType.OPERATOR, "+")
    ));
  }

  @Test
  public void complex()
  {
    _testFile("complex", Arrays.asList(
        new Token(ETokenType.WORD, "contact"),
        new Token(ETokenType.WORD, "``"),
        new Token(ETokenType.WORD, "OrganiSATION"),
        new Token(ETokenType.STRING, "\"Hubert Maier\""),
        new Token(ETokenType.STRING, "'Franz'"),
        new Token(ETokenType.OPERATOR, "++"),
        new Token(ETokenType.SYMBOL, "#"),
        new Token(ETokenType.BLOCK_COMMENT, "/*This is\r\na Comment*/"),
        new Token(ETokenType.WORD, "actiVITY"),
        new Token(ETokenType.SYMBOL, "."),
        new Token(ETokenType.WORD, "ACTIVITYID"),
        new Token(ETokenType.OPERATOR, "+"),
        new Token(ETokenType.FUNCTION_KEYWORD, "max"),
        new Token(ETokenType.SYMBOL, "("),
        new Token(ETokenType.NUMBER, "1"),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.NUMBER, "2"),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.NUMBER, "3"),
        new Token(ETokenType.SYMBOL, ")"),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.WORD, "[ACTIVITYID]"),
        new Token(ETokenType.LINE_COMMENT, "-- This is a comment as well")
    ));
  }

  /**
   * Tokenizes a file and compares the result with the specified tokens
   * if they don't match the test will fail
   *
   * @param pName   the name of the test sql file
   * @param pTokens the expected tokens
   */
  private void _testFile(String pName, List<Token> pTokens)
  {
    String sqlText = new Scanner(Lexer.class.getResourceAsStream(pName + ".sql"), StandardCharsets.UTF_8)
        .useDelimiter("\\A").next();
    Tokenizer tokenizer = new Tokenizer(sqlText);

    int tokenPos = 0;
    while (true)
    {
      Token token = tokenizer.next();
      if (token.getType() == ETokenType.EOF)
        break;
      Assert.assertTrue("Token amount doesn't match", tokenPos < pTokens.size());
      Token expectedToken = pTokens.get(tokenPos++);
      Assert.assertEquals("Token doesn't match", expectedToken, token);
    }
    Assert.assertEquals("Token amount doesn't match", tokenPos, pTokens.size());
  }
}
