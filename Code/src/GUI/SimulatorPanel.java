package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yichenzhou on 2/4/17.
 */
public class SimulatorPanel extends JFrame {
    public JPanel panelView;

    public Register PC;
    public Register IR;
    public Register MAR;
    public Register MBR;
    public Register IAR;
    public Register R0;
    public Register R1;
    public Register R2;
    public Register R3;
    public Register X0;
    public Register X1;
    public Register X2;

    public JLabel console;

    public JButton loadButton;
    public JButton singleButton;
    public JButton runButton;


    public SimulatorPanel() {
        // Container Initializer
        this.setTitle("CISC Simulator");
        this.setBounds(0, 0, 1300, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        // Panel View Initializer
        this.panelView = new JPanel();
        this.panelView.setBounds(10, 10, 10, 10);
        this.setContentPane(panelView);
        this.panelView.setLayout(null);
        // Title Label Initializer
        JLabel titleLabel = new JLabel("CSCI Simulator");
        titleLabel.setFont(new Font("Avenir", 0, 45));
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setBounds(13, 35, 350, 45);
        this.panelView.add(titleLabel);

        // Separator-1 Initializer
        JSeparator separator1 = new JSeparator();
        separator1.setBounds(0, 5, 1500, 10);
        separator1.setBackground(Color.gray);
        this.panelView.add(separator1);

        // Separator-2 Initializer
        JSeparator separator2 = new JSeparator();
        separator2.setBounds(0, 100, 1500, 10);
        separator2.setBackground(Color.gray);
        this.panelView.add(separator2);

        // PC Initializer
        this.PC = new Register("PC", 12, 80, 150);
        this.PC.addToView(this.panelView);

        // IR Initializer
        this.IR = new Register("IR", 16, 80, 220);
        this.IR.addToView(panelView);

        // MAR Initializer
        this.MAR = new Register("MAR", 16, 80, 290);
        this.MAR.addToView(panelView);

        // MBR Initializer
        this.MBR = new Register("MBR", 16, 80, 360);
        this.MBR.addToView(panelView);

        // IAR Initializer
        this.IAR = new Register("IAR", 16, 80, 430);
        this.IAR.addToView(panelView);

        // R0 Initializer
        this.R0 = new Register("R0", 16, 700, 150);
        this.R0.addToView(panelView);

        // R1 Initializer
        this.R1 = new Register("R1", 16, 700, 197);
        this.R1.addToView(panelView);

        // R2 Initializer
        this.R2 = new Register("R2", 16, 700, 244);
        this.R2.addToView(panelView);

        // R3 Initializer
        this.R3 = new Register("R3", 16, 700, 291);
        this.R3.addToView(panelView);

        // X0 Initializer
        this.X0 = new Register("X0", 16, 700, 338);
        this.X0.addToView(panelView);

        // X1 initializer
        this.X1 = new Register("X1", 16, 700, 385);
        this.X1.addToView(panelView);

        // X2 Initializer
        this.X2 = new Register("X2", 16, 700, 432);
        this.X2.addToView(panelView);

        // Separator 3 Initializer
        JSeparator separator3 = new JSeparator();
        separator3.setBounds(0, 480, 1500, 10);
        separator3.setBackground(Color.gray);
        panelView.add(separator3);

        // Console Initializer
        this.console = new JLabel("CPU Simulator Console:");
        this.console.setFont(new Font("Menlo", 0, 15));
        this.console.setForeground(Color.white);
        this.console.setBackground(Color.darkGray);
        this.console.setOpaque(true);
        this.console.setHorizontalAlignment(SwingConstants.LEFT);
        this.console.setVerticalAlignment(SwingConstants.TOP);
        this.console.setBounds(150, 520, 1110, 210);
        this.panelView.add(console);

        this.loadButton = new JButton("Load");
        this.loadButton.setFont(new Font("Avenir", 0, 15));
        this.loadButton.setBounds(30, 520, 100, 50);
        this.loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("<html><body>CPU Simulator Console: Load Button Pressed<br></body></html>");
            }
        });
        this.panelView.add(loadButton);


        this.singleButton = new JButton("Single Step");
        this.singleButton.setFont(new Font("Avenir", 0, 15));
        this.singleButton.setBounds(30, 600, 100, 50);
        this.singleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("<html><body>CPU Simulator Console: Single Step Button Pressed<br></body></html>");
            }
        });
        this.panelView.add(singleButton);

        this.runButton = new JButton("Run");
        this.runButton.setFont(new Font("Avenir", 0, 15));
        this.runButton.setBounds(30, 680, 100, 50);
        this.runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("<html><body>CPU Simulator Console: Run Button Pressed<br></body></html>");
            }
        });
        this.panelView.add(runButton);
    }
}
