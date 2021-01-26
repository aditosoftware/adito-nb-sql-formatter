package de.adito.aditoweb.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;
import org.openide.util.NbPreferences;

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
   * What kind of indent should be used
   */
  private final EIndentMode indentMode;

  /**
   * What kind of line ending should be used
   */
  private final ELineEnding lineEnding;

  /**
   * In witch 'letter case' should words be formatted
   */
  private final ELetterCaseMode wordCaseMode;

  /**
   * In witch 'letter case' should words be formatted
   */
  private final ELetterCaseMode keywordCaseMode;

  /**
   * Default constructor
   * constructs a Settings object with default settings
   */
  public Settings()
  {
    this(EIndentMode.SPACE_4, ELineEnding.CRLF, ELetterCaseMode.UPPERCASE, ELetterCaseMode.LOWERCASE);
  }

  /**
   * Creates a settings object with the specified properties
   *
   * @param pIndentMode      What kind of indent should be used
   * @param pLineEnding      What kind of line ending should be used
   * @param pWordCaseMode    In witch 'letter case' should words be formatted
   * @param pKeywordCaseMode In witch 'letter case' should words be formatted
   */
  public Settings(@NotNull EIndentMode pIndentMode, @NotNull ELineEnding pLineEnding, @NotNull ELetterCaseMode pWordCaseMode, @NotNull ELetterCaseMode pKeywordCaseMode)
  {
    indentMode = pIndentMode;
    lineEnding = pLineEnding;
    wordCaseMode = pWordCaseMode;
    keywordCaseMode = pKeywordCaseMode;
  }

  /**
   * Loads the settings from storage
   *
   * @return the loaded settings
   */
  @NotNull
  public static Settings getSettings()
  {
    Settings defaultSetting = new Settings();
    return new Settings(
        EIndentMode.values()[_PREFERENCES.getInt("indentMode", defaultSetting.indentMode.ordinal())],
        ELineEnding.values()[_PREFERENCES.getInt("lineEnding", defaultSetting.lineEnding.ordinal())],
        ELetterCaseMode.values()[_PREFERENCES.getInt("wordCaseMode", defaultSetting.wordCaseMode.ordinal())],
        ELetterCaseMode.values()[_PREFERENCES.getInt("keywordCaseMode", defaultSetting.keywordCaseMode.ordinal())]
    );
  }

  /**
   * Saves the settings to storage
   *
   * @param settings the settings to store
   */
  public static void setSettings(@NotNull Settings settings)
  {
    _PREFERENCES.putInt("indentMode", settings.indentMode.ordinal());
    _PREFERENCES.putInt("lineEnding", settings.lineEnding.ordinal());
    _PREFERENCES.putInt("wordCaseMode", settings.wordCaseMode.ordinal());
    _PREFERENCES.putInt("keywordCaseMode", settings.keywordCaseMode.ordinal());
  }

  /**
   * What kind of indent should be used
   *
   * @return the kind of indent which should be used
   */
  @NotNull
  public EIndentMode getIndentMode()
  {
    return indentMode;
  }

  /**
   * What kind of line ending should be used
   *
   * @return the kind of line ending which should be used
   */
  @NotNull
  public ELineEnding getLineEnding()
  {
    return lineEnding;
  }

  /**
   * In witch 'letter case' should words be formatted
   *
   * @return the 'letter case' for words
   */
  @NotNull
  public ELetterCaseMode getWordCaseMode()
  {
    return wordCaseMode;
  }

  /**
   * In witch 'letter case' should keywords be formatted
   *
   * @return the 'letter case' for keywords
   */
  @NotNull
  public ELetterCaseMode getKeywordCaseMode()
  {
    return keywordCaseMode;
  }

  /**
   * Overrides the equals method
   * used to check if the settings have been changed
   *
   * @param o the second settings object
   * @return whether the settings are equal or not
   */
  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Settings settings = (Settings) o;

    if (indentMode != settings.indentMode) return false;
    if (lineEnding != settings.lineEnding) return false;
    if (wordCaseMode != settings.wordCaseMode) return false;
    return keywordCaseMode == settings.keywordCaseMode;
  }

  /**
   * Generates a 'unique' hash for each setting
   *
   * @return the 'unique' hash
   */
  @Override
  public int hashCode()
  {
    int result = indentMode.hashCode();
    result = 31 * result + lineEnding.hashCode();
    result = 31 * result + wordCaseMode.hashCode();
    result = 31 * result + keywordCaseMode.hashCode();
    return result;
  }
}
