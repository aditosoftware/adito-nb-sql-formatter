import de.adito.nbm.sqlformatter.Settings;
import de.adito.nbm.sqlformatter.gui.options.SqlFormatterOptionsPanel;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class GUI
{
	@Test
	public void runOptionsPanel() throws InterruptedException
	{
		SqlFormatterOptionsPanel optionsPanel = new SqlFormatterOptionsPanel();

		JFrame frame = new JFrame("OptionsPanel");
		frame.setPreferredSize(new Dimension(700, 500));
		frame.setContentPane(optionsPanel);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		optionsPanel.setSettings(new Settings());

		/* HACK */
		while (frame.isVisible())
		{
			Thread.sleep(1000);
		}
	}
}
