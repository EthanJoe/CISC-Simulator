package Logic;

/**
 * Created by yichenzhou on 4/14/17.
 */
public class Pipeline {
    CPU cpu;
    int fromIndex;
    int endIndex;
    Instruction[] line;

    public Pipeline(CPU cpu, int fromIndex, int endIndex) {
        this.cpu = cpu;
        assert endIndex > fromIndex;
        this.fromIndex = fromIndex;
        this.endIndex = endIndex;
        this.line = new Instruction[4];
        for (int i = 0; i < 4; i++) {
            line[i] = null;
        }
    }

    /*
     * Begin Pipelining Operation
     */
    public void cycle() {
        int currentCount = fromIndex;
        while (!check() && currentCount <= endIndex + 4) {
            if (currentCount > endIndex) {
                push(null);
            } else {
                push(new Instruction(cpu.getMemory(currentCount)));
            }
            execute();
        }
    }

    /*
     * Push Instruction into Pipeline
     */
    private void push(Instruction instruction) {
        line[3] = line[2];
        line[2] = line[1];
        line[1] = line[0];
        line[0] = instruction;
    }

    /*
     * Execute Instruction
     */
    private void execute() {
        if (line[2] != null) {
            OPCode opCode = line[2].getOPCode();
            opCode.execute(cpu, line[2]);
        }
    }

    /*
     * Check if all units in `line` are null
     */
    private boolean check() {
        for (int i = 0; i < 4; i++) {
            if (line[i] != null) {
                return false;
            }
        }
        return true;
    }
}
