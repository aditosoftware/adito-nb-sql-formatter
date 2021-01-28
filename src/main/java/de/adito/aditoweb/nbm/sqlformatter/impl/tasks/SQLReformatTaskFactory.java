package de.adito.aditoweb.nbm.sqlformatter.impl.tasks;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.editor.indent.spi.*;

/**
 * Reformat task factory which adds the SQL formatting functionality into NetBeans
 *
 * @author p.neub, 01.12.2020
 */
@SuppressWarnings("unused") // Netbeans
@MimeRegistration(mimeType = "text/x-sql", service = ReformatTask.Factory.class)
public class SQLReformatTaskFactory implements ReformatTask.Factory
{
  /**
   * Called by NetBeans
   *
   * @param pContext The reformat context provided by NetBeans
   * @return A new custom SQL reformat task
   */
  @Override
  public ReformatTask createTask(Context pContext)
  {
    return new SQLReformatTask(pContext);
  }
}
