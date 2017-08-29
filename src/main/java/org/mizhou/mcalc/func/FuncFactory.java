package org.mizhou.mcalc.func;

/**
 * 函数工厂
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class FuncFactory {

    public static Func getFunc(String name) {
        switch (name.toLowerCase()) {
            case "log":
                return new LogFunc();
            case "pow":
                return new PowFunc();
            case "pi":
                return new PIFunc();
        }

        return null;
    }
}
