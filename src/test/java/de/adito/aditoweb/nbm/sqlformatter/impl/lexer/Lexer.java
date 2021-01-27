package de.adito.aditoweb.nbm.sqlformatter.impl.lexer;

import org.junit.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Lexer
{
  @Test
  public void symbols()
  {
    testFile("symbols", Arrays.asList(
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
    testFile("complex", Arrays.asList(
        new Token(ETokenType.WORD, "contact"),
        new Token(ETokenType.SYMBOL, "`"),
        new Token(ETokenType.SYMBOL, "`"),
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
        new Token(ETokenType.RESERVED, "max"),
        new Token(ETokenType.SYMBOL, "("),
        new Token(ETokenType.NUMBER, "1"),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.NUMBER, "2"),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.NUMBER, "3"),
        new Token(ETokenType.SYMBOL, ")"),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.SYMBOL, ","),
        new Token(ETokenType.LINE_COMMENT, "-- This is a comment as well")
    ));
  }

  private void testFile(String pName, List<Token> pTokens)
  {
    String sqlText = new Scanner(Lexer.class.getResourceAsStream(pName + ".sql"), StandardCharsets.UTF_8)
        .useDelimiter("\\A").next();
    Tokenizer tokenizer = new Tokenizer(sqlText);

    int tokenPos = 0;
    while (true)
    {
      Token token = tokenizer.next();
      if (token == null)
        break;
      Assert.assertTrue("Token amount doesn't match", tokenPos < pTokens.size());
      Token expectedToken = pTokens.get(tokenPos++);
      Assert.assertEquals("Token doesn't match", expectedToken, token);
    }
    Assert.assertEquals("Token amount doesn't match", tokenPos, pTokens.size());
  }
}
