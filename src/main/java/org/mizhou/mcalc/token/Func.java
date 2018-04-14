package org.mizhou.mcalc.token;

import org.mizhou.mcalc.exception.UnknownFunctionException;
import org.mizhou.mcalc.function.FunctionFactory;
import org.mizhou.mcalc.function.Function;

/**
 * 函数 Token
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class Func extends AbstractToken implements Token {

    private static final Num[] PARAMS_NONE = new Num[0];

    private final String name;  // 名字
    private final Num[] params; // 参数

    public Func(String content) {
        int indexOfLeftBracket = content.indexOf('(');
        int indexOfRightBracket = content.lastIndexOf(')');

        name = content.substring(0, indexOfLeftBracket);

        String paramsContent = content.substring(
                indexOfLeftBracket + 1, indexOfRightBracket);

        // 提取出各个参数
        String[] paramStrs = paramsContent.split(",");
        if (paramStrs.length == 1 && paramStrs[0].isEmpty()) { // 没有参数
            params = PARAMS_NONE;

        } else { // 有参数
            params = new Num[paramStrs.length];

            for (int i = 0; i < params.length; i++) {
                String paramStr = paramStrs[i].trim();
                
                // 如果需要参数也能是表达式，修改这个地方
                // 先计算出表达式的值（Num），然后将其作为参数
                params[i] = new Num(paramStr); 
            }
        }
    }

    /**
     * 获得函数的结果
     *
     * @return 函数的结果
     */
    public Num getResult() {
        Function function = FunctionFactory.getFunction(name);

        if (function != null) {
            return function.apply(params);
        }

        throw new UnknownFunctionException(name);
    }

    @Override
    public TokenType getType() {
        return TokenType.FUNCTION;
    }

    @Override
    public String text() {
        StringBuilder function = new StringBuilder();

        function.append(name).append('(');
        for (Num param : params) {
            function.append(param.text()).append(", ");
        }
        function.delete(function.length() - 2, function.length());
        function.append(')');

        return function.toString();
    }

}
