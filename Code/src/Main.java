import GUI.SimulatorPanel;

import java.awt.*;

/**
 * Created by yichenzhou on 2/4/17.
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SimulatorPanel simulatorPanel = new SimulatorPanel();
            simulatorPanel.setVisible(true);
        });
    }
}
