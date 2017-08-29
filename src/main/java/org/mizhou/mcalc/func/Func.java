package org.mizhou.mcalc.func;

import org.mizhou.mcalc.token.Num;

/**
 * 函数
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface Func {

    public Num apply(Num[] params);

}
