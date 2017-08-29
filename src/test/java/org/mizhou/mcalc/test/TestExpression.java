package org.mizhou.mcalc.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mizhou.mcalc.Expression;

public class TestExpression {

    @Test
    public void testToPostfixExpr() {
        Expression expr = Expression.of("1 + (3 - 2) * 4 / 5");
        assertEquals("1 + ( 3 - 2 ) * 4 / 5", expr.toString());
        assertEquals("1 3 2 - 4 * 5 / +", expr.toPostfixExpr().toString());
    }

    @Test
    public void testConstructor() {
        // 测试数与运算符解析
        Expression expr = Expression.of("1+2*3");
        assertEquals("1 + 2 * 3", expr.toString());

        expr = Expression.of("1 + 2 * 3 / 6");
        assertEquals("1 + 2 * 3 / 6", expr.toString());

        // 测试括号解析
        expr = Expression.of("(1 + 2) * 3");
        assertEquals("( 1 + 2 ) * 3", expr.toString());

        expr = Expression.of("(1+ 2 * 3) * (6+1)");
        assertEquals("( 1 + 2 * 3 ) * ( 6 + 1 )", expr.toString());

        // 测试小数解析
        expr = Expression.of("0.65- 0.56");
        assertEquals("0.65 - 0.56", expr.toString());

        expr = Expression.of(".65 - .56");
        assertEquals("0.65 - 0.56", expr.toString());

        // 测试负数解析
        expr = Expression.of("-1 + 2 *  3");
        assertEquals("0 - 1 + 2 * 3", expr.toString());

        expr = Expression.of("1 + (-2)");
        assertEquals("1 + (-2)", expr.toString());

        expr = Expression.of("1 + (-2.345)");
        assertEquals("1 + (-2.345)", expr.toString());

        expr = Expression.of("1 + (-.345)");
        assertEquals("1 + (-0.345)", expr.toString());

        // 测试捕获解析异常
        try {
            Expression.of("1 + 2 * 3 / 6 # 8");
            fail("1 + 2 * 3 / 6 # 8");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }

        // 测试函数解析
        expr = Expression.of("1.23456789 * pow(2, 4)");
        assertEquals("1.23456789 * 16", expr.toString());

        expr = Expression.of("1.23456789 * log(2, 4)");
        assertEquals("1.23456789 * 2.0", expr.toString());

        expr = Expression.of("pi()");
        assertEquals("3.141592653589793", expr.toString());

        try {
            Expression.of("1 + abc(123, 456)");
            fail("1 + abc(123, 456)");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
