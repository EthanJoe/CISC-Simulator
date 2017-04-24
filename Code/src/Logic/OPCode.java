package Logic;

import java.util.ArrayList;

/**
 * Created by yichenzhou on 2/20/17.
 */

public enum OPCode {
    HLT(0, "Halt Machine"),
    LDR(1, "Load Register From Memory"),
    STR(2, "Store Register To Memory"),
    LDA(3, "Load Accumulator With Memory"),
    AMR(4, "Add Memory To Register"),
    SMR(5, "Subtract Memory To Register"),
    AIR(6, "Add Immediate To Register"),
    SIR(7, "Subtract Immediate From Register"),
    JZ(10, "Jump If Zero"),
    JNE(11, "Jump If Not Equal"),
    JCC(12, "Jump If Condition Code Specified"),
    JMA(13, "Unconditional Jump"),
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
        if (ins == null) {
            ins = new Instruction("0000000000000000");
        }

        final int EAValue = cpu.getEAValue(ins);
        final String EA = CPU.toBitsBinary(EAValue, 16);

        final int pcValue = cpu.getPCValue();
        final int Rx = CPU.toDecimalNumber(ins.getIx());
        final int Ry = CPU.toDecimalNumber(ins.getR());
        final int r1 = cpu.getGPRValue(Rx);
        final int r2 = cpu.getGPRValue(Ry);
        final int ri = CPU.toDecimalNumber(ins.getIx());
        final int count = CPU.toDecimalNumber(ins.toString().substring(12, 16));
        final int lr = CPU.toDecimalNumber(ins.toString().substring(9, 10));
        final int al = CPU.toDecimalNumber(ins.toString().substring(8, 9));
        final int rx = cpu.getGPRValue(ri);
        final int i = CPU.toDecimalNumber(ins.toString().substring(10, 11));
        final int devid = CPU.toDecimalNumber(ins.toString().substring(11, 16));

        FloatingRepresentation FP, result;
        Float newFloatValue;

