import java.util.ArrayList;
import java.util.List;

public class Memory {
    private List<Frame> mainMemory;
    private int size;
    private List<Integer> access;

    public Memory(int size){
        this.size=size;
        access = new ArrayList<>(size);
        for(int i=0;i<size;i++){
            access.add(i, 0);
        }
        mainMemory = new ArrayList<>();
    }

    public List<Frame> getMainMemory() {
        return mainMemory;
    }

    public void setMainMemory(List<Frame> mainMemory) {
        this.mainMemory = mainMemory;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Integer> getAccess() {
        return access;
    }

    public void setAccess(List<Integer> access) {
        this.access = access;
    }

    public void add(Frame frame){
        mainMemory.add(frame);
    }

    public int leastAccessed(){
        int smaller = access.get(0);
        for(int a:access){
            if(a<smaller)
                smaller=a;
        }
        return smaller;
    }

    public void add(int index, Frame frame){
        mainMemory.set(index, frame);
    }
}
