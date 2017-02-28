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
        switch (id) {
            case 1:
                String EA = cpu.getEA(ins);

            default:
                System.out.println("Instruction " + id.toString() + " Executed.");
        }

    }
}
