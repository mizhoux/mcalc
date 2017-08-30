package org.mizhou.mcalc.token;

/**
 * 运算符
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class Operator extends AbstractToken implements Token {

    private final char value;

    public Operator(char value) {
        if (value == '+' || value == '-' || value == '*' || value == '/') {
            this.value = value;
        } else {
            throw new RuntimeException(String.format("未知的运算符：%c", value));
        }
    }

    public char value() {
        return value;
    }

    /**
     * 获得运算符的优先级
     *
     * @return 运算符的优先级
     */
    public int property() {
        switch (value) {
            case '*':
            case '/':
                return 1;
            default:
                return 0;
        }
    }

    /**
     * 该运算符的优先级是否大于所给的运算符的优先级
     *
     * @param other 所给的运算符
     * @return 如果该运算符的优先级是否大于所给的运算符的优先级，返回 true，否则返回 false
     */
    public boolean isHigherThan(Operator other) {
        return this.property() > other.property();
    }

    @Override
    public TokenType getType() {
        return TokenType.OPERATOR;
    }

    @Override
    public String text() {
        return String.valueOf(value);
    }

}
