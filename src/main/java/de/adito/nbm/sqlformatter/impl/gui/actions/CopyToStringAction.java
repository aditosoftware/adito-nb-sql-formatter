package de.adito.nbm.sqlformatter.impl.gui.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
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
    try
    {
      Document doc = context.getDocument();
      String text = doc.getText(0, doc.getLength()).replaceAll("\"", "\\\\\"");

      ArrayList<String> jsLines = new ArrayList<>();
      for (String line : text.trim().split("\r\n|\n|\r"))
        jsLines.add("\"" + line + "\"");

      Toolkit.getDefaultToolkit().getSystemClipboard()
          .setContents(new StringSelection(String.join(" +\r\n", jsLines)), null);
      StatusDisplayer.getDefault()
          .setStatusText(NbBundle.getMessage(CopyToStringAction.class, "LBL_CopyToString_MESSAGE"));
    }
    catch (Exception ignored)
    {
      // Dann halt nicht
    }
  }
}
