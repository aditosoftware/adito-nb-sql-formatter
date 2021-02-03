package de.adito.aditoweb.nbm.sqlformatter.impl.gui.actions;

import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import org.openide.awt.*;
import org.openide.cookies.EditorCookie;
import org.openide.util.NbBundle;

import javax.swing.text.Document;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Contextmenu Action which can copy the whole sql to an JavaScript-String
 *
 * @author p.neub, 01.12.2020
 */
@SuppressWarnings("unused") // Netbeans
@ActionID(category = "Edit", id = "de.adito.nbm.sqlformatter.gui.actions.CopyToStringAction")
@ActionRegistration(displayName = "#LBL_CopyToString_TITLE")
@ActionReference(path = "Editors/text/x-sql/Popup", position = 3500)
public class CopyToStringAction implements ActionListener
{
  /**
   * Stores all information about the current document
   * Needed für reading the sql
   */
  private final EditorCookie context;

  /**
   * Constructor called by NetBeans
   *
   * @param pContext Holds all information about the current document
   */
  public CopyToStringAction(EditorCookie pContext)
  {
    context = pContext;
  }

  /**
   * Once the Action got clicked, this function will be called by NetBeans
   * This function copies the while SQL into the clipboard but as JavaScript-String
   *
   * @param e the ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    Settings settings = Settings.getSettings();

    String text = context.getOpenedPanes()[0].getSelectedText();
    if (text == null)
      text = context.getOpenedPanes()[0].getText();
    text = text.replaceAll("\"", "\\\\\"");
    text = text.replaceAll("\t", "    ");

    ArrayList<String> jsLines = new ArrayList<>();
    for (String line : text.trim().split("\r\n|\n|\r"))
    {
      if (settings.gapInsideQuotes)
        line = '\"' + line;
      else
      {
        int indetPos = line.replaceAll("[^ ]", "").length() - 1;
        line = line.substring(0, indetPos) + '\"' + line.substring(indetPos);
      }

      String lineStr = settings.copyToStringPlusRight ? line + "\" + \"\\n\" +" : "+ " + line + "\" + \"\\n\"";
      jsLines.add(lineStr);
    }

    Toolkit.getDefaultToolkit().getSystemClipboard()
        .setContents(new StringSelection(String.join("\r\n", jsLines)), null);
    StatusDisplayer.getDefault()
        .setStatusText(NbBundle.getMessage(CopyToStringAction.class, "LBL_CopyToString_MESSAGE"));
  }
}
