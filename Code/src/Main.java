import GUI.GUIPanel;
import Logic.Instruction;
import Logic.OPCode;

import java.awt.*;

/**
 * Created by yichenzhou on 2/4/17.
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUIPanel panel = new GUIPanel();
            panel.setVisible(true);
        });
    }
}
