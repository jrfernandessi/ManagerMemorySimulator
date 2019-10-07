import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Page> secondaryMemory = new ArrayList<>();
    static List<Frame> mainMemory = new ArrayList<>();


    public static void main(String args[]) {
        Scanner in;
        Scanner read = new Scanner(System.in);
        String[] stringdaLinha;
        int col=0;
        int contador=0;
        System.out.println("digite o tamanho da página/frame");
        int size = read.nextInt();
        int sizeMemory = 2*size;
        int pageId = 0;
        try {
            in = new Scanner(new FileReader("file.txt"));
            while (in.hasNextLine()) {
                col++;
                String line = in.nextLine();
                String nome = "";
                String op = "";
                String valor = "";

                stringdaLinha =  line.split(" ");
                for(int i =0 ;i<stringdaLinha.length;i++) {

                    if (!stringdaLinha[i].equals("")) {
                        contador++;
                        if(contador%3==1) {
                            nome = stringdaLinha[i];
                        }
                        else if(contador%3==2){
                            op = stringdaLinha[i];
                        }else{
                            valor = stringdaLinha[i];
                        }
                    }
                }

                //create process
                for(int i =0 ;i<stringdaLinha.length;i+=3) {
                    if (op.equals("C")) {
                        System.out.println("criando processo " + nome);
                        Process process = new Process();
                        process.setName(nome);
                        process.setSize(Integer.parseInt(valor));
                        for(int j=0;j<process.getSize();j+=size){
                            List<Integer> instructionList = new ArrayList<>();
                            int flag = j+size;
                            int k = j;
                            while(k<flag){
                                instructionList.add(k);
                                k++;
                            }
                            Page page = new Page(instructionList, size, pageId, process);
                            secondaryMemory.add(page);
                            pageId++;
                        }
                    }else if ((op.equals("W") || op.equals("R"))){
                        if(mainMemory.size()*size<sizeMemory){
                            int c = 0;
                            if(!verifyProcessInMainMemory(nome, Integer.parseInt(valor))) {
                                Page page = getProcessOfSecondaryMemory(nome, Integer.parseInt(valor));
                                Frame frame = new Frame(page.getInstructionList(), page.getSize(), mainMemory.size(), page.getProcess(), page.getAddress());
                                mainMemory.add(frame);
                                System.out.println("adicionando o processo " + frame.getProcess().getName() +
                                        " na memória principal com a operação de " + op + " endereco " + frame.getProcess().getCount());
                                System.out.println("executando o processo " + frame.getProcess().getName() +
                                        " na memória principal com a operação de " + op + " endereco " + frame.getProcess().getCount());
                                c = frame.getProcess().getCount();
                                frame.getProcess().setCount(c + 1);
                            }else{
                                Frame frame = getProcessOfMainMemory(nome, Integer.parseInt(valor));
                                System.out.println("executando o processo " + frame.getProcess().getName() +
                                        " na memória principal com a operação de " + op + " endereco " + frame.getProcess().getCount());
                                c = frame.getProcess().getCount();
                                frame.getProcess().setCount(c + 1);
                            }


                        }else{
                            Page page = getProcessOfSecondaryMemory(nome, Integer.parseInt(valor));
                            Frame frame = mainMemory.get(0);
                            if(!page.getProcess().getName().equals(frame.getProcess().getName()))
                                FIFO(page, frame);
                            else{
                                System.out.println("executando o processo " + frame.getProcess().getName() +
                                        " na memória principal com a operação de " + op + " endereco " + frame.getProcess().getCount());
                                int c = frame.getProcess().getCount();
                                frame.getProcess().setCount(c + 1);
                            }
                        }
                    }

                }
            }
            System.out.println(secondaryMemory.size());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static Frame getProcessOfMainMemory(String name, int instruction) {
        for(Frame frame: mainMemory){
            if(frame.getProcess().getName().equals(name)){
                for(Integer i: frame.getInstructionList()){
                    if(i==instruction){
                        return frame;
                    }
                }
            }
        }
        return null;
    }

    public static void pageCalculate(Process process, int intruction){
        int numberPage = process.getCount()%intruction;
    }

    public static Page getProcessOfSecondaryMemory(String name, int instruction){

        for(Page page: secondaryMemory){
            if(page.getProcess().getName().equals(name)){
                for(Integer i: page.getInstructionList()){
                    if(i==instruction){
                        return page;
                    }
                }
            }
        }
        return null;
    }

    public static boolean verifyProcessInMainMemory(String name, int instruction){
        for(Frame frame: mainMemory){
            if(frame.getProcess().getName().equals(name)){
                for(Integer i: frame.getInstructionList()){
                    if(i==instruction){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void FIFO(Page page, Frame frame){

            System.out.println("fazendo swap entre o processo " + frame.getProcess().getName() + " e o processo " + page.getProcess().getName());
            Page newPage = new Page(frame.getInstructionList(), frame.getSize(), frame.getAddress(), frame.getProcess());
            frame.setInstructionList(page.getInstructionList());
            frame.setPageAddress(page.getAddress());
            frame.setProcess(page.getProcess());
            frame.setInstructionList(page.getInstructionList());
            page = newPage;
            mainMemory.add(frame);
            secondaryMemory.add(page);

    }


}
