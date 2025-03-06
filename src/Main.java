public class Main {

    public static void main(String[] args) {
        
        Tokenizer token= new Tokenizer("MUGNA LETRA d = 'h'");
        VariableHandler.declareVariable(token.tokenize());
        token= new Tokenizer("MUGNA NUMERO c = 7");

        VariableHandler.declareVariable(token.tokenize());
//        System.out.print(token.tokens);

    }
}