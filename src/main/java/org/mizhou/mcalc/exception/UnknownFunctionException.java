package org.mizhou.mcalc.exception;

/**
 * Unknown Function Exception
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class UnknownFunctionException extends RuntimeException {

    public UnknownFunctionException(String functionName) {
        super("未知的函数：" + functionName);
    }

}
