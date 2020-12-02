import de.adito.nbm.sqlformatter.impl.lexer.Token;
import de.adito.nbm.sqlformatter.impl.lexer.Tokenizer;
import org.junit.Test;

public class Lexer
{
	private void _lexText(String pText)
	{
		System.out.println(pText);
		Tokenizer tokenizer = new Tokenizer(pText);

		while (true)
		{
			Token token = tokenizer.next();
			if (token == null) break;
			System.out.println(token);
		}
	}

	@Test
	public void basic()
	{
		_lexText(ExampleSQL.BASIC);
	}

	@Test
	public void simple()
	{
		_lexText(ExampleSQL.SIMPLE);
	}

	@Test
	public void comments()
	{
		_lexText(ExampleSQL.COMMENTS);
	}

	@Test
	public void strings()
	{
		_lexText(ExampleSQL.STRINGS);
	}

	@Test
	public void complex()
	{
		_lexText(ExampleSQL.COMPLEX);
	}
}
