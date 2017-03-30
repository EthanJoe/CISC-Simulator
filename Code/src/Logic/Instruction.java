package Logic;

public class Instruction {
	
	/*
	 * Input Area - In this area the user can input individual instructions
	 * Opcode	6 bits	Specifies one of 64 possible instructions; Not all may be defined in this project
	 * IX		2 bits	Specifies one of three index registers; may be referred to by X1 ñ X3. O value indicates no indexing.
	 * R		2 bits	Specifies one of four general purpose registers; may be referred to by R0 ñ R3
	 * I		1 bits	If I =1, specifies indirect addressing; otherwise, no indirect addressing.
	 * Address	7 bits	Specifies one of 32 locations
	 */
	
	private String opCode;
	private String ix;
	private String r;
	private String i;
	private String address;

/** Constructor assigning instruction parts */
	public Instruction(String opCode, String r, String ix, String i, String address){
		this.opCode = opCode;
		this.r = r;
		this.ix = ix;
		this.i = i;
		this.address = address;
	}
	
/** Breaking an instruction into substrings according to ISA */
	public Instruction(String instruction){
		this.opCode = instruction.substring(0, 6);
		this.r = instruction.substring(6, 8);
		this.ix = instruction.substring(8, 10);
		this.i = instruction.substring(10, 11);
		this.address = instruction.substring(11);
	}

	public Instruction(OPCode opcode, int r, int ix, int i,int address) {
		this.opCode = CPU.toBitsBinary(opcode.getID(), 6);
		this.r = CPU.toBitsBinary(r, 2);
		this.ix = CPU.toBitsBinary(ix, 2);
		this.i = Integer.toString(i);
		this.address = CPU.toBitsBinary(address, 7);
	}

	public String getOpCode() {
		switch (CPU.toDecimalNumber(opCode)) {
			case 0:
				return "HLT";
			case 1:
				return "LDR";
			case 2:
				return "STR";
			case 3:
				return "LDA";
			case 4:
				return "AMR";
			case 5:
				return "SMR";
			case 6:
				return "AIR";
			case 7:
				return "SIR";
			case 10:
				return "JZ";
			case 11:
				return "JNE";
			case 12:
				return "JCC";
			case 13:
				return "JMA";
			case 14:
				return "JSR";
			case 15:
				return "RFS";
			case 16:
				return "SOB";
			case 17:
				return "JGE";
			case 20:
				return "MLT";
			case 21:
				return "DVD";
			case 22:
				return "TRR";
			case 23:
				return "AND";
			case 24:
				return "ORR";
			case 25:
				return "NOT";
			case 30:
				return "TRAP";
			case 31:
				return "SRC";
			case 32:
				return "RRC";
			case 33:
				return "FADD";
			case 34:
				return "FSUB";
			case 35:
				return "VADD";
			case 36:
				return "VSUB";
			case 37:
				return "CNVRT";
			case 41:
				return "LDX";
			case 42:
				return "STX";
			case 50:
				return "LDFR";
			case 51:
				return "STFR";
			case 61:
				return "IN";
			case 62:
				return "OUT";
			case 63:
				return "CHK";
		}
		return opCode;
	}

	public OPCode getOPCode() {
		for (OPCode opcode: OPCode.values()) {
			if (getOpCode().equals(opcode.toString())) {
				return opcode;
			}
		}
		return null;
	}

	public int getOPCodeValue() {
		return CPU.toDecimalNumber(opCode);
	}


	public String getIx() {
		return ix;
	}
	
	public Integer getIxValue() {
		return Integer.parseInt(ix, 2);
	}


	public String getR() {
		return r;
	}
	
	public Integer getRValue() {
		return Integer.parseInt(r, 2);
	}


	public String getI() {
		return i;
	}


	public String getAddress() {
		return address;
	}
	
	public Integer getAddressValue() {
		return Integer.parseInt(address, 2);
	}

	
	@Override
	public String toString() {
		return opCode + r + ix + i + address;
	}

}
