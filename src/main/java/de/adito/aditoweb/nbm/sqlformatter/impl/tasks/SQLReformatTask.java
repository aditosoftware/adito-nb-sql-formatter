package de.adito.aditoweb.nbm.sqlformatter.impl.tasks;

import de.adito.aditoweb.nbm.sqlformatter.impl.formatting.Formatter;
import de.adito.aditoweb.nbm.sqlformatter.impl.settings.Settings;
import org.netbeans.modules.editor.indent.spi.*;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Reformat task which adds the SQL formatting functionality into NetBeans
 *
 * @author p.neub, 01.12.2020
 */
public class SQLReformatTask implements ReformatTask
{
  /**
   * Context information for both indentation and reformatting.
   */
  private final Context context;

  /**
   * Constructor called by NetBeans
   *
   * @param pContext Context information for both indentation and reformatting.
   */
  public SQLReformatTask(Context pContext)
  {
    context = pContext;
  }

  /**
   * if the format event gets triggered, this function will be executed
   *
   * @throws BadLocationException ignored
   */
  @Override
  public void reformat() throws BadLocationException
  {
    Document doc = context.document();
    String docText = doc.getText(context.startOffset(),
                                 context.endOffset() - context.startOffset());
    String formatted = new Formatter(docText, Settings.getSettings()).format();
    doc.remove(context.startOffset(), context.endOffset() - context.startOffset());
    doc.insertString(context.startOffset(), formatted, null);
  }

  /**
   * Returns null, because no extra locking is necessary.
   *
   * @return null because we don't want extra locking
   */
  @Override
  public ExtraLock reformatLock()
  {
    return null;
  }
}
