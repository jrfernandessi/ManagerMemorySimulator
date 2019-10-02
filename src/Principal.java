import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    static List<String> memoriaPrincipal = new ArrayList<>();
    static List<String> memoriaSecundaria = new ArrayList<>();
    static boolean flagPrincipal = false;
    static boolean flagSecundaria = false;

    public static void main (String args[]){
        leituraArquivo("file.txt");


    }
    public static void leituraArquivo(String file) {
        Scanner in;
        String[] stringdaLinha;
        int col=0;
        int contador=0;
        List<Processo> processos= new ArrayList<>();
        try {
            in = new Scanner(new FileReader(file));
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
                for(int i =0 ;i<stringdaLinha.length;i++) {
                    if (op.equals("C") && count==0) {
                        System.out.println("criando processo " + nome);
                        Processo processo = new Processo();
                        processo.setNome(nome);
                        processo.setTamanho(Integer.parseInt(valor));
                        processos.add(processo);
                        count++;
                        if(count==3)
                            count=0;
                    } else if (op.equals("I/O")&& count==0) {
                        System.out.println("Processo " + nome + " bloqueado por operação de entrada e saída");
                        count++;
                        if(count==3)
                            count=0;
                    } else if ((op.equals("W") || op.equals("R"))&& count==0) {
//                        System.out.println("alocando processo " + nome + " no endereço " + valor + " na operação de " + op);


                        if(memoriaPrincipal.size()<2){
                            System.out.println("alocando processo " + nome + " no endereço " + valor + " na operação de " + op+ " na memoria principal");
                            memoriaPrincipal.add(nome);
                        }else{
                            String aux = memoriaPrincipal.get(0);
                            if(!memoriaPrincipal.get(0).equals(nome)) {
                                memoriaPrincipal.remove(0);
                                memoriaPrincipal.add(nome);
                                System.out.println("Trocando processo " + aux + " por " + nome + " na memoria principal");
                            }else if(memoriaSecundaria.size()<4){
                                System.out.println("alocando processo " + aux + " na memoria secundaria");
                            }
                        }

                        count++;
                        if(count==3)
                            count=0;
                    }
                }
//                System.out.println(stringdaLinha[col*1]);
//                System.out.println(stringdaLinha[col*2]);
//                System.out.println(stringdaLinha[col*3]);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
