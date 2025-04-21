import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Interpreter {
    boolean started = false;
    BufferedReader bf;


    public Interpreter() {
        try {
            bf = new BufferedReader(new FileReader("C:\\Users\\Nico\\IdeaProjects\\Bisaya Interpreter\\src\\TEST_CASES\\declaration01"));
            String curr;
            System.out.println();


            while((curr=bf.readLine())!=null){
                if(curr.contains("SUGOD")){
                    System.out.println("accept");
                }else{
                    System.out.println("not found");
                }


            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
