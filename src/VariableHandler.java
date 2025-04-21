import java.util.*;

public class VariableHandler {
    static HashMap<String, Object> variables = new HashMap<>();
    public static int current_index;

    public static Object[] declareVariable(List<Token> tokens, int index) {
        current_index = index;
//        System.out.println(tokens.get(current_index));
        if (!tokens.get(current_index).value.equals("MUGNA")) {
            System.out.println("Expected 'MUGNA' at position " + current_index);
            return null;
        }

        Token dataTypeToken = tokens.get(++current_index);
        if (dataTypeToken.type != TokenType.KEYWORD) {
            System.out.println("Expected data type keyword after MUGNA");
            return null;
        }

        Object returnValue = null;
        switch (dataTypeToken.value) {
            case "LETRA":
                returnValue = dataTypeChar(tokens);
                break;
            case "NUMERO":
                returnValue =(int) dataTypeInt(tokens);
                break;
            case "TINUOD":
                returnValue = dataTypeBool(tokens);
                break;
            case "TIPIK":
                returnValue = dataTypeFloat(tokens);
                break;
            default:
                System.out.println("Unknown data type: " + dataTypeToken.value);
                return null;
        }

        return new Object[]{returnValue, current_index};
    }


    public static int dataTypeInt(List<Token> tokens) {


        return dataTypeInteger(tokens).intValue();
    }
    public static Integer dataTypeInteger(List<Token> tokens) {

        Token current = tokens.get(++current_index);
        String variableName;


        if (current.type == TokenType.IDENTIFIER) {
            if(variables.containsKey(current.value)){
                System.out.println("Invalid variable name. name already used");
                return 0;
            }
            variableName = current.value;

        } else {
            System.out.println("Invalid variable name. keyword");
            return 0;
        }






        if (tokens.isEmpty() || tokens.get(++current_index).type != TokenType.ASSIGN) {
            System.out.println("Operator '=' should be after identifier");
            return 0;
        }




        Integer value = evaluateExpression(tokens.subList(current_index,tokens.size()-1)).intValue();
        if (value == null) {
            System.out.println("Invalid expression");
            return 0;
        }


        variables.put(variableName, value);
        ;
        return value;
    }


    public static Double dataTypeFloat(List<Token> tokens) {

        Token current = tokens.get(++current_index);
        String variableName;


        if (current.type == TokenType.IDENTIFIER) {
            if(variables.containsKey(current.value)){
                System.out.println("Invalid variable name. name already used");
                return .0;
            }
            variableName = current.value;

        } else {
            System.out.println("Invalid variable name. keyword");
            return .0;
        }






        if (tokens.isEmpty() || tokens.get(++current_index).type != TokenType.ASSIGN) {
            System.out.println("Operator '=' should be after identifier");
            return .0;
        }




        Double value = evaluateExpression(tokens.subList(current_index,tokens.size()-1));
        if (value == null) {
            System.out.println("Invalid expression");
            return .0;
        }


        variables.put(variableName, value);
        ;
        return value;
    }
    public static char dataTypeChar(List<Token> tokens) {
        Token current = tokens.get(++current_index);
        String variableName;

        if (current.type == TokenType.IDENTIFIER) {
            if(variables.containsKey(current.value)){
                System.out.println("Invalid variable name. name already used");
               return 'n';
            }
            variableName = current.value;
        } else {
            System.out.println("Invalid variable name");
            return 'n';
        }


        if (tokens.isEmpty() || tokens.get(++current_index).type != TokenType.ASSIGN) {
            System.out.println("Operator '=' should be after identifier");
            return 'n';
        }



        current = tokens.get(++current_index);
        if (current.type == TokenType.CHAR){
            if(current.value.length()==1){
                variables.put(variableName, current.value.charAt(0));
            }
        }



        return  current.value.charAt(0);

    }
    public static boolean dataTypeBool(List<Token> tokens) {
        Token current = tokens.get(++current_index);
        String variableName;


        if (current.type == TokenType.IDENTIFIER) {
            if(variables.containsKey(current.value)){
                System.out.println("Invalid variable name. name already used");
                return false;
            }
            variableName = current.value;
        } else {
            System.out.println("Invalid variable name");
            return false;
        }


        if (tokens.isEmpty() || tokens.get(++current_index).type != TokenType.ASSIGN) {
            System.out.println("Operator '=' should be after identifier");
            return false;
        }



        current = tokens.get(++current_index);


//        System.out.println(tokens);
//           if(tokens.size()>1){
//               System.out.println("");
//               return false;
//           } implement later for logical operators



//        System.out.println(current.value);
        if (Objects.equals(current.value, "OO")){

            variables.put(variableName, true);

        }else if (Objects.equals(current.value, "DILI")){

            variables.put(variableName, false);

        }else {
            System.out.println("Error only OO and DILI");
            return false;
        }


        return true;

    }


    public static Double evaluateExpression(List<Token> tokens) {
        List<Token> postfix = convertToPostfix(tokens);
        return evaluatePostfix(postfix);
    }


    private static List<Token> convertToPostfix(List<Token> tokens) {
        Stack<Token> operatorStack = new Stack<>();
        List<Token> output = new ArrayList<>();

        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("^", 3);

        for (Token token : tokens) {
            if (token.type == TokenType.NUMBER) {
                output.add(token);
            } else if (token.type == TokenType.IDENTIFIER) {
                if (variables.containsKey(token.value)) {
                    output.add(new Token(TokenType.NUMBER, variables.get(token.value).toString()));
                } else {
                    System.out.println("Undefined variable: " + token.value);
                    return null;
                }
            } else if (token.type == TokenType.OPERATOR) {
                while (!operatorStack.isEmpty() &&
                        precedence.getOrDefault(operatorStack.peek().value, 0) >= precedence.getOrDefault(token.value, 0)) {
                    output.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (token.type == TokenType.LPAREN) {
                operatorStack.push(token);
            } else if (token.type == TokenType.RPAREN) {
                while (!operatorStack.isEmpty() && operatorStack.peek().type != TokenType.LPAREN) {
                    output.add(operatorStack.pop());
                }
                operatorStack.pop();
            }
        }

        while (!operatorStack.isEmpty()) {
            output.add(operatorStack.pop());
        }

        return output;
    }


    private static Double evaluatePostfix(List<Token> tokens) {
        Stack<Double> stack = new Stack<>();

        for (Token token : tokens) {
            if (token.type == TokenType.NUMBER) {
                stack.push(Double.parseDouble(token.value));
            } else if (token.type == TokenType.OPERATOR) {
                if (stack.size() < 2) {
                    System.out.println("Invalid expression");
                    return .0;
                }

                double b = stack.pop();
                double a = stack.pop();
                switch (token.value) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/":
                        if (b == 0) {
                            System.out.println("Division by zero error");
                            return .0;
                        }
                        stack.push(a / b);
                        break;
                    case "^": stack.push(Math.pow(a, b)); break;
                    default:
                        System.out.println("Invalid operator: " + token.value);
                        return .0;
                }
            }
        }

        return stack.isEmpty() ? null :  stack.pop();
    }
}
