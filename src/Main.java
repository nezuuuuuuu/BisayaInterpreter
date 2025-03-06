public class Main {

    public static void main(String[] args) {

        Tokenizer token;
        token= new Tokenizer("MUGNA TINUOD c = OO c");

        VariableHandler.declareVariable(token.tokenize());
//        System.out.print(token.tokens);



//        Interpreter interpreter = new Interpreter();

    }
}