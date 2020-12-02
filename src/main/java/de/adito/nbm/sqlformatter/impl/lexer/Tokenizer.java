package de.adito.nbm.sqlformatter.impl.lexer;

import de.adito.nbm.sqlformatter.SQL;

import java.util.function.Function;

/**
 * Utility class used to split the input SQL into tokens/literals
 *
 * @author p.neub, 01.12.2020
 */
public class Tokenizer
{
	private final String text;
	private int pos = 0;

	/**
	 * Constructor of the tokenizer
	 *
	 * @param pText the text witch needs to be tokenized
	 */
	public Tokenizer(String pText)
	{
		text = pText;
	}

	private Token _nextReserved(String[] pArr, TokenType pTokenType)
	{
		for (String reservedWord : pArr)
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
			String text = _readWhile(ch -> ch != currChar);
			pos++;
			return new Token(TokenType.STRING, currChar + text + currChar);
		}

		if (pos + 1 < text.length()) switch (text.substring(pos, pos + 2))
		{
			case "--":
				String lineComment = _readWhile(ch -> ch != '\n').trim();
				return new Token(TokenType.LINE_COMMENT, lineComment);
			case "/*":
				String blockComment = _readWhile(ch -> !text.startsWith("*/", pos)).trim();
				pos += 2;
				return new Token(TokenType.BLOCK_COMMENT, blockComment + "*/");
		}

		if (SQL.OPERATORS.indexOf(currChar) != -1)
		{
			String operator = _readWhile(ch -> SQL.OPERATORS.indexOf(ch) != -1);
			return new Token(TokenType.OPERATOR, operator);
		}
		if (Character.isDigit(currChar))
		{
			String number = _readWhile(ch -> ch == '.' ||
				Character.isDigit(ch));
			return new Token(TokenType.NUMBER, number);
		}

		Token nextReserved = _nextReserved(SQL.RESERVED_TOPLEVEL_WORDS, TokenType.RESERVED_TOPLEVEL);
		if (nextReserved != null) return nextReserved;
		nextReserved = _nextReserved(SQL.RESERVED_WRAPPING_WORDS, TokenType.RESERVED_WRAPPING);
		if (nextReserved != null) return nextReserved;
		nextReserved = _nextReserved(SQL.RESERVED_WORDS, TokenType.RESERVED);
		if (nextReserved != null) return nextReserved;

		if (currChar == '_' || Character.isAlphabetic(currChar))
		{
			String word = _readWhile(ch -> ch == '_' ||
				Character.isAlphabetic(ch) || Character.isDigit(ch));
			return new Token(TokenType.WORD, word);
		}

		pos++;
		return new Token(TokenType.SYMBOL, Character.toString(currChar));
	}

	/**
	 * Reads while the condition checkFunc is satisfied
	 *
	 * @param pCheckFunc the condition
	 * @return returns the read text
	 */
	private String _readWhile(Function<Character, Boolean> pCheckFunc)
	{
		StringBuilder result = new StringBuilder();
		while (pos < text.length())
		{
			if (pos + 1 < text.length()) switch (text.substring(pos, pos + 2))
			{
				case "--":
				case "/*":
					break;
			}
			char currChar = text.charAt(pos);
			if (pCheckFunc.apply(currChar))
			{
				result.append(currChar);
				pos++;
			}
			else break;
		}
		return result.toString();
	}
}
