package de.adito.nbm.sqlformatter.impl.formatting;

import de.adito.nbm.formatter.api.IFormatter;
import de.adito.nbm.sqlformatter.Settings;
import de.adito.nbm.sqlformatter.impl.lexer.Token;
import de.adito.nbm.sqlformatter.impl.lexer.TokenType;
import de.adito.nbm.sqlformatter.impl.lexer.Tokenizer;

/**
 * Implements SQL-Formatting functionality
 *
 * @author p.neub, 01.12.2020
 */
public class Formatter implements IFormatter
{
	/**
	 * The tokenizer holding the input SQL
	 */
	private final Tokenizer tokenizer;

	/**
	 * The settings which are needed for formatting the SQL
	 */
	private final Settings settings;

	/**
	 * The TextBuilder which is used to write the formatted SQL into a central object
	 */
	private final TextBuilder text;

	/**
	 * The current Token
	 */
	private Token curr = null;

	/**
	 * The last token
	 */
	private Token last = null;

	/**
	 * Constructor of the Formatter
	 *
	 * @param pTokenizer The Tokenizer holding the input SQL
	 * @param pSettings The settings which are needed for formatting the SQL
	 */
	public Formatter(Tokenizer pTokenizer, Settings pSettings)
	{
		tokenizer = pTokenizer;
		settings = pSettings;
		text = new TextBuilder(settings);
	}

	/**
	 * This function gets called after each token has been written to the output TextBuilder
	 * and is used for writing a proper spacing between each token
	 *
	 * For example operator tokens need a space on both sides, but commas only need a space after the token
	 */
	private void _writeSpacing()
	{
		if (!text.spacingAllowed())
			return;

		if (curr.check(TokenType.SYMBOL, ";")) return;
		if (last.check(TokenType.SYMBOL, ";")) {
			text.decIndent(Integer.MAX_VALUE);
			text.singleNewline();
			text.newline();
			text.newline();
			return;
		}

		if (curr.check(TokenType.SYMBOL, ".") || last.check(TokenType.SYMBOL, ".")) return;
		if (curr.check(TokenType.SYMBOL, ",")) return;

		if (last.isText() && curr.check(TokenType.SYMBOL, "(")) return;
		if (last.check(TokenType.SYMBOL, "(") || curr.check(TokenType.SYMBOL, ")")) return;

		if (last.check(TokenType.SYMBOL, ",")) text.singleNewline();
		else text.write(" ");
	}

	/**
	 * Implementation of the core formatting algorithm
	 *
	 * This function loops through all tokens and decides if an how many spaces/line breaks
	 * needs to be written before and after the token
	 *
	 * @return The formatted SQL
	 */
	public String format()
	{
		boolean indentCompressFlag = true;

		while (true)
		{
			curr = tokenizer.next();
			if (curr == null) return text.finish();

			switch (curr.type)
			{
				case RESERVED:
					if (curr.text.equalsIgnoreCase("CASE"))
					{
						if (last.isText()) text.write(" ");
						text.write(curr.format(settings));
						text.incIndent(1);
					}
					else if (curr.text.equalsIgnoreCase("END"))
					{
						text.decIndent(1);
						text.singleNewline();
						text.write(curr.format(settings));
					}
					else
					{
						_writeSpacing();
						text.write(curr.format(settings));
					}
					break;
				case RESERVED_TOPLEVEL:
					text.decIndent(0);
					if (last == null || !last.check(TokenType.SYMBOL, "(")) text.singleNewline();
					text.write(curr.format(settings));
					text.incIndent(0);
					text.singleNewline();
					break;
				case RESERVED_WRAPPING:
					text.singleNewline();
					text.write(curr.format(settings));
					break;
				default:
					_writeSpacing();
					text.write(curr.format(settings));
					break;
			}

			if (curr.check(TokenType.SYMBOL, "("))
			{
				if (last.isText()) indentCompressFlag = true;
				else
				{
					text.decIndent(0);
					indentCompressFlag = false;
				}
				text.incIndent(2);
			}
			if (curr.check(TokenType.SYMBOL, ")"))
			{
				text.decIndent(2);
				if (!indentCompressFlag) text.incIndent(0);
			}
			last = curr;
		}
	}
}
