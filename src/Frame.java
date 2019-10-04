import java.util.ArrayList;
import java.util.List;

public class Frame {
    private List<Integer> instructionList = new ArrayList<>();
    private int size;
    private int address;
    private Process process;
    private int pageAddress;

    public Frame(List<Integer> instructionList, int size, int address, Process process, int pageAddress) {
        this.instructionList = instructionList;
        this.size = size;
        this.address = address;
        this.process = process;
        this.pageAddress = pageAddress;
    }

    public List<Integer> getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(List<Integer> instructionList) {
        this.instructionList = instructionList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public int getPageAddress() {
        return pageAddress;
    }

    public void setPageAddress(int pageAddress) {
        this.pageAddress = pageAddress;
    }
}
