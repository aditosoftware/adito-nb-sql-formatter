package de.adito.nbm.sqlformatter.impl.lexer;

/**
 * List of TokenTypes
 */
public enum TokenType
{
	/**
	 * A line comment e.g. // some comment
	 */
	LINE_COMMENT,

	/**
	 * A block comment
	 */
	BLOCK_COMMENT,

	/**
	 * A String e.g. "some text"
	 */
	STRING,

	/**
	 * A number e.g. 123
	 */
	NUMBER,

	/**
	 * A word e.g. CONTACT_ID
	 */
	WORD,

	/**
	 * A reserved word e.g. max
	 */
	RESERVED,

	/**
	 * A reserved toplevel word e.g. select
	 */
	RESERVED_TOPLEVEL,

	/**
	 * A reserved wrapping word e.g. where
	 */
	RESERVED_WRAPPING,

	/**
	 * A operator e.g. +
	 */
	OPERATOR,

	/**
	 * Any other character which isn't captured by any of the other rules
	 */
	SYMBOL
}
