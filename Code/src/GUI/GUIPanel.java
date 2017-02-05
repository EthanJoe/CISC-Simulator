package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by yichenzhou on 2/4/17.
 */
public class GUIPanel extends JFrame {
    public JPanel panelView;

    public GUIRegister PC;
    public GUIRegister IR;
    public GUIRegister MAR;
    public GUIRegister MBR;
    public GUIRegister IAR;
    public GUIRegister R0;
    public GUIRegister R1;
    public GUIRegister R2;
    public GUIRegister R3;
    public GUIRegister X0;
    public GUIRegister X1;
    public GUIRegister X2;

    public JList console;
    public DefaultListModel consoleMode;

    public JButton loadButton;
    public JButton singleButton;
    public JButton runButton;


    public GUIPanel() {
        // Container Initializer
        this.setTitle("CISC SimulatorX");
        this.setBounds(0, 0, 1300, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Panel View Initializer
        this.panelView = new JPanel();
        this.panelView.setBounds(10, 10, 10, 10);
        this.setContentPane(panelView);
        this.panelView.setLayout(null);

        // Title Label Initializer
        JLabel titleLabel = new JLabel("CSCI SimulatorX");
        titleLabel.setFont(new Font("Avenir", 0, 45));
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setBounds(13, 35, 350, 45);
        this.panelView.add(titleLabel);

        // Separators Initializer
        InitSeparator();
        // Registers Initializer
        InitRegister();
        // Console Initializer
        InitConsole();
        // Buttons Initializer
        InitButton();
    }

    /***
     * Initialize all separators on the panel view
     */
    private void InitSeparator() {
        // Separator-1 Initializer
        JSeparator separator1 = new JSeparator();
        separator1.setBounds(0, 100, 1500, 10);
        separator1.setBackground(Color.gray);
        this.panelView.add(separator1);

        // Separator 2 Initializer
        JSeparator separator2 = new JSeparator();
        separator2.setBounds(0, 480, 1500, 10);
        separator2.setBackground(Color.gray);
        panelView.add(separator2);
    }

    /***
     * Initialize all registers on the panel view
     */
    private void InitRegister() {
        // PC Initializer
        this.PC = new GUIRegister("PC", 12, 80, 150);
        this.PC.addToView(this.panelView);

        // IR Initializer
        this.IR = new GUIRegister("IR", 16, 80, 220);
        this.IR.addToView(panelView);

        // MAR Initializer
        this.MAR = new GUIRegister("MAR", 16, 80, 290);
        this.MAR.addToView(panelView);

        // MBR Initializer
        this.MBR = new GUIRegister("MBR", 16, 80, 360);
        this.MBR.addToView(panelView);

        // IAR Initializer
        this.IAR = new GUIRegister("IAR", 16, 80, 430);
        this.IAR.addToView(panelView);

        // R0 Initializer
        this.R0 = new GUIRegister("R0", 16, 700, 150);
        this.R0.addToView(panelView);

        // R1 Initializer
        this.R1 = new GUIRegister("R1", 16, 700, 197);
        this.R1.addToView(panelView);

        // R2 Initializer
        this.R2 = new GUIRegister("R2", 16, 700, 244);
        this.R2.addToView(panelView);

        // R3 Initializer
        this.R3 = new GUIRegister("R3", 16, 700, 291);
        this.R3.addToView(panelView);

        // X0 Initializer
        this.X0 = new GUIRegister("X0", 16, 700, 338);
        this.X0.addToView(panelView);

        // X1 initializer
        this.X1 = new GUIRegister("X1", 16, 700, 385);
        this.X1.addToView(panelView);

        // X2 Initializer
        this.X2 = new GUIRegister("X2", 16, 700, 432);
        this.X2.addToView(panelView);
    }

    /***
     * Initialize console on the panel view
     */
    private void InitConsole() {
        consoleMode = new DefaultListModel();
        console = new JList(consoleMode);
        console.setFont(new Font("Menlo", 0, 12));
        console.setForeground(Color.white);
        console.setBackground(Color.darkGray);
        console.setOpaque(true);
        console.setBounds(150, 520, 1110, 210);
        console.setLayoutOrientation(0);

        JScrollPane test = new JScrollPane(console);
        test.setBounds(150, 520, 1110, 210);
        test.getVerticalScrollBar().addAdjustmentListener(
                e -> e.getAdjustable().setValue(e.getAdjustable().getValue()));
        panelView.add(test);
    }

    /***
     * Initialize all buttons on the panel view
     */
    private void InitButton() {
        // Load button initializer
        loadButton = new JButton("Load");
        loadButton.setFont(new Font("Avenir", 0, 15));
        loadButton.setBounds(30, 520, 100, 50);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "CPU Simulator Console: Load Button Pressed";
                consoleMode.addElement(msg);
                console.ensureIndexIsVisible(console.getModel().getSize() - 1);
                loadButtonAction(loadButton);
            }
        });
        panelView.add(loadButton);

        // Single step button initializer
        singleButton = new JButton("Single Step");
        singleButton.setFont(new Font("Avenir", 0, 15));
        singleButton.setBounds(30, 600, 100, 50);
        singleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "CPU Simulator Console: Single Step Button Pressed";
                consoleMode.addElement(msg);
                console.ensureIndexIsVisible(console.getModel().getSize() - 1);
            }
        });
        panelView.add(singleButton);

        // Run button initializer
        runButton = new JButton("Run");
        runButton.setFont(new Font("Avenir", 0, 15));
        runButton.setBounds(30, 680, 100, 50);
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "CPU Simulator Console: Run Button Pressed";
                consoleMode.addElement(msg);
                console.ensureIndexIsVisible(console.getModel().getSize() - 1);
            }
        });
        panelView.add(runButton);
    }

    /***
     * @param button Pop up menu for input menu item
     */
    private void loadButtonAction(JButton button) {
        JPopupMenu loadMenu = new JPopupMenu();

        JMenuItem inputItem = new JMenuItem("Input");
        JMenuItem fileItem = new JMenuItem("File");

        inputItem.addActionListener((ActionEvent e) -> {
            String msg = "CPU Simulator Console: Input Button Pressed";
            consoleMode.addElement(msg);
            console.ensureIndexIsVisible(console.getModel().getSize() - 1);
            String data = JOptionPane.showInputDialog(panelView, "Input your data", null);
            consoleMode.addElement(data);
            console.ensureIndexIsVisible(console.getModel().getSize() - 1);
        });

        fileItem.addActionListener((ActionEvent e) -> {
            String msg = "CPU Simulator Console: File Button Pressed";
            consoleMode.addElement(msg);
            console.ensureIndexIsVisible(console.getModel().getSize() - 1);

            JFileChooser fileChooser = new JFileChooser();
            //FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");
            //fileChooser.setFileFilter(filter);

            File srcDir = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(srcDir);
            int value = fileChooser.showOpenDialog(null);
            if (value == JFileChooser.APPROVE_OPTION) {
                String msg1 = "You choose to open the file in " + fileChooser.getSelectedFile().getAbsolutePath();
                consoleMode.addElement(msg1);
                console.ensureIndexIsVisible(console.getModel().getSize() - 1);
                readFile(fileChooser.getSelectedFile());
            }
        });

        loadMenu.add(inputItem);
        loadMenu.add(fileItem);
        loadMenu.show(button, 100, 0);
    }

    /***
     * @param file Read the file in the location selected by user
     */
    private void readFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);

            while (in.readLine() != null) {
                String fileMsg = in.readLine();
                consoleMode.addElement(fileMsg);
                console.ensureIndexIsVisible(console.getModel().getSize() - 1);
            }
        } catch (IOException ex) {
            String exMsg = ex.getMessage();
            consoleMode.addElement(exMsg);
            console.ensureIndexIsVisible(console.getModel().getSize() - 1);
        }
    }
}
