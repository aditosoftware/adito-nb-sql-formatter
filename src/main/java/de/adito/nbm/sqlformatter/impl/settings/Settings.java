package de.adito.nbm.sqlformatter.impl.settings;

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
  private static final Preferences preferences = NbPreferences.forModule(Settings.class);

  /**
   * What kind of indent should be used
   */
  public IndentMode indentMode;

  /**
   * What kind of line ending should be used
   */
  public LineEnding lineEnding;

  /**
   * In witch 'letter case' should words be formatted
   */
  public LetterCaseMode wordCaseMode;

  /**
   * In witch 'letter case' should words be formatted
   */
  public LetterCaseMode keywordCaseMode;

  /**
   * Default constructor
   * constructs a Settings object with default settings
   */
  public Settings()
  {
    this(IndentMode.SPACE_4, LineEnding.CRLF, LetterCaseMode.UPPERCASE, LetterCaseMode.LOWERCASE);
  }

  /**
   * Creates a settings object with the specified properties
   *
   * @param pIndentMode      What kind of indent should be used
   * @param pLineEnding      What kind of line ending should be used
   * @param pWordCaseMode    In witch 'letter case' should words be formatted
   * @param pKeywordCaseMode In witch 'letter case' should words be formatted
   */
  public Settings(@NotNull IndentMode pIndentMode, @NotNull LineEnding pLineEnding, @NotNull LetterCaseMode pWordCaseMode, @NotNull LetterCaseMode pKeywordCaseMode)
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
        IndentMode.values()[preferences.getInt("indentMode", defaultSetting.indentMode.ordinal())],
        LineEnding.values()[preferences.getInt("lineEnding", defaultSetting.lineEnding.ordinal())],
        LetterCaseMode.values()[preferences.getInt("wordCaseMode", defaultSetting.wordCaseMode.ordinal())],
        LetterCaseMode.values()[preferences.getInt("keywordCaseMode", defaultSetting.keywordCaseMode.ordinal())]
    );
  }

  /**
   * Saves the settings to storage
   *
   * @param settings the settings to store
   */
  public static void setSettings(@NotNull Settings settings)
  {
    preferences.putInt("indentMode", settings.indentMode.ordinal());
    preferences.putInt("lineEnding", settings.lineEnding.ordinal());
    preferences.putInt("wordCaseMode", settings.wordCaseMode.ordinal());
    preferences.putInt("keywordCaseMode", settings.keywordCaseMode.ordinal());
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
