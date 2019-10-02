import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Principal {

    public static void main (String args[]){
        leituraArquivo("file.txt");


    }
    public static void leituraArquivo(String file) {
        Scanner in;
        String[] stringdaLinha;
        int col=0;
        try {
            in = new Scanner(new FileReader(file));
            while (in.hasNextLine()) {
                col++;
                String line = in.nextLine();
                stringdaLinha =  line.split(" ");
                for(int i =0 ;i<stringdaLinha.length;i++)
                    if(!stringdaLinha[i].equals(""))
                        System.out.println(stringdaLinha[i]);
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
