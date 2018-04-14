package org.mizhou.mcalc.function;

import org.mizhou.mcalc.token.Num;

/**
 * 函数
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface Function {

    /**
     * 函数运算
     *
     * @param params 函数的参数
     * @return 运算的结果
     */
    public Num apply(Num[] params);

}
