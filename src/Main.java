import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<String> memoriaPrincipal = new ArrayList<>();
    static List<String> memoriaSecundaria = new ArrayList<>();
    static boolean flagPrincipal = false;
    static boolean flagSecundaria = false;
    static List<Page> secondaryMemory = new ArrayList<>();
    static List<Frame> mainMemory = new ArrayList<>();

//    public static void main (String args[]){
//        runProcess("file.txt");
//    }
    public static void main(String args[]) {
        Scanner in;
        Scanner read = new Scanner(System.in);
        String[] stringdaLinha;
        int col=0;
        int contador=0;
        System.out.println("digite o tamanho da página/frame");
        int size = read.nextInt();
        int sizeMemory = 2*size;
        List<Process> processList= new ArrayList<>();
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
//                            System.out.println(stringdaLinha[i]);
                            nome = stringdaLinha[i];
                        }
                        else if(contador%3==2){
                            op = stringdaLinha[i];
                        }else{
                            valor = stringdaLinha[i];
                        }
                    }
                }
                int count = 0;
                int pageId = 0;
                //create process
                for(int i =0 ;i<stringdaLinha.length;i++) {
                    if (op.equals("C") && count==0) {
                        System.out.println("criando processo " + nome);
                        Process process = new Process();
//                        process.setSize(Integer.parseInt(valor));
                        process.setName(nome);
                        process.setSize(Integer.parseInt(valor));
//                        processList.add(process);
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
                        count++;
                    }else if ((op.equals("W") || op.equals("R"))&& count==0){
                        if(mainMemory.size()*size<sizeMemory){
                            int c = 0;
                            if(!verifyProcessInMainMemory(nome, Integer.parseInt(valor))) {
                                Page page = getProcessOfSecondaryMemory(nome, Integer.parseInt(valor));
                                Frame frame = new Frame(page.getInstructionList(), page.getSize(), mainMemory.size(), page.getProcess(), page.getAddress());
                                secondaryMemory.remove(page);
                                mainMemory.add(frame);

//                            for(int j = 0;i<frame.getSize();i++) {
//                                System.out.println("adicionando o processo " + frame.getProcess().getName() +
//                                        " na memória principal com a operação de " + op+ " endereco "+frame.getProcess().getCount());
//                                c = frame.getProcess().getCount();
//                                frame.getProcess().setCount(c+1);
//                            }
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
                            System.out.println("precisa fazer swap");
                        }
                    }

                }
//                System.out.println(stringdaLinha[col*1]);
//                System.out.println(stringdaLinha[col*2]);
//                System.out.println(stringdaLinha[col*3]);
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


}