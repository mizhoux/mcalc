package org.mizhou.mcalc.token;

/**
 * 抽象 Token
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public abstract class AbstractToken implements Token {

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public boolean isBracket() {
        return false;
    }

}
