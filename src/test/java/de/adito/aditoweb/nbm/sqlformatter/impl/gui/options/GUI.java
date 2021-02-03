package de.adito.aditoweb.nbm.sqlformatter.impl.gui.options;

import de.adito.aditoweb.nbm.sqlformatter.impl.settings.*;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class GUI
{
  public static void main(String[] args)
  {
    SQLFormatterOptionsPanel optionsPanel = new SQLFormatterOptionsPanel();
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(optionsPanel);

    JFrame frame = new JFrame("OptionsPanel");
    frame.setPreferredSize(new Dimension(700, 500));
    frame.setContentPane(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    optionsPanel.setSettings(new Settings());
  }
}