        int value;
        switch (id) {
            /*
             * OPCode 00 HALT
             */
            case 0:
                throw new NullPointerException("HLT Stop Machine");
            /*
             * OPCode 01 LDR
             * r <- c(EA)
             * r <- c(c(EA)), if I bit set
             */
            case 1:
                cpu.setMAR(EA);
                cpu.setMBR(cpu.getMemory(EAValue));

                cpu.setGPR(cpu.getMemory(EAValue), ins.getRValue());
                break;
            /*
             * OPCode 02 STR
             * c(EA) <- c(r)
             */
            case 2:
                cpu.setMAR(EA);
                cpu.setMBR(cpu.getGPR(ins.getRValue()));

                cpu.setMemory(cpu.getGPR(ins.getRValue()), EAValue);
                break;
            /*
             * OPCode 03 LDA
             * r <- EA
             */
            case 3:
                cpu.setMAR(EA);
                cpu.setMBR(cpu.getGPR(ins.getRValue()));
                cpu.setGPR(cpu.getMemory(EAValue), ins.getRValue());
                break;
            /*
             * OPCode 04 AMR
             * r <- c(r) + c(EA)
             */
            case 4:
                cpu.setMAR(EA);
                cpu.setMBR(cpu.getMAR());
                value = cpu.getMemoryValue(EAValue) + cpu.getGPRValue(ins.getRValue());
                if (Integer.toBinaryString(value).length() > 16) {
                    cpu.setCC(true, 0);
                    System.out.println("Overflow");
                    break;
                }
                cpu.setGPR(CPU.toBitsBinary(value, 16), ins.getRValue());
                break;
            /*
             * OPCode 05 SMR
             * r <- c(r) - c(EA)
             */
            case 5:
                cpu.setMAR(EA);
                cpu.setMBR(cpu.getMemory(EAValue));
                value = cpu.getGPRValue(ins.getRValue()) - cpu.getMemoryValue(EAValue);
                if (value < 0) {
                    cpu.setCC(true, 1);
                    System.out.println("Underflow");
                    break;
                }
                cpu.setGPR(CPU.toBitsBinary(value, 16), ins.getRValue());
                break;
            /*
             * OPCode 06 AIR
             */
            case 6:
                cpu.setMBR(ins.getAddress());

                value = ins.getAddressValue();
                if (value != 0) {
                    if (cpu.getGPRValue(ins.getRValue()) == 0) {
                        cpu.setGPR(CPU.toBitsBinary(value, 16), ins.getRValue());
                    } else {
                        value += cpu.getGPRValue(ins.getRValue());
                        cpu.setGPR(CPU.toBitsBinary(value, 16), ins.getRValue());
                    }
                }
                break;
            /*
             * OPCode 07 SIR
             */
            case 7:
                cpu.setMBR(ins.getAddress());
                value = ins.getAddressValue();
                if (value != 0) {
                    if (cpu.getGPRValue(ins.getRValue()) == 0) {
                        cpu.setGPR(CPU.toBitsBinary(value, 16), ins.getRValue());
                    } else {
                        value = cpu.getGPRValue(ins.getRValue()) - value;
                        if (value < 0) {
                            cpu.setCC(true, 1);
                            System.out.println("Underflow");
                            break;
                        }
                        cpu.setGPR(CPU.toBitsBinary(value, 16), ins.getRValue());
                    }
                }
                break;
            /*
             * OPCode 10 JZ
             */
            case 10 :
               if(cpu.getGPRValue(ins.getRValue()) == 0) {
                   cpu.setPC(CPU.toBitsBinary(EAValue, 12));
               } else {
                   cpu.setPC(CPU.toBitsBinary(cpu.getPCValue() + 1, 12));
               }
               break;
            /*
             * OPCode 11 JNE
             */
            case 11 :
                if (cpu.getGPRValue(ins.getRValue()) != 0) {
                    cpu.setPC(CPU.toBitsBinary(EAValue, 12));
                } else {
                    cpu.setPC(CPU.toBitsBinary(cpu.getPCValue() + 1, 12));
                }
                break;
            /*
             * OPCode 12 JCC
             */
            case 12 :
                if (cpu.getCC(ins.getRValue())) {
                    System.out.println("Jump to Memory Slot " + EAValue);
                    cpu.setPC(CPU.toBitsBinary(EAValue, 12));
                } else {
                    System.out.println("Don jump, just to next PC " + (pcValue + 1));
                    cpu.setPC(CPU.toBitsBinary((pcValue + 1), 12));
                }
                break;
            /*
             * OPCode 13 JMA
             */
            case 13 :
                cpu.setPC(CPU.toBitsBinary(EAValue, 12));
                System.out.println("Jump to memory slot " + EAValue);
                break;
            /*
             * OPCode 14 JSR
             */
            case 14 :
                cpu.setGPR(CPU.toBitsBinary(cpu.getPCValue() + 1, 16), 3);
                cpu.setPC(CPU.toBitsBinary(EAValue, 12));
                break;
            /*
             * OPCode 15 RFS
             */
            case 15 :
                cpu.setGPR(ins.getAddress(), 0);
                cpu.setPC(cpu.getGPR(3));
                break;
            /*
             * OPCode 16 SOB
             */
            case 16 :
                int num = ins.getRValue();
                cpu.setGPR(CPU.toBitsBinary(num - 1, 16), num);
                if (cpu.getGPRValue(num) > 0) {
                    cpu.setPC(CPU.toBitsBinary(EAValue, 12));
                } else {
                    cpu.setPC(CPU.toBitsBinary(cpu.getPCValue() + 1, 12));
                }
                break;
            /*
             * OPCode 17 JGE
             */
            case 17 :
                if (cpu.getGPRValue(ins.getRValue()) > 0) {
                    cpu.setPC(CPU.toBitsBinary(EAValue, 12));
                } else {
                    cpu.setPC(CPU.toBitsBinary(cpu.getPCValue() + 1, 12));
                }
                break;
             /*
              * OPCode 20 MLT
              */
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
            /*
             * OPCode 21 DVD
             */
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
            /*
             * OPCode 22 TRR
             */
            case 22:
                if (cpu.getGPRValue(ins.getRValue()) == cpu.getGPRValue(ins.getIxValue())) {
                    cpu.setCC(true, 3);
                    System.out.println("Equal, then jump.");
                } else {
                    cpu.setCC(false, 3);
                    System.out.println("Not equal, don't jump");
                }
                break;
            /*
             * OPCode 23 AND
             */
            case 23:
                value = r1&r2;
                cpu.setGPR(CPU.toBitsBinary(value, 16), Rx);
                break;
            /*
             * OPCode 24 ORR
             */
            case 24:
                value = r1|r2;
                cpu.setGPR(CPU.toBitsBinary(value, 16), Rx);
                break;
            /*
             * OPCode 25 NOT
             */
            case 25:
                value = ~r1;
                cpu.setGPR(CPU.toBitsBinary(value, 16), Rx);
                break;
            /*
             * OPCode 30 TRAP
             */
            case 30:
                //Traps to memory address 0
                cpu.setMemory(String.valueOf(10000), 0);

                //Stores the PC+1 in memory address 2
                int pcVal = cpu.getPCValue();
                cpu.setMemory(String.valueOf(pcVal + 1),2);

                int memVal = cpu.getMemoryValue(0);
                int insVal = cpu.getEAValue(ins);
                cpu.setPC(String.valueOf(memVal + insVal));

                break;
            /*
             * OPCode 31 SRC
             */
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
            /*
             * OPCode 32 RRC
             */
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
            /*
             * OPCode 33 FADD
             */
            case 33:
                FP = new FloatingRepresentation(cpu.getGPR(ins.getRValue()));
                newFloatValue = EAValue + FP.calDecimalNum();
                result = new FloatingRepresentation(newFloatValue);

                if (result.getExponent() == null) {
                    cpu.setCC(true, 0);
                    throw new NullPointerException("Overflow");
                }

                if (ins.getRValue() == 0) {
                    cpu.setFR0(result.toString());
                } else if (ins.getRValue() == 1) {
                    cpu.setFR1(result.toString());
                }

                break;
            /*
             * OPCode 34 FSUB
             */
            case 34:
                FP = new FloatingRepresentation(cpu.getGPR(ins.getRValue()));
                newFloatValue = FP.calDecimalNum() - EAValue;
                result = new FloatingRepresentation(newFloatValue);

                Float limit = (new Float(Math.pow(3.682143, 19))) * -1;

                if (newFloatValue.compareTo(limit) < 0) {
                    cpu.setCC(true, 1);
                    throw  new NullPointerException("Underflow");
                }

                if (ins.getRValue() == 0) {
                    cpu.setFR0(result.toString());
                } else if (ins.getRValue() == 1) {
                    cpu.setFR1(result.toString());
                }

                break;


            /*
             * OPCode 41 LDX
             * Xx <- c(EA)
             */
            case 41:
                cpu.setIX(cpu.getMemory(EAValue), ins.getIxValue());
                break;
            /*
             * OPCode 42 STX
             * EA <- C(Xx)
             */
            case 42:
                cpu.setMemory(cpu.getIX(ins.getIxValue()), EAValue);
                break;
            /*
             * OPCode 61 IN
             */
            case 61:
                if (cpu.getInput() != null) {
                    CacheLine cacheLine;
                    for (int k = 0; k < cpu.getInput().length; k++) {
                        String address = CPU.toBitsBinary(k, 16);
                        String data = cpu.getInput()[k];
                        cacheLine = new CacheLine(address, data);
                        cpu.getCache().insert(cacheLine);
                    }
                }
                break;
             /*
              * OPCode 62 OUT
              */
            case 62:
                if (cpu.getCache().size() > 0) {
                    ArrayList<String> dataStr = new ArrayList<>();
                    for (CacheLine cacheLine: cpu.getCache().getQueue()) {
                        dataStr.add(cacheLine.getData());
                    }
                    cpu.setInput(dataStr.toArray(new String[dataStr.size()]));
                    cpu.resetCache();
                }
                break;
            default:
                throw new NullPointerException("Instruction " + id.toString() + " Does Not Exist.");
        }
    }
}

