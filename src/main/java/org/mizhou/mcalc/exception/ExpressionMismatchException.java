package org.mizhou.mcalc.exception;

/**
 * Mismatch Expression Exception
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class ExpressionMismatchException extends RuntimeException {

    public ExpressionMismatchException() {
        super("请先将表达式转为后缀表达式再计算");
    }

}
