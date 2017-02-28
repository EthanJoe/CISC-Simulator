package Logic;

/**
 * Created by yichenzhou on 2/8/17.
 */
public class CPU {
    private String PC;               // 12 bit String in binary
    private String IR;               // 16 bit String in binary
    private String MAR;              // 16 bit String in binary
    private String MBR;              // 16 bit String in binary
    private String MSR;              // 16 bit String in binary
    private String MFR;              // 16 bit String in binary

    private Boolean[] CC;            // Array contains 4 boolean value for Condition Code
    private String[] IX;             // Array contains "16 bit String in binary" unit
    private String[] GPR;            // Array contains "16 bit String in binary" unit


    private String[] Memory;
    private int numberOfIn;          // The dynamic number of the rest instructions in memory

    public CPU() {
        this.PC = toBitsBinary(6, 12);
        this.IR = toBitsBinary(0, 16);
        this.MAR = toBitsBinary(0, 16);
        this.MAR = toBitsBinary(0, 16);
        this.MBR = toBitsBinary(0, 16);
        this.MBR = toBitsBinary(0, 4);

        this.CC = new Boolean[]{false, false, false, false};

        this.IX = new String[3];
        for (int i = 0; i < 3; i++) {
            this.IX[i] = toBitsBinary(0, 16);
        }

        this.GPR = new String[4];
        for (int i = 0; i < 4; i++) {
            this.GPR[i] = toBitsBinary(0, 16);
        }

        this.Memory = new String[2048];
        for (int i = 0; i < 2048; i++) {
            this.Memory[i] = "0000000000000000";
        }

        this.numberOfIn = 0;
    }

    /*
     * Load instances of Instruction in the inArr to Memory
     */
    public void loadMemory(String[] inArr) {
        assert inArr.length != 0;
        assert inArr.length < 2042;
        for (int i = 0; i < inArr.length; i++) {
            Memory[i + 6] = inArr[0];
        }
        numberOfIn = inArr.length;
    }

    /*
     * Reset each unit in the memory to 0 as 16 bit
     */
    public void resetMemory() {
        for (int i = 0; i < 2048; i++) {
            this.Memory[i] = "0000000000000000";
        }
        numberOfIn = 0;
    }

    /*
     * Fetch instruction from memory
     */
    public void fetchInstruction() {
        MAR = toBitsBinary(Integer.parseInt(PC, 2), 16);
        MBR = Memory[Integer.parseInt(MAR, 2)];
        IR = MBR;
        PC = toBitsBinary((Integer.parseInt(PC, 2) + 1), 12);
        numberOfIn--;
    }

    /*
     * Get Effective Address Value in Binary
     */
    public String getEA(Instruction ins) {
        int i = toDecimalNumber(ins.getI());
        int ix = toDecimalNumber(ins.getIx());
        int addressDec = toDecimalNumber(ins.getAddress());
        if (i == 0) {
            if (ix == 0) {
                return ins.getAddress();
            } else {
                return toBitsBinary(addressDec + toDecimalNumber(IX[ix]), 7);
            }
        } else {
            if (ix == 0) {
                return Memory[addressDec];
            } else {
                return Memory[(addressDec + toDecimalNumber(IX[ix]))];
            }
        }
    }

    /***
     *  Get Effective Address Value in Decimal
     */
    public int getEAValue(Instruction ins) {
        return toDecimalNumber(this.getEA(ins));
    }

    /*
     * Getter/Setter for PC
     */
    public String getPC() {
        return PC;
    }

    public int getPCValue() { return toDecimalNumber(PC);}

    public void setPC(String pc) {
        PC = pc;
    }

    /*
     * Getter/Setter for IR
     */
    public String getIR() {
        return IR;
    }

    public int getIRValue() { return toDecimalNumber(IR);}

    public void SetIR(String ir) {
        IR = ir;
    }

    /*
     * Getter/Setter for IX
     */
    public String getIX(int index) {
        return IX[index];
    }

    public int getIXValue(int index) {return toDecimalNumber(IX[index]);}

    public void setIX(String value, int index) {
        IX[index] = value;
    }


    /*
     * Getter/Setter for MAR
     */
    public String getMAR() {
        return MAR;
    }

    public int getMARValue() { return toDecimalNumber(MAR); }

    public void setMAR(String mar) {
        MAR = mar;
    }


    /*
     * Getter/Setter for MBR
     */
    public String getMBR() {
        return MBR;
    }

    public int getMBRValue() { return toDecimalNumber(MBR); }

    public void setMBR(String mbr) {
        MBR = mbr;
    }


    /*
     * Getter/Setter for CC
     */
    public Boolean getCC(int index) {
        return CC[index];
    }

    public void setCC(Boolean value, int index) {
        CC[index] = value;
    }


    /*
     * Getter/Setter for Memory
     */
    public String getMemory(int index) {
        return Memory[index];
    }

    public int getMemoryValue(int index) { return toDecimalNumber(Memory[index]); }

    public void setMemory(String value, int index) {
        Memory[index] = value;
    }


    /*
     * Getter/Setter for for GPR
     */
    public String getGPR(int index) {
        return GPR[index];
    }

    public int getGPRValue(int index) { return toDecimalNumber(GPR[index]); }

    public void setGPR(String value, int index) {
        GPR[index] = value;
    }


    public int getNumberOfIn() {
        return numberOfIn;
    }
    
    public String getStatus() {
        String msg = "PC <" + PC + ">, IR <" + IR + ">, MAR: <" + MAR + ">, MBR: <" + MBR + ">";
        return msg;
    }

    /*
     *  num: Decimal number
     *  bits: The bits of binary to translate
     *  return Binary number of 'num' based on bits
     */
    public static String toBitsBinary(int num, int bits) {
        String strOfBin = Integer.toBinaryString(num);
        if (strOfBin.length() < bits) {
            String extraZero = "";
            for (int i = 0; i < bits - strOfBin.length(); i++) {
                extraZero += "0";
            }
            strOfBin = extraZero + strOfBin;
        }
        return strOfBin;
    }

    /*
     * value: Binary value in string
     * return Decimal value in int
     */
    public static int toDecimalNumber(String value) {
        return Integer.parseInt(value, 2);
    }

}
