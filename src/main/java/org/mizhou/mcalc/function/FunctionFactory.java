package org.mizhou.mcalc.function;

import java.util.Objects;

/**
 * 函数工厂
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public final class FunctionFactory {

    public static Function getFunction(String name) {
        Objects.requireNonNull(name);

        name = name.toLowerCase();
        switch (name) {
            case "pi":
                return new PIFunction();
                
            case "log":
                return new LogFunction();
                
            case "pow":
                return new PowFunction();
        }

        return null;
    }
}
