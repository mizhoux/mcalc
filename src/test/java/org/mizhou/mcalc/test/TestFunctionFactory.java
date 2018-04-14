package org.mizhou.mcalc.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mizhou.mcalc.function.FunctionFactory;
import org.mizhou.mcalc.token.Num;
import org.mizhou.mcalc.function.Function;

public class TestFunctionFactory {

    @Test
    public void testGetFunction() {
        Function func = FunctionFactory.getFunction("log");
        Num result = func.apply(new Num[]{new Num("2"), new Num("16")});
        assertEquals("4.0", result.text());
        
        try {
            func.apply(new Num[]{new Num("2")});
            fail();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            func.apply(new Num[]{new Num("2"), new Num("3"), new Num("4")});
            fail();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }

        func = FunctionFactory.getFunction("pow");
        result = func.apply(new Num[]{new Num("2"), new Num("4")});
        assertEquals("16", result.text());

        try {
            func.apply(new Num[]{new Num("2")});
            fail();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            func.apply(new Num[]{new Num("2"), new Num("3"), new Num("4")});
            fail();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        
        func = FunctionFactory.getFunction("pi");
        result = func.apply(new Num[0]);
        assertEquals("3.141592653589793", result.text());
        
        try {
            func.apply(new Num[]{new Num("2")});
            fail();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        
        func = FunctionFactory.getFunction("abc");
        assertNull(func);
    }
}
