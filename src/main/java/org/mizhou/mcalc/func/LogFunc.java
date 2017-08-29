package org.mizhou.mcalc.func;

import org.mizhou.mcalc.token.Num;

/**
 * log 函数
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class LogFunc implements Func {

    @Override
    public Num apply(Num[] params) {
        if (params.length != 2) {
            throw new RuntimeException("log 函数只接收 2 个参数");
        }

        double a = params[0].value().doubleValue();
        double b = params[1].value().doubleValue();

        double result = Math.log10(b) / Math.log10(a);

        return new Num(String.valueOf(result));
    }

}
