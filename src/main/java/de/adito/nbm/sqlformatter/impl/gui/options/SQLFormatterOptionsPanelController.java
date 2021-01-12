package de.adito.nbm.sqlformatter.impl.gui.options;

import de.adito.nbm.sqlformatter.impl.settings.Settings;
import org.netbeans.api.options.OptionsDisplayer;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

import javax.swing.*;
import java.beans.PropertyChangeListener;

@SuppressWarnings("unused") // NetBeans
@OptionsPanelController.SubRegistration(location = OptionsDisplayer.EDITOR, displayName = "#LBL_OptionsPanelController_TITLE")
public class SQLFormatterOptionsPanelController extends OptionsPanelController
{
  private final SQLFormatterOptionsPanel panel = new SQLFormatterOptionsPanel();

  @Override
  public void update()
  {
    panel.setSettings(Settings.getSettings());
  }

  @Override
  public void applyChanges()
  {
    Settings.setSettings(panel.getSettings());
  }

  @Override
  public void cancel()
  {
    panel.setSettings(Settings.getSettings());
  }

  @Override
  public boolean isValid()
  {
    return true;
  }

  @Override
  public boolean isChanged()
  {
    return !Settings.getSettings().equals(panel.getSettings());
  }

  @Override
  public JComponent getComponent(Lookup pMasterLookup)
  {
    return panel;
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return null;
  }


  @Override
  public void addPropertyChangeListener(PropertyChangeListener l)
  {
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener l)
  {
  }
}
