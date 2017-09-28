package org.mizhou.mcalc.exception;

/**
 * Invalid Expression Exception
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class InvalidExpressionException extends RuntimeException {

    public InvalidExpressionException() {
        super("错误的表达式");
    }

}
