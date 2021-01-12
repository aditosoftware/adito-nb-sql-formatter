import de.adito.nbm.sqlformatter.impl.settings.Settings;
import de.adito.nbm.sqlformatter.impl.gui.options.SQLFormatterOptionsPanel;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class GUI
{
  @Test
  public void runOptionsPanel() throws InterruptedException
  {
    SQLFormatterOptionsPanel optionsPanel = new SQLFormatterOptionsPanel();
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(optionsPanel);

    JFrame frame = new JFrame("OptionsPanel");
    frame.setPreferredSize(new Dimension(700, 500));
    frame.setContentPane(panel);
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
