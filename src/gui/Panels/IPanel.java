package gui.Panels;

import javax.swing.*;

public interface IPanel {

    void bindPanelListener(JFrame frame);

    void removePanelListener(JFrame frame);
}
