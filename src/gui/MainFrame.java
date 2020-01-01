package gui;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private static MainFrame frame = new MainFrame();

    public static MainFrame getInstance() {
        return frame;
    }

    private MainFrame() {

    }
}
