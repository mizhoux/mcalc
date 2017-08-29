package org.mizhou.mcalc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import org.mizhou.mcalc.token.Num;

/**
 * 启动器
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        System.out.println("欢迎使用你的计算器（输入 e(xit) 退出）");

        try (Reader in = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(in)) {

            String line;
            while (true) {
                System.out.print("> ");
                line = reader.readLine();

                if (null == line
                        || "e".equalsIgnoreCase(line)
                        || "exit".equalsIgnoreCase(line)) {
                    break;
                } else if (line.isEmpty()) {
                    continue;
                }

                try {
                    Expression expr = Expression.of(line);
                    Expression postfixExpr = expr.toPostfixExpr();
                    Num result = postfixExpr.calculate();

                    System.out.println(result);

                } catch (ArithmeticException ex) {
                    System.out.println("运算错误：" + ex.getMessage());
                } catch (RuntimeException ex) {
                    System.out.println("运行错误：" + ex.getMessage());
                    // ex.printStackTrace(System.err);
                }

            }
        }
    }
}
