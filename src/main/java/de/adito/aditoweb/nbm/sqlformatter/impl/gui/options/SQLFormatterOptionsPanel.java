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
  private final JTextArea inputSql = new JTextArea("select  case   when CONTACT.PERSON_ID is null then 1   else case    when trim (CONTACT.ORGANISATION_ID  ) = '0' then 2    else 3   end  end,     ADDRESS.ADDRESS,   ADDRESS.BUILDINGNO,  ADDRESS.ZIP, ADDRESS.CITY,     ADDRESS.COUNTRY,  ADDRESS.ADDRESSADDITION, ADDRESS.ADDRIDENTIFIER, ADDRESS.DISTRICT,  ADDRESS.REGION, ADDRESS.PROVINCE,     CONTACT.DEPARTMENT,     CONTACT.CONTACTROLE,     CONTACT.CONTACTPOSITION,     CONTACT.LETTERSALUTATION,     ORGANISATION.name,     PERSON.FIRSTNAME,     PERSON.MIDDLENAME,     PERSON.LASTNAME,     PERSON.SALUTATION,     PERSON.TITLE,     PERSON.TITLESUFFIX,  coalesce ( CONTACT.ISOLANGUAGE,  (  select   c.ISOLANGUAGE  from CONTACT  c   where  c.ORGANISATION_ID = CONTACT.ORGANISATION_ID                 and PERSON_ID is null         )     ),     '',     '',     '',     (   select ADDR_FORMAT from AB_COUNTRYINFO  where  ISO2 = ADDRESS.COUNTRY     ), ADDRESS.ADDR_TYPE from CONTACT join PERSON     on CONTACT.PERSON_ID = PERSON.PERSONID left join ORGANISATION     on CONTACT.ORGANISATION_ID = ORGANISATION.ORGANISATIONID left join ADDRESS     on CONTACT.ADDRESS_ID = ADDRESS.ADDRESSID where     CONTACT.CONTACTID in (  '0ae3a779-131c-440b-a653-569ac25ea699' )  and TEST = 123 and TEST = 123  or SDF = 45");
  private final JTextArea outputSql = new JTextArea();

  private final JComboBox<ELetterCaseMode> wordCaseMode = new JComboBox<>(ELetterCaseMode.values());
  private final JComboBox<ELetterCaseMode> keywordCaseMode = new JComboBox<>(ELetterCaseMode.values());
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
    addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_WORD_CASE"), wordCaseMode);
    addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_KEYWORD_CASE"), keywordCaseMode);
    addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_NEWLINE_COMMA"), newlineBeforeComma);
    addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_PLUS_RIGHT"), copyToStringPlusRight);
    addConfigComponent(leftPanel, NbBundle.getMessage(SQLFormatterOptionsPanel.class, "LBL_OptionsPanel_CONF_GAP_INSIDE_QUOTES"), gapInsideQuotes);
    add(leftPanel);

    wordCaseMode.addActionListener(e -> _update());
    keywordCaseMode.addActionListener(e -> _update());
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

  private void addConfigComponent(@NotNull JPanel panel, @NotNull String text, @NotNull Component component)
  {
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
  @NotNull
  public Settings getSettings()
  {
    return new Settings(
        (ELetterCaseMode) Objects.requireNonNull(wordCaseMode.getSelectedItem()),
        (ELetterCaseMode) Objects.requireNonNull(keywordCaseMode.getSelectedItem()),
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
