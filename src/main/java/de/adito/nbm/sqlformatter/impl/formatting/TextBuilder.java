package de.adito.nbm.sqlformatter.impl.formatting;

import de.adito.nbm.sqlformatter.Settings;

import java.util.Stack;

/**
 * Writes the formatted sql to an sqlbuilder and handles the indentation
 *
 * @author p.neub, 01.12.2020
 */
public class TextBuilder
{
	private final Settings settings;
	private final StringBuilder builder = new StringBuilder();
	private boolean newlineFlag = true;
	private final Stack<Integer> indents = new Stack<>();

	public TextBuilder(Settings pSettings)
	{
		settings = pSettings;
	}

	/**
	 * Returns a String which represents the indents
	 *
	 * @return the string representing the indents
	 */
	private String _getIndents()
	{
		return new String(new char[indents.size()]).replace("\0", settings.indentMode.getIndent());
	}

	/**
	 * Writes a new line
	 */
	public void newline()
	{
		newlineFlag = true;
		builder.append(settings.lineEnding.getLineEnding());
	}

	/**
	 * Writes a new line but if the last char is already a new line it does nothing
	 */
	public void singleNewline()
	{
		if (!newlineFlag) newline();
	}

	/**
	 * Writes some text, this should not be used for writing new lines
	 *
	 * @param pText the text which will be written to the StringBuilder
	 */
	public void write(String pText)
	{
		if (newlineFlag)
		{
			newlineFlag = false;
			builder.append(_getIndents());
		}
		builder.append(pText);
	}

	/**
	 * Convert the underlying StringBuilder to an String an returns it
	 *
	 * @return the formatted SQL as String
	 */
	public String finish()
	{
		return builder.toString();
	}

	/**
	 * Checks if spacing is allowed at the current state of the TextBuilder
	 * - Checks if the last char is a newline if so spacing is not allowed
	 *
	 * @return is spacing allowed
	 */
	public boolean spacingAllowed()
	{
		return !newlineFlag;
	}

	/**
	 * Increases the indents
	 *
	 * @param pLevel The level is used for decreasing indents. Higher level indents always decrease the lower level indents too.
	 */
	public void incIndent(int pLevel)
	{
		indents.push(pLevel);
	}

	/**
	 * Decreases the indents
	 *
	 * @param pLevel Higher level indents always decrease the lower level indents too.
	 */
	public void decIndent(int pLevel)
	{
		while (indents.size() > 0)
		{
			if (indents.peek() == pLevel)
			{
				indents.pop();
				break;
			}
			else if (indents.peek() < pLevel) indents.pop();
			else break;
		}
	}
}
