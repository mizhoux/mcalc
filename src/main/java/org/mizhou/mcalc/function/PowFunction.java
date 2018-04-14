package org.mizhou.mcalc.function;

import java.math.BigDecimal;
import org.mizhou.mcalc.token.Num;

/**
 * pow 函数
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class PowFunction implements Function {

    @Override
    public Num apply(Num[] params) {
        if (params.length != 2) {
            throw new RuntimeException("pow 函数只接收 2 个参数");
        }

        BigDecimal a = params[0].value();
        BigDecimal b = params[1].value();

        if (b.toString().indexOf('.') == -1) { // 是整数
            BigDecimal result = a.pow(b.intValue());
            
            return new Num(result);
        }

        double result = Math.pow(a.doubleValue(), b.doubleValue());
        
        return new Num(result);
    }

}
