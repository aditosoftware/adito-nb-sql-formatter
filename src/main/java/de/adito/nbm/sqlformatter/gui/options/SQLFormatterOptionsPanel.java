package de.adito.nbm.sqlformatter.gui.options;

import de.adito.nbm.sqlformatter.SQL;
import de.adito.nbm.sqlformatter.Settings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Options GUI for SQL Formatter
 *
 * @author p.neub, 01.12.2020
 */
public class SQLFormatterOptionsPanel extends JPanel
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
	public SQLFormatterOptionsPanel()
	{
		setLayout(new GridLayout(1, 2));
		setPreferredSize(new Dimension(880, 380));

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		addConfigComponent(leftPanel, "Indent Mode", indentMode);
		addConfigComponent(leftPanel, "Line Ending", lineEnding);
		addConfigComponent(leftPanel, "Word Case", wordCaseMode);
		addConfigComponent(leftPanel, "Keyword Case", keywordCaseMode);
		add(leftPanel);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2, 1));
		JScrollPane inputScrollPane = new JScrollPane(inputSql);
		rightPanel.add(inputScrollPane);

		outputSql.setEditable(false);
		JScrollPane outputScrollPane = new JScrollPane(outputSql);
		rightPanel.add(outputScrollPane);
		add(rightPanel);

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

	private <T extends JComboBox<E>, E> void addConfigComponent(JPanel panel, String text, T component)
	{
		component.addActionListener(e -> _update());
		JPanel componentPanel = new JPanel();
		componentPanel.setLayout(new GridLayout(1, 1));
		componentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		componentPanel.add(new JLabel(text));
		componentPanel.add(component);
		panel.add(componentPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 3)));
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
