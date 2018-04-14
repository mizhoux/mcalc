package org.mizhou.mcalc.function;

import org.mizhou.mcalc.token.Num;

/**
 * π，Pi
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class PIFunction implements Function {

    @Override
    public Num apply(Num[] params) {
        if (params.length != 0) {
            throw new RuntimeException("pi 函数没有参数");
        }

        return new Num("3.141592653589793");
    }

}
