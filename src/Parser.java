import java.util.List;

public class Parser {
    public static int index=0;
    public static void parseToken(List<Token> tokens) throws Exception {

        Token current ;
        int size = tokens.size();
        boolean SOF= false;
//        System.out.println(tokens);
        for(;index<size;index++){
            current = tokens.get(index);

            switch (current.getType()) {

                case TokenType.KEYWORD:
                    switch (current.getValue()) {
                        case "SUGOD":
                            if(SOF){

                            throw new Exception("DOUBLE SUGOD");
                            }
                            SOF = true;
                            break;
                        case "MUGNA":

                            Object[] array = VariableHandler.declareVariable(tokens,index);

                            index=Integer.parseInt(array[1].toString());

//                            System.out.println(VariableHandler.variables.get("c"));
                            break;
                        case "IPAKITA":

                            Printer.ipakita(tokens,index);
                            break;


                    }

            }



        }

        index=0;
    }

}
