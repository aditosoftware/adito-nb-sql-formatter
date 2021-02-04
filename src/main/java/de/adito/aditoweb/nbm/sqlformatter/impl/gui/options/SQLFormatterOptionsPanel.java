package de.adito.aditoweb.nbm.sqlformatter.impl.gui.options;

import de.adito.aditoweb.nbm.sqlformatter.impl.formatting.Formatter;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.*;
import org.jetbrains.annotations.NotNull;
import org.openide.util.NbBundle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Objects;

/**
 * Options GUI for SQL Formatter
 *
 * @author p.neub, 01.12.2020
 */
public class SQLFormatterOptionsPanel extends JPanel
{
  private final JTextArea inputSql = new JTextArea("select  case   when CONTACT.PERSON_ID is null then 1   else case    when trim (CONTACT.ORGANISATION_ID  ) = '0' then 2    else 3   end  end,     ADDRESS.ADDRESS,   ADDRESS.BUILDINGNO,  ADDRESS.ZIP, ADDRESS.CITY,     ADDRESS.COUNTRY,  ADDRESS.ADDRESSADDITION, ADDRESS.ADDRIDENTIFIER, ADDRESS.DISTRICT,  ADDRESS.REGION, ADDRESS.PROVINCE,     CONTACT.DEPARTMENT,     CONTACT.CONTACTROLE,     CONTACT.CONTACTPOSITION,     CONTACT.LETTERSALUTATION,     ORGANISATION.name,     PERSON.FIRSTNAME,     PERSON.MIDDLENAME,     PERSON.LASTNAME,     PERSON.SALUTATION,     PERSON.TITLE,     PERSON.TITLESUFFIX,  coalesce ( CONTACT.ISOLANGUAGE,  (  select   c.ISOLANGUAGE  from CONTACT  c   where  c.ORGANISATION_ID = CONTACT.ORGANISATION_ID                 and PERSON_ID is null         )     ),      'Max Musterman',     (   select ADDR_FORMAT from AB_COUNTRYINFO  where  ISO2 = ADDRESS.COUNTRY     ), ADDRESS.ADDR_TYPE from CONTACT join PERSON     on CONTACT.PERSON_ID = PERSON.PERSONID left join ORGANISATION     on CONTACT.ORGANISATION_ID = ORGANISATION.ORGANISATIONID left join ADDRESS     on CONTACT.ADDRESS_ID = ADDRESS.ADDRESSID where     CONTACT.CONTACTID in (  '0ae3a779-131c-440b-a653-569ac25ea699' )  and TEST = 123 and TEST = 123  or SDF = 45");
  private final JTextArea outputSql = new JTextArea();

  private final JComboBox<ELetterCaseMode> wordCaseMode = new JComboBox<>(ELetterCaseMode.values());
  private final JComboBox<ELetterCaseMode> keywordCaseMode = new JComboBox<>(ELetterCaseMode.values());

  private final JCheckBox caseWhenInSingleLine = new JCheckBox();
  private final JCheckBox newlineBeforeComma = new JCheckBox();

  private final JCheckBox copyToStringPlusRight = new JCheckBox();
  private final JCheckBox gapInsideQuotes = new JCheckBox();

  /**
   * Initializes the GUI
   */
  public SQLFormatterOptionsPanel()
  {
    setLayout(new GridLayout(1, 2));
    setPreferredSize(new Dimension(880, 380));

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    add(leftPanel);

    _addGroupLabel(leftPanel, "Formatting");
    _addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_WORD_CASE"), wordCaseMode);
    _addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_KEYWORD_CASE"), keywordCaseMode);
    _addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_CASE_SINGLE_LINE"), caseWhenInSingleLine);
    _addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_NEWLINE_COMMA"), newlineBeforeComma);

    _addGroupLabel(leftPanel, "CopyToString");
    _addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_PLUS_RIGHT"), copyToStringPlusRight);
    _addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_GAP_INSIDE_QUOTES"), gapInsideQuotes);

    wordCaseMode.addActionListener(e -> _update());
    keywordCaseMode.addActionListener(e -> _update());

    caseWhenInSingleLine.addActionListener(e -> _update());
    newlineBeforeComma.addActionListener(e -> _update());

    copyToStringPlusRight.addActionListener(e -> _update());
    gapInsideQuotes.addActionListener(e -> _update());

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

  /**
   * Adds a group label to the given JPanel
   *
   * @param pPanel the target panel
   * @param pText the label text
   */
  private void _addGroupLabel(@NotNull JPanel pPanel, @NotNull String pText)
  {
    pPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    JPanel componentPanel = new JPanel();
    componentPanel.setLayout(new GridLayout(1, 1));
    componentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
    JLabel label = new JLabel(pText);
    label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 16));
    componentPanel.add(label);
    componentPanel.add(new JLabel());
    pPanel.add(componentPanel);
    pPanel.add(Box.createRigidArea(new Dimension(0, 3)));
  }

  /**
   * Adds a settings component the the given JPanel
   *
   * @param pPanel the target panel
   * @param pText the component description
   * @param pComponent the component witch will be added to the panel
   */
  private void _addConfigComponent(@NotNull JPanel pPanel, @NotNull String pText, @NotNull Component pComponent)
  {
    JPanel componentPanel = new JPanel();
    componentPanel.setLayout(new GridLayout(1, 1));
    componentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
    componentPanel.add(new JLabel(pText));
    componentPanel.add(pComponent);
    pPanel.add(componentPanel);
    pPanel.add(Box.createRigidArea(new Dimension(0, 3)));
  }

  /**
   * Returns the settings defined by the GUI
   *
   * @return the settings
   */
  @NotNull
  public Settings getSettings()
  {
    return new Settings(
        (ELetterCaseMode) Objects.requireNonNull(wordCaseMode.getSelectedItem()),
        (ELetterCaseMode) Objects.requireNonNull(keywordCaseMode.getSelectedItem()),

        caseWhenInSingleLine.isSelected(),
        newlineBeforeComma.isSelected(),

        copyToStringPlusRight.isSelected(),
        gapInsideQuotes.isSelected()
    );
  }

  /**
   * Sets the settings for the GUI
   *
   * @param pSettings The settings to set
   */
  public void setSettings(@NotNull Settings pSettings)
  {
    wordCaseMode.setSelectedItem(pSettings.wordCaseMode);
    keywordCaseMode.setSelectedItem(pSettings.keywordCaseMode);

    caseWhenInSingleLine.setSelected(pSettings.caseWhenInSingleLine);
    newlineBeforeComma.setSelected(pSettings.newlineBeforeComma);

    newlineBeforeComma.setSelected(pSettings.copyToStringPlusRight);
    gapInsideQuotes.setSelected(pSettings.gapInsideQuotes);

    _update();
  }

  /**
   * This function updates the formatted output sql
   */
  private void _update()
  {
    outputSql.setText(new Formatter(inputSql.getText(), getSettings()).format());
  }
}
