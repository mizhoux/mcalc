package org.mizhou.mcalc.token;

/**
 * 括号（左括号或者右括号）
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class Bracket extends AbstractToken implements Token {

    public static final char CHAR_LEFT_BRACKET = '(';
    public static final char CHAR_RIGHT_BRACKET = ')';

    private final char value;

    public Bracket(char value) {
        if (value == CHAR_LEFT_BRACKET || value == CHAR_RIGHT_BRACKET) {
            this.value = value;
        } else {
            throw new RuntimeException("未知的括号字符：" + value);
        }
    }

    public boolean isLeft() {
        return value == CHAR_LEFT_BRACKET;
    }

    public boolean isRight() {
        return value == CHAR_RIGHT_BRACKET;
    }

    @Override
    public TokenType getType() {
        return TokenType.BRACKET;
    }

    @Override
    public String text() {
        return String.valueOf(value);
    }

}
