package Logic;

/**
 * Created by yichenzhou on 2/20/17.
 */

public enum OPCode {
    HLT(0, "Halt Machine"),
    LDR(1, "Load Register From Memory"),
    STR(2, "Store Register To Memory"),
    LDA(3, "Add Memory To Register"),
    AMR(4, "Add Memory To Register"),
    SMR(5, "Subtract Memory To Register"),
    AIR(6, "Add Immediate To Register"),
    SIR(7, "Subtract Immediate From Register"),
    JZ(10, "Jump If Zero"),
    JNE(11, "Jump If Not Equal"),
    JCC(12, "Jump If Condition Code Specified"),
    JMP(12, "Unconditional Jump"),
    JSR(14, "Jump Subroutine"),
    RFS(15, "Return From Subroutine"),
    SOB(16, "Subtract One And Branch"),
    JGE(17, "Jump If Greater Or Equal"),
    MLT(20, "Multiply Register By Register"),
    DVD(21, "Divide Register By Register"),
    TRR(22, "Test If The Contents Of Two Register Equal"),
    AND(23, "Logical AND"),
    ORR(24, "Logical OR"),
    NOT(25, "Logical NOT"),
    TRAP(30, "Traps to Memory Address 0"),
    SRC(31, "Shift Register By Count"),
    RRC(32, "Rotate Register By Count"),
    FADD(33, "Floating Add Memory To Register"),
    FSUB(34, "Floating Subtract From Register"),
    VADD(35, "Vector Add"),
    VSUB(36, "Vector Subtract"),
    CNVRT(37, "Convert To Fixed/Floating Point"),
    LDX(41, "Load Index Register From Memory"),
    STX(42, "Store Index Register To Memory"),
    LDFR(50, "Load Floating Register From Memory"),
    STFR(51, "Store Floating Register To Memory"),
    IN(61, "Input Character To Register"),
    OUT(62, "Output Character From Register"),
    CHK(63, "Check Device Status");


    private Integer id;
    private String content;

    OPCode(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Integer getID() {
        return id;
    }

    public String getBinaryID() {
        return CPU.toBitsBinary(id, 6);
    }

    public String getContent() {
        return content;
    }

    public void execute(CPU cpu, Instruction ins) {
        final int Rx = CPU.toDecimalNumber(ins.getIx());
        final int Ry = CPU.toDecimalNumber(ins.getR());
        final int r1 = cpu.getGPRValue(Rx);
        final int r2 = cpu.getGPRValue(Ry);
        final int ri = CPU.toDecimalNumber(ins.getIx());
        final int count = CPU.toDecimalNumber(ins.toString().substring(12, 16));
        final int lr = CPU.toDecimalNumber(ins.toString().substring(9, 10));
        final int al = CPU.toDecimalNumber(ins.toString().substring(8, 9));
        final int rx = cpu.getGPRValue(ri);
        int value;
        switch (id) {
            case 1:
                String EA = cpu.getEA(ins);
                cpu.setMAR(EA);
                cpu.setMBR(cpu.getMemory(Integer.parseInt(EA, 2)));
                cpu.setGPR(cpu.getMBR(), Integer.parseInt(ins.getR(), 2));
                break;
            case 20:
                // Check value of Rx and Ry equal to 0 or 2
                if (Rx != 0 && Rx != 2) {
                    throw new NullPointerException("Rx should be 0 or 2");
                }

                if (Ry != 0 && Ry != 2) {
                    throw new NullPointerException("Ry should be 0 or 2");
                }

                value = r1 * r2;

                if (value > Math.pow(2, 32) - 1) {
                    cpu.setCC(true, 0);
                    throw new NullPointerException("MLT Operation Overflow");
                }

                // Translate value in high bits<16 bits> and low bits<16 bits> for GPR
                String highBitInBin = CPU.toBitsBinary(value >> 16, 16);
                String lowBitInBin = CPU.toBitsBinary(value << 16, 16);

                if (Rx == 0) {
                    cpu.setGPR(highBitInBin, 0);
                    cpu.setGPR(lowBitInBin, 1);
                } else {
                    cpu.setGPR(highBitInBin, 2);
                    cpu.setGPR(lowBitInBin, 3);
                }
                break;

            case 21:
                if (Rx != 0 && Rx != 2) {
                    throw new NullPointerException("Rx should be 0 or 2");
                }

                if (Ry != 0 && Ry != 2) {
                    throw new NullPointerException("Ry should be 0 or 2");
                }

                if (r2 == 0) {
                    cpu.setCC(true, 0);
                    throw new NullPointerException("MLT DIVZERO");
                }

                String quotient = CPU.toBitsBinary(r1 / r2, 16);
                String remainder = CPU.toBitsBinary(r1 % r2, 16);

                if (Rx == 0) {
                    cpu.setGPR(quotient, 0);
                    cpu.setGPR(remainder, 1);
                } else {
                    cpu.setGPR(quotient, 2);
                    cpu.setGPR(remainder, 3);
                }
                break;

            case 22:
                if (cpu.getGPRValue(Rx) == cpu.getGPRValue(Ry)) {
                    cpu.setCC(true, 3);
                } else {
                    cpu.setCC(false, 3);
                }

            case 23:
                value = r1&r2;
                cpu.setGPR(CPU.toBitsBinary(value, 16), Rx);
                break;

            case 24:
                value = r1|r2;
                cpu.setGPR(CPU.toBitsBinary(value, 16), Rx);
                break;

            case 25:
                value = ~r1;
                cpu.setGPR(CPU.toBitsBinary(value, 16), Rx);
                break;

            case 31:
                String msg = "";
                if (lr == 1 && al == 0) {
                    // Arithmetic left shift
                    value = rx << count;
                    cpu.setGPR(CPU.toBitsBinary(value, 16), ri);
                } else if (lr == 1 && al == 1) {
                    value = rx << count;
                    cpu.setGPR(CPU.toBitsBinary(value, 16), ri);
                } else if (lr == 0 && al == 0) {
                    value = rx >> count;
                    cpu.setGPR(CPU.toBitsBinary(value, 16), ri);
                } else if (lr == 0 && al == 1) {
                    value = rx >>> count;
                    cpu.setGPR(CPU.toBitsBinary(value, 16), ri);
                } else {
                    msg += "SRC: Wrong Instruction/n";
                }
                // Check underflow or not
                if (cpu.getGPRValue(ri) % Math.pow(2, 16) != 0) {
                    cpu.setCC(true, 1);
                    msg += "SRC: Underflow";
                }
                // Check if there exist fault message
                if (!msg.equals("")) {
                    throw new NullPointerException(msg);
                }
                break;

            case 32:
                if (lr == 1 && al == 1) {
                    // Logical left shift
                    value = rx >>> (16 - count) | rx >> count;
                    cpu.setGPR(CPU.toBitsBinary(value, 16), ri);
                } else if (lr == 0 && al == 1) {
                    // Logical right shift
                    value = rx >>> count | rx << (16 - count);
                    cpu.setGPR(CPU.toBitsBinary(value, 16), ri);
                }
                break;

            default:
                System.out.println("Instruction " + id.toString() + " Executed.");
        }

    }
}
