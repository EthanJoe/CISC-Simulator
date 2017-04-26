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
import java.util.Scanner;

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

    private GUIRegister MSR;
    private GUIRegister MFR;
    private GUIRegister FR0;
    private GUIRegister FR1;

    private JList console;
    private DefaultListModel consoleMode;

    private JTextField inputConsole;

    private JButton loadButton;
    private JButton singleButton;
    private JButton runButton;

    private CPU cpu;

    private boolean OPCodeTag;
    private boolean Program2Tag;
    private boolean Program4Tag;


    public GUIPanel() {
        // Container Initializer
        this.setTitle("CISC SimulatorX");
        this.setBounds(0, 0, 1300, 880);
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

        this.OPCodeTag = false;
        this.Program2Tag = false;
        this.Program4Tag = false;
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
        separator2.setBounds(0, 580, 1500, 10);
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
        this.IR = new GUIRegister("IR", 16, 80, 214);
        this.IR.addToView(panelView);

        // MAR Initializer
        this.MAR = new GUIRegister("MAR", 16, 80, 278);
        this.MAR.addToView(panelView);

        // MBR Initializer
        this.MBR = new GUIRegister("MBR", 16, 80, 342);
        this.MBR.addToView(panelView);

        // IAR Initializer
        this.IAR = new GUIRegister("IAR", 16, 80, 406);
        this.IAR.addToView(panelView);

        // MSR Initializer
        this.MSR = new GUIRegister("MSR", 16, 80, 470);
        this.MSR.addToView(panelView);

        // MFR Initializer
        this.MFR = new GUIRegister("MFR", 16, 80, 534);
        this.MFR.addToView(panelView);

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

        // FR0 Initializer
        this.FR0 = new GUIRegister("FR0", 16, 700, 479);
        this.FR0.addToView(panelView);

        // FR1 Initializer
        this.FR1 = new GUIRegister("FR1", 16, 700, 526);
        this.FR1.addToView(panelView);
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
        console.setBounds(150, 620, 1110, 160);
        console.setLayoutOrientation(0);

        JScrollPane test = new JScrollPane(console);
        test.setBounds(150, 620, 1110, 160);
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
        inputConsole.setBounds(150, 785, 1110, 40);

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
        inputConsole.addActionListener(e -> {
            if (OPCodeTag) {
                if (inputConsole.getText().length() != 16) {
                    setMessage("Invalid Instruction.");
                } else {
                    Instruction ins = new Instruction(inputConsole.getText());
                    String op = ins.getOpCode();
                    for (OPCode code: OPCode.values()) {
                        if (op.equals(code.toString())) {
                            setMessage("OPCode " + ins.getOPCodeValue() + " " + op + "-> " + code.getContent());
                            try {
                                code.execute(cpu, ins);
                            }
                            catch (NullPointerException ex) {
                                setMessage(ex.getMessage());
                            }
                            setValue();
                        }
                    }
                }
                inputConsole.setText("");
                OPCodeTag = false;
                InitInputConsole("Input console can not be used right now.", false);
            } else if (Program2Tag) {
                if (inputConsole.getText().equals("") || inputConsole.getText().equals(" ") || inputConsole.getText().equals(null)) {
                    InitInputConsole("Re-type the word to search:", true);
                    setMessage("Please type invalid to search.");
                } else {
                    String wordStr = inputConsole.getText();
                    String fileStr = cpu.getCache().search("SpecialCache").getData();
                    if (fileStr.equals(null)) {
                        setMessage("Cache Error");
                    } else {
                        cpu.resetCache();
                        loadFileInMemoryAndCache(fileStr);
                        ArrayList<Instruction> list = find(wordStr);
                        for (Instruction instruction: list) {
                            cpu.setMAR(CPU.toBitsBinary(instruction.getAddressValue(), 16));
                            cpu.setPC(CPU.toBitsBinary(instruction.getAddressValue(), 12));
                            cpu.setIX(CPU.toBitsBinary(instruction.getAddressValue(), 16), instruction.getIxValue());
                            cpu.setGPR(CPU.toBitsBinary(instruction.getAddressValue(), 16), instruction.getRValue());
                            setValue();
                        }
                    }
                    Program2Tag = false;
                    InitInputConsole("Input console can not be used right now", false);
                }
            } else if (Program4Tag) {

            } else if (cpu.getInput() == null) {
                String checkedData = "";
                String[] checkedArr = dataSeparator(inputConsole.getText());
                cpu.setInput(checkedArr);
                OPCode.IN.execute(cpu, null);
                for (String str: checkedArr) {
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
        loadButton.setBounds(30, 620, 100, 50);
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
        singleButton.setBounds(30, 700, 100, 50);
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
        runButton.setBounds(30, 780, 100, 50);
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
        JMenuItem program2Item = new JMenuItem("Program 2");
        JMenuItem project4Item = new JMenuItem("Project 4 Demo");
        JMenuItem opcodeItem = new JMenuItem("Individual OPCode");

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
                cpu.resetCache();
                setMessage("Memory reset.");
                readInstructionFile(fileChooser.getSelectedFile());
            }
        });

        program1Item.addActionListener((ActionEvent e) -> {
            cpu.setInput(null);
            setMessage("Please input 20 numbers in Input Console which are separated by space.");
            resetValue();
            cpu.resetCache();
            InitInputConsole("Input numbers here.", true);
        });

        program2Item.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            File srcDir = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(srcDir);
            int value = fileChooser.showOpenDialog(null);
            if (value != JFileChooser.APPROVE_OPTION) {
                setMessage("File has not been selected.");
            }
            setMessage("Chosen file in " + fileChooser.getSelectedFile().getAbsolutePath());
            cpu.resetMemory();
            resetValue();
            cpu.resetCache();
            Program2Tag = true;
            setMessage("Memory reset.");
            String fileStr = readProgram2File(fileChooser.getSelectedFile());
            // Put fileStr into Cache as singleton
            CacheLine fileCache = new CacheLine("SpecialCache", fileStr);
            cpu.getCache().insert(fileCache);
            splitProgram2File(fileStr);
            InitInputConsole("Type the word to search:", true);
        });

        opcodeItem.addActionListener((ActionEvent e) -> {
            setMessage("Please input 16 bits instruction in Input Console.");
            OPCodeTag = true;
            cpu.resetCache();
            resetValue();
            InitInputConsole("Input instruction here.", true);
        });

        project4Item.addActionListener((ActionEvent e) -> {
            setMessage("For Project 4 Demo, Please Choose Instruction File.");
            Program4Tag = true;
            cpu.resetCache();
            resetValue();
            demoFileForProject4();
            Program4Tag = false;
            cpu.resetCache();
            cpu.resetMemory();
        });


        loadMenu.add(fileItem);
        loadMenu.add(program1Item);
        loadMenu.add(program2Item);
        loadMenu.add(project4Item);
        loadMenu.add(opcodeItem);
        loadMenu.show(button, 100, 0);
    }

    /*
     * Read the file in the location selected by user
     */
    private String[] readInstructionFile(File file) {
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
     * Read file as search base to search the word
     */
    private String readProgram2File(File file) {
        String fileTxt = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileTxt += line;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileTxt;
    }
    /*
     * Show each sentence from Program2 File on the console
     */
    private void splitProgram2File(String txt) {
        String[] strArr = txt.split("\\.");
        for (int i = 0; i < strArr.length - 1; i++) {
            String str = strArr[i].trim();
            setMessage("SENTENCE " + (i + 1) + " -> " + str + ".");
        }
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
        MSR.setValue(cpu.getMSR());
        MFR.setValue(cpu.getMFR());
        FR0.setValue(cpu.getFR0());
        FR1.setValue(cpu.getFR1());
        X0.setValue(cpu.getIX(0));
        X1.setValue(cpu.getIX(1));
        X2.setValue(cpu.getIX(2));
        R0.setValue(cpu.getGPR(0));
        R1.setValue(cpu.getGPR(1));
        R2.setValue(cpu.getGPR(2));
        R3.setValue(cpu.getGPR(3));
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
        MSR.resetValue();
        MFR.resetValue();
        FR0.resetValue();
        FR1.resetValue();
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

    /*
     * Load fileStr from Program2 into memory from 50 and cache
     */
    private void loadFileInMemoryAndCache(String fileStr) {
        int memoryIndex = 50;
        String[] sentenceArr = fileStr.split("\\.");
        for (int i = 0; i < sentenceArr.length; i++) {
            String[] wordArr = sentenceArr[i].split(" ");
            ArrayList<String> wordList = new ArrayList<>();
            for (String word: wordArr) {
                if (!word.equals(" ") && !word.equals("")) {
                    wordList.add(word);
                }
            }
            wordArr = new String[wordList.size()];
            for (int index = 0; index < wordList.size(); index++) {
                wordArr[index] = wordList.get(index);
            }

            for (int j = 0; j < wordArr.length; j++) {
                String word = wordArr[j].trim();
                int beginIndex = memoryIndex;
                for (int k = 0; k < word.length(); k++) {
                    int ascIIChar = (int) word.charAt(k);
                    cpu.setMemory(ascIIChar, memoryIndex++);
                }
                int endIndex = memoryIndex;
                String address = beginIndex + "-" + endIndex;
                String data = word + ";" + i + ";" + j;
                CacheLine cacheLine = new CacheLine(address, data);
                cpu.getCache().insert(cacheLine);
            }
        }
    }

    /*
     * Find the wordStr in Memory and Cache
     */
    private ArrayList<Instruction> find(String wordStr) {
        ArrayList<Instruction> list = new ArrayList<>();
        for (CacheLine cache: cpu.getCache().getQueue()) {
            String[] dataArr = cache.getData().split(";");
            String[] addressArr = cache.getAddress().split("-");
            int startIndex = Integer.parseInt(addressArr[0]);
            int endIndex = Integer.parseInt(addressArr[1]);
            for (int i = startIndex; i <= endIndex; i++) {
                list.add(new Instruction(OPCode.LDR, 1, 0, 0, i));
                list.add(new Instruction(OPCode.LDR, 0, 0, 0, i));
                list.add(new Instruction(OPCode.TRR, 0, 1, 0, i));
                list.add(new Instruction(OPCode.STR, 1, 0, 0, i));
            }
            if (dataArr[0].equals(wordStr)) {
                int sentenceNum = Integer.parseInt(dataArr[1]) + 1;
                int wordNum = Integer.parseInt(dataArr[2]) + 1;
                String msg = "Found " + wordStr + " In Sentence " + sentenceNum + " Word " + wordNum;
                setMessage(msg);
                return list;
            }
        }
        setMessage("Not Found " + wordStr);
        return list;
    }

    /*
     * Pre-process for Project 4 Demo
     */
    private void demoFileForProject4() {
        JFileChooser fileChooser = new JFileChooser();
        File srcDir = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(srcDir);
        int value = fileChooser.showOpenDialog(null);
        if (value != JFileChooser.APPROVE_OPTION) {
            setMessage("File has not been selected.");
        }
        setMessage("Chosen file in " + fileChooser.getSelectedFile().getAbsolutePath());
        String fileName = fileChooser.getSelectedFile().getName();
        fileName = fileName.split("\\.")[0];
        cpu.setMemory(99, 99);
        cpu.setMemory(100, 100); // Save `100` to Memory[100]
        cpu.setGPR(CPU.toBitsBinary(1, 16), 0);
        cpu.setFR0(CPU.toBitsBinary(99, 16));
        setMessage("FR0 Value: 99");
        cpu.setFR1(CPU.toBitsBinary(100, 16));
        setMessage("FR1 Value: 100");
        setValue();
        Instruction ins;
        switch (fileName) {
            case "FADD":
                ins = new Instruction("100001000001100100");
                setMessage("OPCode FADD Operating");
                ins.getOPCode().execute(cpu, ins);
                setValue();
                setMessage("After FADD, FR0 Value: " + cpu.getFR0Value());
                break;
            case "FSUB":
                ins = new Instruction("100010000001100100");
                setMessage("OPCode FSUB Operating");
                ins.getOPCode().execute(cpu, ins);
                setValue();
                setMessage("After FSUB, FR0 Value: " + cpu.getFR0Value());
                break;
            case "VADD":
                ins = new Instruction("100011000001100100");
                setMessage("OPCode VADD Operating");
                try {
                    ins.getOPCode().execute(cpu, ins);
                    setValue();
                }
                catch (NullPointerException e) {
                    setMessage(e.getMessage());
                }
                break;
            case "VSUB":
                ins = new Instruction("100100000001100100");
                setMessage("OPCode VSUB Operating");
                try {
                    ins.getOPCode().execute(cpu, ins);
                    setValue();
                }
                catch (NullPointerException e) {
                    setMessage(e.getMessage());
                }
                break;
            case "CNVRT":
                ins = new Instruction("100101000001100100");
                setMessage("OPCode CNVRT Operating");
                ins.getOPCode().execute(cpu, ins);
                setValue();
                setMessage("After CNVRT, FR0 = " + cpu.getFR0Value());
                break;
            default:
                throw new NullPointerException("Unknown File Error");
        }

    }
}