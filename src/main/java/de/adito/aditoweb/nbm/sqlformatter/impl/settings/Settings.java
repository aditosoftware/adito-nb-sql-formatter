package de.adito.aditoweb.nbm.sqlformatter.impl.settings;

import lombok.NonNull;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.openide.util.NbPreferences;

import java.util.*;
import java.util.prefs.Preferences;

/**
 * This class stores all the settings for the SQL Formatter Plugin
 * it also handles settings saving and loading
 *
 * @author p.neub, 01.12.2020
 */
public class Settings
{
  private static final Preferences _PREFERENCES = NbPreferences.forModule(Settings.class);

  /**
   * In witch 'letter case' should words be formatted
   */
  public final ELetterCaseMode wordCaseMode;

  /**
   * In witch 'letter case' should words be formatted
   */
  public final ELetterCaseMode keywordCaseMode;

  /**
   * If true the whole case block will be written into a single line
   */
  public final boolean caseWhenInSingleLine;

  /**
   * Idicates whether the comma should be places before or after a newline
   */
  public final boolean newlineBeforeComma;

  /**
   * Indicates whether the plus should be placed on the left/right of the line
   */
  public final boolean copyToStringPlusRight;

  /**
   * Indicates whether the whitespace gab should be places inside the quotes
   */
  public final boolean gapInsideQuotes;

  /**
   * Default constructor
   * constructs a Settings object with default settings
   */
  public Settings()
  {
    this(ELetterCaseMode.UPPERCASE, ELetterCaseMode.LOWERCASE,
         false, false,
         true, true);
  }

  /**
   * Creates a settings object with the specified properties
   *
   * @param pWordCaseMode          In witch 'letter case' should words be formatted
   * @param pKeywordCaseMode       In witch 'letter case' should words be formatted
   * @param pCaseWhenInSingleLine  If true the whole case block will be written into a single line
   * @param pNewlineBeforeComma    Indicates whether the comma should be places before or after a newline
   * @param pCopyToStringPlusRight Indicates whether the plus should be placed on the left/right of the line
   * @param pGapInsideQuotes       Indicates whether the whitespace gab should be places inside the quotes
   */
  public Settings(@NonNull ELetterCaseMode pWordCaseMode, @NonNull ELetterCaseMode pKeywordCaseMode,
                  boolean pCaseWhenInSingleLine, boolean pNewlineBeforeComma,
                  boolean pCopyToStringPlusRight, boolean pGapInsideQuotes)
  {
    wordCaseMode = pWordCaseMode;
    keywordCaseMode = pKeywordCaseMode;

    caseWhenInSingleLine = pCaseWhenInSingleLine;
    newlineBeforeComma = pNewlineBeforeComma;

    copyToStringPlusRight = pCopyToStringPlusRight;
    gapInsideQuotes = pGapInsideQuotes;
  }

  /**
   * Loads the settings from storage
   *
   * @return the loaded settings
   */
  @NonNull
  public static Settings getSettings()
  {
    Settings defaultSetting = new Settings();
    return new Settings(
        ELetterCaseMode.valueOf(_PREFERENCES.get("wordCaseMode", defaultSetting.wordCaseMode.name())),
        ELetterCaseMode.valueOf(_PREFERENCES.get("keywordCaseMode", defaultSetting.keywordCaseMode.name())),

        _PREFERENCES.getBoolean("caseWhenInSingleLine", defaultSetting.caseWhenInSingleLine),
        _PREFERENCES.getBoolean("newlineBeforeComma", defaultSetting.newlineBeforeComma),

        _PREFERENCES.getBoolean("copyToStringPlusRight", defaultSetting.copyToStringPlusRight),
        _PREFERENCES.getBoolean("gapInsideQuotes", defaultSetting.gapInsideQuotes)
    );
  }

  /**
   * Saves the settings to storage
   *
   * @param settings the settings to store
   */
  public static void setSettings(@NonNull Settings settings)
  {
    _PREFERENCES.put("wordCaseMode", settings.wordCaseMode.name());
    _PREFERENCES.put("keywordCaseMode", settings.keywordCaseMode.name());

    _PREFERENCES.putBoolean("caseWhenInSingleLine", settings.caseWhenInSingleLine);
    _PREFERENCES.putBoolean("newlineBeforeComma", settings.newlineBeforeComma);

    _PREFERENCES.putBoolean("copyToStringPlusRight", settings.copyToStringPlusRight);
    _PREFERENCES.putBoolean("gapInsideQuotes", settings.gapInsideQuotes);
  }

  /**
   * Reads the indentation settings from the designer
   *
   * @return the string used for indentation
   */
  public static String getIndentStr()
  {
    Preferences pref = MimeLookup.getLookup("").lookup(Preferences.class);
    if (!pref.getBoolean("enable-indent", true))
      return "";
    if (!pref.getBoolean("expand-tabs", true))
      return "\t";

    int tabSize = pref.getInt("spaces-per-tab", 4);
    char[] tabChars = new char[tabSize];
    Arrays.fill(tabChars, ' ');
    return new String(tabChars);
  }

  /**
   * Returns the new line character/string
   *
   * @return the new line character/string
   */
  public static String getNewlineStr()
  {
    return "\r\n";
  }

  @Override
  public boolean equals(Object pO)
  {
    if (this == pO) return true;
    if (pO == null || getClass() != pO.getClass()) return false;
    Settings settings = (Settings) pO;
    return caseWhenInSingleLine == settings.caseWhenInSingleLine && newlineBeforeComma == settings.newlineBeforeComma && copyToStringPlusRight == settings.copyToStringPlusRight && gapInsideQuotes == settings.gapInsideQuotes && wordCaseMode == settings.wordCaseMode && keywordCaseMode == settings.keywordCaseMode;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(wordCaseMode, keywordCaseMode, caseWhenInSingleLine, newlineBeforeComma, copyToStringPlusRight, gapInsideQuotes);
  }
}
