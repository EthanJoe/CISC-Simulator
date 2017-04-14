package Logic;

/**
 * Created by yichenzhou on 4/14/17.
 */
public class Pipeline {
    CPU cpu;
    Instruction[] line;

    public Pipeline(CPU cpu, int fromIndex, int endIndex) {
        this.cpu = cpu;
        this.line = new Instruction[4];
        for (int i = 0; i < 4; i++) {
            line[i] = null;
        }

        for (int i = fromIndex; i <= endIndex; i++) {
            String address = CPU.toBitsBinary(i, 16);
            cpu.resetCache();
            cpu.getCache().getQueue().enqueue(new CacheLine(address, cpu.getMemory(i)));
        }
    }

    public void cycle() {
        while (!check()) {
            if (cpu.getCache().size() > 0) {
                Instruction instruction = new Instruction(cpu.getCache().getQueue().dequeue().getData());
                push(instruction);
            } else {
                push(null);
            }

            execute();
        }
    }

    private void push(Instruction instruction) {
        line[3] = line[2];
        line[2] = line[1];
        line[1] = line[0];
        line[0] = instruction;
    }

    private void execute() {
        OPCode opCode = line[2].getOPCode();
        opCode.execute(cpu, line[2]);
    }

    private boolean check() {
        for (int i = 0; i < 4; i++) {
            if (line[i] != null) {
                return false;
            }
        }
        return true;
    }
}
