import de.adito.nbm.sqlformatter.SQL;
import de.adito.nbm.sqlformatter.Settings;
import org.junit.Test;

public class Formatting
{
	@Test
	public void complex()
	{
		_formatSource(ExampleSQL.COMPLEX);
	}

	@Test
	public void test()
	{
		_formatSource(ExampleSQL.TEST);
	}

	private void _formatSource(String pSource)
	{
		String formatted = SQL.format(pSource, new Settings());

		System.out.println(pSource);
		System.out.println("--------------------------------------");
		System.out.println(formatted);
	}
}
