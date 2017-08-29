package org.mizhou.mcalc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mizhou.mcalc.token.Bracket;
import org.mizhou.mcalc.token.Function;
import org.mizhou.mcalc.token.Num;
import org.mizhou.mcalc.token.Operator;
import org.mizhou.mcalc.token.Token;

/**
 * 表达式
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class Expression {

    private static final String REG_EXPR
            = "\\s*((\\(-(\\d*\\.\\d+|\\d+)\\))|(\\d*\\.\\d+|\\d+)|(\\+|-|\\*|/)|(\\(|\\))|([A-Za-z]+\\(.*\\)))\\s*";
    private static final Pattern PATTERN = Pattern.compile(REG_EXPR);

    private final List<Token> tokens; // 该表达式中的所有 Token
    private final boolean postfix;    // 该表达式是否为后缀表达式的标识

    public Expression(List<Token> tokens, boolean postfix) {
        this.tokens = tokens;
        this.postfix = postfix;
    }

    public Expression(List<Token> tokens) {
        this(tokens, false);
    }

    /**
     * 由给定的字符串获得一个表达式（中缀）
     *
     * @param expr 给定的字符串
     * @return 表达式（中缀）
     */
    public static Expression of(String expr) {
        List<Token> exprTokens = parseTokens(expr);
        return new Expression(exprTokens, false);
    }

    private static List<Token> parseTokens(String expr) {
        Objects.requireNonNull(expr);
        expr = expr.trim(); // 去除开头的空格
        if (expr.startsWith("-") || expr.startsWith("+")) {
            expr = "0" + expr;        // 在表达式最前面补个 0
        }

        List<Token> ts = new ArrayList<>();

        Matcher matcher = PATTERN.matcher(expr);
        int start = 0, end = expr.length();

        while (start < end) {
            // 设定正则的查找范围在 [start, end)，不包括 end
            matcher.region(start, end);

            // lookingAt() 方法会从 start 位置开始匹配下一个满足正则的子串
            // 我也不知道当年 Java 的开发人员为什么会取 lookingAt 这么鬼畜的名字
            if (matcher.lookingAt()) { //  如果找到了一个匹配正则的子串
                Token token = getToken(matcher);
                ts.add(token);

                start = matcher.end(); // end() 方法会返回上一次匹配的子串的末尾的位置

            } else { // 没有找到匹配正则的子串，说明表达式包含了非正则中定义的文本
                String errorExpr = expr.substring(start);
                throw new RuntimeException("解析错误：" + errorExpr);
            }
        }

        return ts;
    }

    private static Token getToken(Matcher matcher) {
        // matcher.group(0) 匹配整个正则，matcher.group(1) 匹配第一个括号
        String m = matcher.group(1);

        if (m != null) {
            if (matcher.group(2) != null) { // 负数
                // matcher.group(3) 提取形如 (-1.2) 中的 1.2
                return new Num("-" + matcher.group(3));

            } else if (matcher.group(4) != null) { // 正数
                return new Num(matcher.group(4));

            } else if (matcher.group(5) != null) { // 运算符
                return new Operator(matcher.group(5).charAt(0));

            } else if (matcher.group(6) != null) { // 括号
                return new Bracket(matcher.group(6).charAt(0));

            } else if (matcher.group(7) != null) { // 函数
                Function function = new Function(matcher.group(7));
                Num num = function.getResult(); // 直接计算出函数的值作为 Token

                return num;
            }
        }

        throw new RuntimeException("getToken"); // 正则无误的情况下不会发生
    }

    /**
     * 该表达式是否为后缀表达式
     *
     * @return 如果该表达式是否为后缀表达式返回 true，否则返回 false
     */
    public boolean isPostfix() {
        return postfix;
    }

    /**
     * 通过后缀表达式计算表达式的值
     *
     * @return 表达式的值
     */
    public Num calculate() {
        if (!isPostfix()) {
            throw new RuntimeException("请先将表达式转为后缀表达式再计算");
        }

        ArrayDeque<Token> stack = new ArrayDeque<>();

        for (Token token : tokens) {

            if (token.isNumber()) {
                stack.push(token);

            } else {
                Num n1 = (Num) stack.pop();
                Num n2 = (Num) stack.pop();
                Operator op = (Operator) token;

                Num result = n2.operate(op, n1);
                stack.push(result);
            }

        }

        if (stack.size() != 1) { // 栈中最后剩下的不止一个数，说明表达式有问题
            throw new RuntimeException("错误的表达式");
        }

        return (Num) stack.pop();
    }

    /**
     * 获得该表达式的后缀形式
     *
     * @return 后缀表达式
     */
    public Expression toPostfixExpr() {
        ArrayDeque<Token> S = new ArrayDeque<>(); // 运算符栈
        ArrayList<Token> L = new ArrayList<>();   // 保存中间结果的列表

        for (Token token : tokens) {
            switch (token.getType()) {
                case NUMBER:
                    L.add(token);
                    break;

                case OPERATOR:
                    Operator op = (Operator) token;
                    boolean back = true;

                    while (back) {
                        back = false;

                        if (S.isEmpty()) { // 运算符栈为空
                            S.push(op);

                        } else {  // 运算符栈不为空
                            Token top = S.peek();

                            // 运算符栈栈顶为 '('
                            if (top.isBracket() && ((Bracket) top).isLeft()) {
                                S.push(op);

                                // op 的优先级大于运算符栈栈顶元素的优先级
                            } else if (op.isHigherThan((Operator) top)) {
                                S.push(token);

                            } else { // op 的优先级小于运算符栈栈顶元素的优先级
                                L.add(S.pop());
                                back = true; // 回到 while
                            }
                        }
                    }
                    break;

                case BRACKET:
                    if (((Bracket) token).isLeft()) {
                        S.push(token);

                    } else {
                        for (Token t = S.pop();
                                !t.isBracket(); t = S.pop()) {
                            L.add(t);
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        while (!S.isEmpty()) {
            L.add(S.pop());
        }

        return new Expression(L, true); // true 表示该表达式为后缀表达式
    }

    @Override
    public String toString() {
        StringBuilder expr = new StringBuilder();

        for (Token token : tokens) {
            expr.append(token.text()).append(' ');
        }

        expr.deleteCharAt(expr.length() - 1); // 删除末尾的空格

        return expr.toString();
    }

}
