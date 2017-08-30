package org.mizhou.mcalc.token;

/**
 * 抽象 Token
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public abstract class AbstractToken implements Token {

    @Override
    public boolean isNumber() {
        return getType() == TokenType.NUMBER;
    }

    @Override
    public boolean isOperator() {
        return getType() == TokenType.OPERATOR;
    }

    @Override
    public boolean isBracket() {
        return getType() == TokenType.BRACKET;
    }

    @Override
    public boolean isFunction() {
        return getType() == TokenType.FUNCTION;
    }

}
