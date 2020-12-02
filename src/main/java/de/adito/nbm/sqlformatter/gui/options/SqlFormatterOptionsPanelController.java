package de.adito.nbm.sqlformatter.gui.options;

import de.adito.nbm.sqlformatter.Settings;
import org.netbeans.api.options.OptionsDisplayer;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

import javax.swing.*;
import java.beans.PropertyChangeListener;

@SuppressWarnings("unused") // NetBeans
@OptionsPanelController.SubRegistration(location = OptionsDisplayer.EDITOR, displayName = "Sql Formatter")
public class SqlFormatterOptionsPanelController extends OptionsPanelController
{
	private final SqlFormatterOptionsPanel panel = new SqlFormatterOptionsPanel();

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
