package org.mizhou.mcalc.token;

/**
 * Token
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface Token {

    public TokenType getType();

    public String text();

    public boolean isNumber();

    public boolean isOperator();

    public boolean isBracket();

}
