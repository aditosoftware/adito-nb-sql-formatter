package de.adito.nbm.formatter.api;

/**
 * Provides an interface, for implementing any kind of Formatter
 *
 * @author p.neub, 01.12.2020
 */
public interface IFormatter
{
	/**
	 * This function should return the formatted 'code'
	 *
	 * @return returns the formatted 'code'
	 */
	String format();
}
