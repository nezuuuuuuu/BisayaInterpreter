import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String[] lines;
    private int lineIndex = 0;
    private int pos = 0;
    List<Token> tokens = new ArrayList<>();

    public Tokenizer(String input) {
        this.lines = input.split("\n");
    }

    public List<Token> tokenize() {
        // Check for SUGOD on the first non-empty, non-comment line
        int startLineIndex = 0;
        while (startLineIndex < lines.length) {
            String line = lines[startLineIndex].trim();
            if (!line.isEmpty() && !line.startsWith("--")) {
                if (line.equals("SUGOD")) {
                    startLineIndex++;
                    break;
                } else {
                    throw new RuntimeException("Error at line " + (startLineIndex + 1) + ": Expected 'SUGOD' at the beginning of the program.");
                }
            }
            startLineIndex++;
        }
        this.lineIndex = startLineIndex; // Start tokenizing after 'SUGOD'
        this.pos = 0;

        while (lineIndex < lines.length) {
            String line = lines[lineIndex];
            pos = 0; // Reset position for the new line

            while (pos < line.length()) {
                char current = line.charAt(pos);

                if (Character.isWhitespace(current)) {
                    pos++;
                    continue;
                }
                if (line.startsWith("--", pos)) {
                    break; // Skip the rest of the line (comment)
                }
                if (line.startsWith("IPAKITA:", pos)) {
                    handlePrintingTokens(line);
                    break; // Move to the next line after processing IPAKITA
                }

                if (Character.isDigit(current)) {
                    tokens.add(new Token(TokenType.NUMBER, readNumber(line)));
                    continue;
                }

                if (Character.isLetter(current) || current == '_') {
                    readIdentifierOrKeyword(line);
                    continue;
                }

                if (current == '\'') {
                    readCharacterLiteral(line);
                    continue;
                }

                switch (current) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                    case '%':
                    case '>':
                    case '<':
                    case '=':
                        readOperator(line);
                        continue;
                    case '(':
                        tokens.add(new Token(TokenType.LPAREN, "("));
                        pos++;
                        continue;
                    case ')':
                        tokens.add(new Token(TokenType.RPAREN, ")"));
                        pos++;
                        continue;
                    case '[':
                        tokens.add(new Token(TokenType.LBRACK, "["));
                        pos++;
                        continue;
                    case ']':
                        tokens.add(new Token(TokenType.RBRACK, "]"));
                        pos++;
                        continue;
                    case ',':
                        tokens.add(new Token(TokenType.COMMA, ","));
                        pos++;
                        continue;
                    case '&':
                        tokens.add(new Token(TokenType.AMPERSAND, "&"));
                        pos++;
                        continue;
                    case '$':
                        tokens.add(new Token(TokenType.DOLLAR, "$"));
                        pos++;
                        continue;
                    case '"':
                        readStringLiteral(line);
                        continue;
                    default:
                        throw new RuntimeException("Unexpected character at line " + (lineIndex + 1) + ", position " + (pos + 1) + ": " + current);
                }
            }
            lineIndex++;
        }

        // Check for KATAPUSAN on the last non-empty, non-comment line
        int lastLineIndex = lines.length - 1;
        while (lastLineIndex >= startLineIndex) { // Start from where tokenization began
            String line = lines[lastLineIndex].trim();
            if (!line.isEmpty() && !line.startsWith("--")) {
                if (line.equals("KATAPUSAN")) {
                    break;
                } else {
                    throw new RuntimeException("Error at the end of the program: Expected 'KATAPUSAN'.");
                }
            }
            lastLineIndex--;
        }

        return tokens;
    }

    private String readNumber(String line) {
        StringBuilder number = new StringBuilder();
        boolean decimalPointFound = false;
        while (pos < line.length()) {
            char currentChar = line.charAt(pos);
            if (Character.isDigit(currentChar)) {
                number.append(currentChar);
                pos++;
            } else if (currentChar == '.' && !decimalPointFound) {
                number.append(currentChar);
                decimalPointFound = true;
                pos++;
            } else {
                break; // Stop reading if it's not a digit or a valid decimal point
            }
        }
        return number.toString();
    }

    private void readIdentifierOrKeyword(String line) {
        StringBuilder identifier = new StringBuilder();
        while (pos < line.length() && (Character.isLetterOrDigit(line.charAt(pos)) || line.charAt(pos) == '_')) {
            identifier.append(line.charAt(pos));
            pos++;
        }

        String identifierStr = identifier.toString();
        if (Keyword.keywords.containsKey(identifierStr)) {
            tokens.add(new Token(TokenType.KEYWORD, identifierStr));
        } else {
            tokens.add(new Token(TokenType.IDENTIFIER, identifierStr));
        }
    }

    private void readIdentifier(String line) {
        StringBuilder identifier = new StringBuilder();
        while (pos < line.length() && (Character.isLetterOrDigit(line.charAt(pos)) || line.charAt(pos) == '_')) {
            identifier.append(line.charAt(pos));
            pos++;
        }
        String identifierStr = identifier.toString();
        tokens.add(new Token(TokenType.IDENTIFIER, identifierStr));
    }

    private void readCharacterLiteral(String line) {
        if (pos + 2 < line.length() && line.charAt(pos) == '\'' && line.charAt(pos + 2) == '\'') {
            tokens.add(new Token(TokenType.CHAR, String.valueOf(line.charAt(pos + 1))));
            pos += 3;
        } else {
            throw new RuntimeException("Invalid character literal at line " + (lineIndex + 1) + ", position " + (pos + 1));
        }
    }

    private void readOperator(String line) {
        StringBuilder operator = new StringBuilder();
        while (pos < line.length() && "+-*/><=!&|".indexOf(line.charAt(pos)) != -1) {
            operator.append(line.charAt(pos));
            pos++;
        }
        String op = operator.toString();

        if (op.equals(">=") || op.equals("<=") || op.equals("==") || op.equals("<>")) {
            tokens.add(new Token(TokenType.OPERATOR, op));
        } else if (op.equals("=")) {
            tokens.add(new Token(TokenType.ASSIGN, op));
        } else if (op.length() == 1) {
            tokens.add(new Token(TokenType.OPERATOR, op));
        } else {
            throw new RuntimeException("Invalid operator at line " + (lineIndex + 1) + ", position " + (pos - op.length() + 1));
        }
    }

    private void readStringLiteral(String line) {
        StringBuilder string = new StringBuilder();
        pos++; // Skip the opening quote
        while (pos < line.length() && line.charAt(pos) != '"') {
            string.append(line.charAt(pos));
            pos++;
        }
        if (pos < line.length() && line.charAt(pos) == '"') {
            tokens.add(new Token(TokenType.STRING, string.toString()));
            pos++; // Skip the closing quote
        } else {
            throw new RuntimeException("Unterminated string literal at line " + (lineIndex + 1));
        }
    }

    public void handlePrintingTokens(String line) {
        tokens.add(new Token(TokenType.KEYWORD, "IPAKITA"));
        pos += "IPAKITA:".length(); // Advance past the keyword

        while (pos < line.length()) {
            char current = line.charAt(pos);

            if (Character.isWhitespace(current)) {
                pos++;
                continue;
            }

            if (Character.isDigit(current)) {
                tokens.add(new Token(TokenType.NUMBER, readNumber(line)));
                continue;
            }

            if (Character.isLetter(current) || current == '_') {
                readIdentifier(line);
                continue;
            }

            switch (current) {
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, "("));
                    pos++;
                    continue;
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ")"));
                    pos++;
                    continue;
                case '#':
                    tokens.add(new Token(TokenType.HASHTAG, "#"));
                    pos++;
                    continue;
                case '[':
                    tokens.add(new Token(TokenType.LBRACK, "["));
                    pos++;
                    continue;
                case ']':
                    tokens.add(new Token(TokenType.RBRACK, "]"));
                    pos++;
                    continue;
                case ',':
                    tokens.add(new Token(TokenType.COMMA, ","));
                    pos++;
                    continue;
                case '&':
                    tokens.add(new Token(TokenType.AMPERSAND, "&"));
                    pos++;
                    continue;
                case '$':
                    tokens.add(new Token(TokenType.DOLLAR, "$"));
                    pos++;
                    continue;
                case '"':
                    readStringLiteral(line);
                    continue;
                default:
                    System.out.println(current);
                    throw new RuntimeException("Unexpected character in IPAKITA statement at line " + (lineIndex + 1) + ", position " + (pos + 1) + ": " + current);
            }


        }
        tokens.add(new Token(TokenType.EOF, "EOF"));
    }
}