package org.mizhou.mcalc.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mizhou.mcalc.Expression;
import org.mizhou.mcalc.exception.UnknownFunctionException;

public class TestExpression {

    @Test
    public void testParse() {
        // 测试数与运算符解析
        Expression expr = Expression.parse("1+2*3");
        assertEquals("1 + 2 * 3", expr.toString());

        expr = Expression.parse("1 + 2 * 3 / 6");
        assertEquals("1 + 2 * 3 / 6", expr.toString());

        // 测试括号解析
        expr = Expression.parse("(1 + 2) * 3");
        assertEquals("( 1 + 2 ) * 3", expr.toString());

        expr = Expression.parse("(1+ 2 * 3) * (6+1)");
        assertEquals("( 1 + 2 * 3 ) * ( 6 + 1 )", expr.toString());

        // 测试小数解析
        expr = Expression.parse("0.65- 0.56");
        assertEquals("0.65 - 0.56", expr.toString());

        expr = Expression.parse(".65 - .56");
        assertEquals("0.65 - 0.56", expr.toString());

        // 测试负数解析
        expr = Expression.parse("-1 + 2 *  3");
        assertEquals("0 - 1 + 2 * 3", expr.toString());

        expr = Expression.parse("1 + (-2)");
        assertEquals("1 + (-2)", expr.toString());

        expr = Expression.parse("1 + (-2.345)");
        assertEquals("1 + (-2.345)", expr.toString());

        expr = Expression.parse("1 + (-.345)");
        assertEquals("1 + (-0.345)", expr.toString());

        // 测试函数解析
        expr = Expression.parse("1.23456789 * pow(2, 4)");
        assertEquals("1.23456789 * 16", expr.toString());

        expr = Expression.parse("1.23456789 * log(2, 4)");
        assertEquals("1.23456789 * 2.0", expr.toString());

        expr = Expression.parse("pi()");
        assertEquals("3.141592653589793", expr.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidExpression() {
        Expression.parse("1 + 2 * 3 / 6 # 8");
    }

    @Test(expected = UnknownFunctionException.class)
    public void testUnknownFunction() {
        Expression.parse("1 + abc(123, 456)");
    }

    @Test
    public void testToPostfixExpr() {
        Expression expr = Expression.parse("1 + (3 - 2) * 4 / 5");
        assertEquals("1 + ( 3 - 2 ) * 4 / 5", expr.toString());
        assertEquals("1 3 2 - 4 * 5 / +", expr.toPostfixExpr().toString());
    }

}
