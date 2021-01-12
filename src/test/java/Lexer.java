import de.adito.nbm.sqlformatter.impl.lexer.*;
import org.junit.Test;

import java.util.Stack;

public class Lexer
{
  @Test
  public void basic()
  {
    String sql = "select*from test+";

    Stack<TokenType> tokens = new Stack<>();
    tokens.add(0, TokenType.RESERVED_TOPLEVEL);
    tokens.add(0, TokenType.OPERATOR);
    tokens.add(0, TokenType.RESERVED_TOPLEVEL);
    tokens.add(0, TokenType.WORD);
    tokens.add(0, TokenType.OPERATOR);

    Tokenizer tokenizer = new Tokenizer(sql);
    while (true)
    {
      Token curr = tokenizer.next();
      if (curr == null) break;
      System.out.println(curr.getType() + " " + tokens.peek());
      assert curr.getType() == tokens.pop();
    }
    System.out.println(tokens.size());
    assert tokens.size() == 0;
  }
}
