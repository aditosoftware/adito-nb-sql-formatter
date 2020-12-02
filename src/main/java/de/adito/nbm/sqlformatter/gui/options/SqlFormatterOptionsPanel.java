package de.adito.nbm.sqlformatter.gui.options;

import de.adito.nbm.sqlformatter.SQL;
import de.adito.nbm.sqlformatter.Settings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Options GUI for SQL Formatter
 *
 * @author p.neub, 01.12.2020
 */
public class SqlFormatterOptionsPanel extends JPanel
{
	private final JTextArea inputSql = new JTextArea("select mIN(1,2), case when blA then 12 when TTt then 11 else 66 end,asdDDsd,(select * from bla where bla = 4),max(test, 1,2),ABc from TaaaaBLE where T.E ='12' and 4 join BLA on 1=2");
	private final JTextArea outputSql = new JTextArea();

	private final JComboBox<Settings.IndentMode> indentMode = new JComboBox<>(Settings.IndentMode.values());
	private final JComboBox<Settings.LineEnding> lineEnding = new JComboBox<>(Settings.LineEnding.values());
	private final JComboBox<Settings.LetterCaseMode> wordCaseMode = new JComboBox<>(Settings.LetterCaseMode.values());
	private final JComboBox<Settings.LetterCaseMode> keywordCaseMode = new JComboBox<>(Settings.LetterCaseMode.values());

	/**
	 * Initializes the GUI
	 */
	public SqlFormatterOptionsPanel()
	{
		outputSql.setEditable(false);

		add(inputSql);
		add(outputSql);

		add(indentMode);
		add(lineEnding);
		add(wordCaseMode);
		add(keywordCaseMode);

		_update();
		inputSql.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				_update();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				_update();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				_update();
			}
		});
	}

	/**
	 * Returns the settings defined by the GUI
	 *
	 * @return the settings
	 */
	public Settings getSettings()
	{
		return new Settings(
			(Settings.IndentMode) indentMode.getSelectedItem(),
			(Settings.LineEnding) lineEnding.getSelectedItem(),
			(Settings.LetterCaseMode) wordCaseMode.getSelectedItem(),
			(Settings.LetterCaseMode) keywordCaseMode.getSelectedItem()
		);
	}

	/**
	 * Sets the settings for the GUI
	 *
	 * @param pSettings The settings to set
	 */
	public void setSettings(Settings pSettings)
	{
		indentMode.setSelectedItem(pSettings.indentMode);
		lineEnding.setSelectedItem(pSettings.lineEnding);
		wordCaseMode.setSelectedItem(pSettings.wordCaseMode);
		keywordCaseMode.setSelectedItem(pSettings.keywordCaseMode);
		_update();
	}

	/**
	 * This function updates the formatted output sql
	 */
	private void _update()
	{
		outputSql.setText(SQL.format(inputSql.getText(), getSettings()));
	}
}
