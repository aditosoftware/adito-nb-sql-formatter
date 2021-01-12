package de.adito.nbm.sqlformatter.impl.lexer;

import de.adito.nbm.sqlformatter.Settings;

/**
 * Represents a SQL literal
 *
 * @author p.neub, 01.12.2020
 */
public class Token
{
	/**
	 * the type of the token
	 */
	public final TokenType type;

	/**
	 * the text of the token, usually the text representation of the token (not formatted)
	 */
	public final String text;

	/**
	 * Token constructor
	 *
	 * @param pType the type of the token
	 * @param pText the text of the token, usually the text representation of the token (not formatted)
	 */
	public Token(TokenType pType, String pText)
	{
		type = pType;
		text = pText;
	}

	/**
	 * Returns the token either UNALTERED or UPPERCASE/LOWERCASE
	 * @param pLetterCaseMode the LetterCaseMode indicating if the token should be returned UNALTERED or LOWERCASE/UPPERCASE
	 * @return returns the 'formatted' token in text form
	 */
	private String _formatLetterCase(Settings.LetterCaseMode pLetterCaseMode)
	{
		switch (pLetterCaseMode)
		{
			case LOWERCASE:
				return text.toLowerCase();
			case UPPERCASE:
				return text.toUpperCase();
			default:
				return text;
		}
	}

	/**
	 * Formats the SQL-Token following the formatting settings
	 *
	 * @param pSettings the formatting settings
	 * @return the formatted token in text form
	 */
	public String format(Settings pSettings)
	{
		switch (type)
		{
			case WORD:
				return _formatLetterCase(pSettings.wordCaseMode);
			case RESERVED:
			case RESERVED_TOPLEVEL:
			case RESERVED_WRAPPING:
				return _formatLetterCase(pSettings.keywordCaseMode);
			default:
				return text;
		}
	}

	/**
	 * Indicates if the token is WORD, RESERVED_TOPLEVEL, RESERVED_WRAPPING or RESERVED
	 *
	 * @return if the token WORD, RESERVED_TOPLEVEL, RESERVED_WRAPPING or RESERVED then true otherwise false
	 */
	public boolean isText()
	{
		return type == TokenType.WORD || type == TokenType.RESERVED_TOPLEVEL
			|| type == TokenType.RESERVED_WRAPPING || type == TokenType.RESERVED;
	}

	/**
	 * Checks two tokens for equality
	 *
	 * @param pType the type of the second token
	 * @param pText the text of the second token
	 * @return true if the tokens equals each other, otherwise false
	 */
	public boolean check(TokenType pType, String pText)
	{
		return type == pType && text.equals(pText);
	}
}
