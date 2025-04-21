import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        Tokenizer token;
//        token= new Tokenizer("SUGOD \n MUGNA LETRA c = 's' \nIPAKITA:  x & t & z & $ & a_1 & [#] & \"last\"  \n KATAPUSAN");
//        token= new Tokenizer("SUGOD \n MUGNA LETRA c = 's' \n MUGNA LETRA k = 'a' \nIPAKITA:  c & k &2 \n KATAPUSAN");
        try {
            token = new Tokenizer(readFromFile("C:\\Users\\Nico\\IdeaProjects\\BisayaInterpreter\\src\\TEST_CASES\\testcase1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Parser.parseToken(token.tokenize());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



//        VariableHandler.declareVariable(token.tokenize());
//        System.out.print(token.tokens);



//        Interpreter interpreter = new Interpreter();

    }
    private static String readFromFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}