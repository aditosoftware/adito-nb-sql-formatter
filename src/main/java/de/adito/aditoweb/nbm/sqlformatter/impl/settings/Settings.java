package de.adito.aditoweb.nbm.sqlformatter.impl.settings;

import org.jetbrains.annotations.NotNull;
import org.openide.util.NbPreferences;

import java.util.Objects;
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
    this(EIndentMode.SPACE_2, ELineEnding.CRLF, ELetterCaseMode.UPPERCASE, ELetterCaseMode.LOWERCASE);
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
        EIndentMode.valueOf(_PREFERENCES.get("indentMode", defaultSetting.indentMode.name())),
        ELineEnding.valueOf(_PREFERENCES.get("lineEnding", defaultSetting.lineEnding.name())),
        ELetterCaseMode.valueOf(_PREFERENCES.get("wordCaseMode", defaultSetting.wordCaseMode.name())),
        ELetterCaseMode.valueOf(_PREFERENCES.get("keywordCaseMode", defaultSetting.keywordCaseMode.name()))
    );
  }

  /**
   * Saves the settings to storage
   *
   * @param settings the settings to store
   */
  public static void setSettings(@NotNull Settings settings)
  {
    _PREFERENCES.put("indentMode", settings.indentMode.name());
    _PREFERENCES.put("lineEnding", settings.lineEnding.name());
    _PREFERENCES.put("wordCaseMode", settings.wordCaseMode.name());
    _PREFERENCES.put("keywordCaseMode", settings.keywordCaseMode.name());
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

  @Override
  public boolean equals(Object pO)
  {
    if (this == pO) return true;
    if (pO == null || getClass() != pO.getClass()) return false;
    Settings settings = (Settings) pO;
    return indentMode == settings.indentMode && lineEnding == settings.lineEnding &&
        wordCaseMode == settings.wordCaseMode && keywordCaseMode == settings.keywordCaseMode;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(indentMode, lineEnding, wordCaseMode, keywordCaseMode);
  }
}
