package GUI;

import Logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by yichenzhou on 2/4/17.
 */
public class GUIPanel extends JFrame {
    private JPanel panelView;

    private GUIRegister PC;
    private GUIRegister IR;
    private GUIRegister MAR;
    private GUIRegister MBR;
    private GUIRegister IAR;
    private GUIRegister R0;
    private GUIRegister R1;
    private GUIRegister R2;
    private GUIRegister R3;
    private GUIRegister X0;
    private GUIRegister X1;
    private GUIRegister X2;

    private JList console;
    private DefaultListModel consoleMode;

    private JTextField inputConsole;

    private JButton loadButton;
    private JButton singleButton;
    private JButton runButton;

    private CPU cpu;


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
        // Input Console Initializer
        InitInputConsole("Input Console: Can not be used right now.", false);
        // Buttons Initializer
        InitButton();
        // CPU Initializer
        InitCPU();
    }

    /*
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

    /*
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

    /*
     * Initialize console on the panel view
     */
    private void InitConsole() {
        consoleMode = new DefaultListModel();
        console = new JList(consoleMode);
        console.setFont(new Font("Menlo", 0, 12));
        console.setForeground(Color.white);
        console.setBackground(Color.darkGray);
        console.setOpaque(true);
        console.setBounds(150, 520, 1110, 160);
        console.setLayoutOrientation(0);

        JScrollPane test = new JScrollPane(console);
        test.setBounds(150, 520, 1110, 160);
        test.getVerticalScrollBar().addAdjustmentListener(
                e -> e.getAdjustable().setValue(e.getAdjustable().getValue()));
        panelView.add(test);
    }

    /*
     * Initialize input console on panel view
     */
    private void InitInputConsole(String hint, boolean editable) {
        if (inputConsole != null) {
            panelView.remove(inputConsole);
        }
        inputConsole = new JTextField(hint);
        inputConsole.setEditable(editable);
        inputConsole.setFont(new Font("Menlo", 0, 12));
        inputConsole.setCaretColor(Color.white);
        inputConsole.setForeground(Color.white);
        inputConsole.setBackground(Color.darkGray);
        inputConsole.setOpaque(true);
        inputConsole.setBounds(150, 685, 1110, 40);

        /*
         * MouseListener to clear the hint
         */
        inputConsole.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (inputConsole.isEditable()) {
                    inputConsole.setText("");
                }
            }
        });
        /*
         * ActionListener for data input
         */
        inputConsole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cpu.getInput() == null) {
                    String checkedData = "";
                    cpu.setInput(dataSeparator(inputConsole.getText()));
                    OPCode.IN.execute(cpu, null);
                    for (String str: dataSeparator(inputConsole.getText())) {
                        String numInBin = (CPU.toBitsBinary(Integer.parseInt(str), 16));
                        R0.setValue(numInBin);
                        str += " ";
                        checkedData += str ;
                    }
                    setMessage("Input Number -> " + checkedData);
                    inputConsole.setText("");
                    setMessage("Please type the number to search or 'exit' to cancel.");
                } else {
                    String searchUnit = inputConsole.getText();
                    if (!isNumeric(searchUnit) && !searchUnit.equals("exit")) {
                        setMessage(searchUnit + " is not numeric to search");
                        inputConsole.setText("");
                    } else if (isNumeric(searchUnit)) {
                        setMessage("Searching for the closest number to " + searchUnit);
                        searchClosestOne(searchUnit);
                        cpu.setInput(null);
                        cpu.resetCache();
                        InitInputConsole("Input Console: Can not be used right now.", false);
                        R0.resetValue();
                    } else {
                        cpu.setInput(null);
                        cpu.resetCache();
                        setMessage("Program 1 done and Input data cleared.");
                        InitInputConsole("Input Console: Can not be used right now.", false);
                        R0.resetValue();

                    }
                }

            }
        });
        panelView.add(inputConsole);
    }

    /*
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
                if (cpu.getNumberOfIn() > 0) {
                    cpu.fetchInstruction();
                    setValue();
                    setMessage(cpu.getStatus());
                } else {
                    setMessage("No instruction in the memory.");
                }
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
                if (cpu.getNumberOfIn() > 0) {
                    for (int i = 0; i < cpu.getNumberOfIn(); i++) {
                        cpu.fetchInstruction();
                        setValue();
                        setMessage(cpu.getStatus());
                    }
                } else {
                    setMessage("No instruction in the memory.");
                }
            }
        });
        panelView.add(runButton);
    }

    /*
     * Initialize CPU of GUI Panel
     */
    private void InitCPU() {
        cpu = new CPU();
        setMessage("CPU Initialized.");
    }

    /*
     * Pop up menu for input menu item
     */
    private void loadButtonAction(JButton button) {
        JPopupMenu loadMenu = new JPopupMenu();

        JMenuItem fileItem = new JMenuItem("File");
        JMenuItem program1Item = new JMenuItem("Program 1");

        fileItem.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            File srcDir = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(srcDir);
            int value = fileChooser.showOpenDialog(null);
            if (value == JFileChooser.APPROVE_OPTION) {
                String msg1 = "Chosen file in " + fileChooser.getSelectedFile().getAbsolutePath();
                setMessage(msg1);
                cpu.resetMemory();
                resetValue();
                setMessage("Memory reset.");
                readFile(fileChooser.getSelectedFile());
            }
        });

        program1Item.addActionListener((ActionEvent e) -> {
            setMessage("Please input 20 numbers in Input Console which are separated by space.");
            InitInputConsole("Input numbers here.", true);
        });

        loadMenu.add(fileItem);
        loadMenu.add(program1Item);
        loadMenu.show(button, 100, 0);
    }

    /*
     * Read the file in the location selected by user
     */
    private String[] readFile(File file) {
        String[] inArr = new String[0];

        try {
            FileReader reader = new FileReader(file);
            FileReader counter = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);
            int numOfLine = numOfLine(counter);
            inArr = new String[numOfLine];

            String line;
            int index = 0;

            while ((line = in.readLine()) != null) {
                String msg = "Loading Instruction <" + line + "> in the memory " + (index + 6);
                setMessage(msg);
                inArr[index] = line;
                index++;
            }

            cpu.loadMemory(inArr);
            String msg = "Loaded " + inArr.length + " instructions in the memory.\n";
            setMessage(msg);
        } catch (IOException ex) {
            String msg = ex.getMessage();
            setMessage(msg);
        }
        return inArr;
    }

    /*
     * Return the number of line of file
     */
    private int numOfLine(FileReader reader) {
        BufferedReader in = new BufferedReader(reader);
        int numOfLine = 0;
        try {
            while (in.readLine() != null) {
                numOfLine++;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return  numOfLine;
    }

    /*
     * Set message in the console
     */
    private void setMessage(String msg) {
        msg = "CSCI SimulatorX Console: " + msg;
        consoleMode.addElement(msg);
        console.ensureIndexIsVisible(console.getModel().getSize() - 1);
    }

    /*
     * Set new value for GUIRegister
     */
    private void setValue() {
        PC.setValue(cpu.getPC());
        IR.setValue(cpu.getIR());
        MAR.setValue(cpu.getMAR());
        MBR.setValue(cpu.getMBR());
    }

    /*
     * Reset value of GUIRegister as 0
     */
    private void resetValue() {
        PC.resetValue();
        IR.resetValue();
        MAR.resetValue();
        MBR.resetValue();
        IAR.resetValue();
        R0.resetValue();
        R1.resetValue();
        R2.resetValue();
        R3.resetValue();
        X0.resetValue();
        X1.resetValue();
        X2.resetValue();
    }

    /*
     * Input data separator
     */
    private String[] dataSeparator(String data) {
        String[] dataArr = data.split(" ");
        ArrayList<String> checkedList = new ArrayList<>();
        for (String s: dataArr) {
            if (isNumeric(s)) {
                checkedList.add(s);
            } else {
                setMessage("Non-Numeric input "  + s +" have been removed.");
            }
        }

        return checkedList.toArray(new String[checkedList.size()]);
    }

    /*
     *  Numeric check
     */
    private boolean isNumeric(String unit) {
        try {
            Integer.parseInt(unit);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return  true;
    }

    /*
     * Search the nearest number for Program 1
     */
    private void searchClosestOne(String searchUnit) {
        ArrayList<Integer> searchList = new ArrayList<>();
        for (CacheLine cacheLine: cpu.getCache().getQueue()) {
            searchList.add(Integer.parseInt(cacheLine.getData()));
        }

        int searchNumber = Integer.parseInt(searchUnit);
        Integer[] searchArr = searchList.toArray(new Integer[searchList.size()]);
        int shortestPath = Math.abs(searchNumber - searchArr[0]);
        int index = 0;


        for (int i = 1; i < searchArr.length; i++) {
            int result = Math.abs(searchNumber - searchArr[i]);
            if (result < shortestPath) {
                shortestPath = result;
                index = i;
            }
        }

        setMessage("The closest number to " + searchUnit + " is " + searchArr[index].toString());
    }
}