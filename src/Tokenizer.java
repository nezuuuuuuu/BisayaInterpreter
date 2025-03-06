import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String[] lines;
    private int lineIndex = 0;
    private int pos = 0;

    public Tokenizer(String input) {
        this.lines = input.split("\n");
    }
    List<Token> tokens = new ArrayList<>();

    public List<Token> tokenize() {

        while (lineIndex < lines.length) {
            String line = lines[lineIndex];

            while (pos < line.length()) {
                char current = line.charAt(pos);

                if (Character.isWhitespace(current)) {
                    pos++;
                } else if (Character.isDigit(current)) {
                    tokens.add(new Token(TokenType.NUMBER, readNumber(line)));
                } else if (Character.isLetter(current) || current == '\'') {

                    readIdentifierOrKeyword(line);
                } else if ("+-*/".indexOf(current) != -1) {
                    tokens.add(new Token(TokenType.OPERATOR, String.valueOf(current)));
                    pos++;
                } else if (current == '(' ) {
                    tokens.add(new Token(TokenType.LPAREN, String.valueOf(current)));
                    pos++;
                } else if ( current == ')') {
                    tokens.add(new Token(TokenType.RPAREN, String.valueOf(current)));
                    pos++;
                }
                else if (current == '=') {
                    tokens.add(new Token(TokenType.ASSIGN, "="));
                    pos++;
                } else {
                    throw new RuntimeException("Unexpected character: " + current);
                }
            }

            lineIndex++;
            pos = 0;
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private String readNumber(String line) {
        StringBuilder number = new StringBuilder();
        while (pos < line.length() && Character.isDigit(line.charAt(pos))) {
            number.append(line.charAt(pos));
            pos++;
        }
        return number.toString();
    }

    private void readIdentifierOrKeyword(String line) {

        StringBuilder identifier = new StringBuilder();
        while (pos < line.length() && (Character.isLetter(line.charAt(pos)) || line.charAt(pos)=='\'')) {
            identifier.append(line.charAt(pos));
            pos++;
        }



        if(identifier.toString().startsWith("'") && identifier.toString().endsWith("'")&&identifier.toString().length()==3){

            tokens.add(new Token(TokenType.CHAR,identifier.toString().charAt(1)+""));

            return;

        }

        if(Keyword.keywords.containsKey(identifier.toString())){
            tokens.add(new Token(TokenType.KEYWORD,identifier.toString()));
            return ;
        }
        tokens.add(new Token(TokenType.IDENTIFIER,identifier.toString()));
        return ;

    }
}
