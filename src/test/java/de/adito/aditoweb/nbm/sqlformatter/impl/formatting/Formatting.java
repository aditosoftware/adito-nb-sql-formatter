package de.adito.aditoweb.nbm.sqlformatter.impl.formatting;

import de.adito.aditoweb.nbm.sqlformatter.api.TextBuilder;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import org.junit.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author p.neub, 28.01.2021
 */
public class Formatting
{
  @Test
  public void simple()
  {
    _compareFiles("simple");
  }

  @Test
  public void insert()
  {
    _compareFiles("insert");
  }

  @Test
  public void subsql()
  {
    _compareFiles("subsql");
  }

  /**
   * Compares two sql files (x.sql and x.result.sql)
   * If the formatted x.sql doesn't match with x.result.sql
   * this test will fail
   *
   * @param pName the name of the test sql file
   */
  private void _compareFiles(String pName)
  {
    String provided = new Scanner(Formatting.class.getResourceAsStream(pName + ".sql"), StandardCharsets.UTF_8)
        .useDelimiter("\\A").next();
    String expected = new Scanner(Formatting.class.getResourceAsStream(pName + ".result.sql"), StandardCharsets.UTF_8)
        .useDelimiter("\\A").next();

    String formatted = new Formatter(provided, new Settings(), new TextBuilder<>("  ", "\r\n")).format();
    Assert.assertEquals("The sql doesn't match", expected, formatted);
  }
}
