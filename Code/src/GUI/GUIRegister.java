package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yichenzhou on 2/4/17.
 */
public class GUIRegister extends JComponent {
    private JLabel titleLabel;
    public JLabel[] registers;

    public GUIRegister(String name, int count, int x, int y) {
        // GUIRegister Title Initializer
        this.titleLabel = new JLabel(name);
        this.titleLabel.setBounds(x - 60, y, 45, 35);
        this.titleLabel.setFont(new Font("Avenir", 0, 20));
        this.titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Registers Initializer
        this.registers = new JLabel[count];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new JLabel("0");
            registers[i].setFont(new Font("Avenir", 0, 15));
            registers[i].setHorizontalAlignment(SwingConstants.CENTER);
            registers[i].setVerticalAlignment(SwingConstants.CENTER);
            registers[i].setForeground(Color.black);
            registers[i].setBackground(Color.lightGray);
            registers[i].setOpaque(true);
            registers[i].setBorder(BorderFactory.createLineBorder(Color.gray));
            if (i > 0) {
                int _x = (int)registers[i - 1].getBounds().getX();
                int _y = (int)registers[i - 1].getBounds().getY();
                registers[i].setBounds(_x + 35, _y, 35, 35);
            } else {
                registers[i].setBounds(x, y, 35, 35);
            }
        }
    }

    /***
     * @param view The superview which to be added the target component
     */
    public void addToView(JPanel view) {
        view.add(titleLabel);
        for (int i = 0; i < registers.length; i++) {
            view.add(registers[i]);
        }
    }


}
