import java.util.ArrayList;
import java.util.List;

public class Printer {
    public static int current_index;


    public static void ipakita(List<Token> tokenList,  int index){
        ++Parser.index;
//        System.out.println("HERERE"+tokenList.get( ++Parser.index));
        String to_print="";
        Token token = tokenList.get(Parser.index);
        System.out.println(token);
        while(true) {
            List<Token> tempList= new ArrayList<>();
            switch (token.getType()){

                case TokenType.IDENTIFIER:
                    if(VariableHandler.variables.get( token.getValue()) instanceof Character ){
                        System.out.println("its a character");
                        to_print+=VariableHandler.variables.get( token.getValue());
                        break;
                    }

                    while(token.getType()!=TokenType.AMPERSAND){
                        System.out.println("here");
                        if(token.getType()== TokenType.IDENTIFIER){
                            tempList.add(new Token(TokenType.NUMBER,VariableHandler.variables.get(token.getValue()).toString()));
                        }else if(token.getType()==TokenType.OPERATOR){
                            tempList.add(new Token(TokenType.OPERATOR,token.getValue()));
                        }else if(token.getType()==TokenType.NUMBER){
                            tempList.add(new Token(TokenType.NUMBER,token.getValue()));
                        }
                        if (token.getType().equals(TokenType.EOF)){
                            System.out.println("done");
                            break;
                        }
                        if(token.getType()!=TokenType.AMPERSAND)
                            token=  tokenList.get(++Parser.index);

                    }
                    to_print +=VariableHandler.evaluateExpression(tempList);
//                    VariableHandler.evaluateExpression();
                case TokenType.NUMBER:
                    while(token.getType()!=TokenType.AMPERSAND){

                        if(token.getType()== TokenType.IDENTIFIER){
                            tempList.add(new Token(TokenType.NUMBER,VariableHandler.variables.get(token.getValue()).toString()));
                        }else if(token.getType()==TokenType.OPERATOR){
                            tempList.add(new Token(TokenType.OPERATOR,token.getValue()));
                        }else if(token.getType()==TokenType.NUMBER){
                            tempList.add(new Token(TokenType.NUMBER,token.getValue()));
                        }
                        if (token.getType().equals(TokenType.EOF)){
                            System.out.println("done");
                            break;
                        }
                        if(Parser.index<tokenList.size()-1)
                            token=  tokenList.get(++Parser.index);
                    }
                    to_print +=VariableHandler.evaluateExpression(tempList);
                    break;

            }
            token=  tokenList.get(++Parser.index);
            if (token.getType()==TokenType.AMPERSAND){
                token=  tokenList.get(++Parser.index);
            }else{
                System.out.println("VALUEEEE "+ to_print);
                to_print="";
                return;
            }

        }




    }
}
