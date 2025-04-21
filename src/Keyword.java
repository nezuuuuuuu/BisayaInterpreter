import java.util.HashMap;
import java.util.Map;

public class Keyword {
    public static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("MUGNA", TokenType.KEYWORD);
        keywords.put("NUMERO", TokenType.KEYWORD);
        keywords.put("LETRA", TokenType.KEYWORD);
        keywords.put("TINUOD", TokenType.KEYWORD);
        keywords.put("TIPIK", TokenType.KEYWORD);
        keywords.put("IPAKITA:", TokenType.KEYWORD);
        keywords.put("DAWAT", TokenType.KEYWORD);
        keywords.put("KUNG", TokenType.KEYWORD);
        keywords.put("KUNG_WALA", TokenType.KEYWORD);
        keywords.put("KUNG_DILI", TokenType.KEYWORD);
        keywords.put("ALANG SA", TokenType.KEYWORD);
        keywords.put("UG", TokenType.KEYWORD);
        keywords.put("O", TokenType.KEYWORD);
        keywords.put("SUGOD", TokenType.KEYWORD);
        keywords.put("KATAPUSAN", TokenType.KEYWORD);
        keywords.put("DILI", TokenType.KEYWORD);
        // Add more keywords as you define them
    }
}