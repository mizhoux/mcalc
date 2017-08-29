package org.mizhou.mcalc.token;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 数（整数或者小数）
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class Num extends AbstractToken implements Token {

    public static final RoundingMode MODE
            = RoundingMode.HALF_UP;  // 默认对末尾小数采用 四舍五入

    public static final MathContext MATH_CONTEXT
            = new MathContext(6, MODE); // 无限循环时保留6位有效数字，末位四舍五入

    private final BigDecimal value;

    public Num(BigDecimal value) {
        this.value = value;
    }

    public Num(String value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal value() {
        return value;
    }

    public Num operate(Operator op, Num other) {
        BigDecimal result = null;

        switch (op.value()) {
            case '+':
                result = value.add(other.value);
                break;
            case '-':
                result = value.subtract(other.value);
                break;
            case '*':
                result = value.multiply(other.value);
                break;
            case '/':
                result = value.divide(other.value, MATH_CONTEXT);
                break;
        }

        if (result == null) {
            throw new RuntimeException(String.format(
                    "operate 方法出错：%s %s %s", value, op.text(), other.value));
        }

        return new Num(result);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public TokenType getType() {
        return TokenType.NUMBER;
    }

    @Override
    public String text() {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            return '(' + value.toString() + ')';
        } else {
            return value.toString();
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
