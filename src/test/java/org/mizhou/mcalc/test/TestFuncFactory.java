package org.mizhou.mcalc.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mizhou.mcalc.func.Func;
import org.mizhou.mcalc.func.FuncFactory;
import org.mizhou.mcalc.token.Num;

public class TestFuncFactory {

    @Test
    public void testGetFunc() {
        Func func = FuncFactory.getFunc("log");
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

        func = FuncFactory.getFunc("pow");
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
        
        func = FuncFactory.getFunc("pi");
        result = func.apply(new Num[0]);
        assertEquals("3.141592653589793", result.text());
        
        try {
            func.apply(new Num[]{new Num("2")});
            fail();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        
        func = FuncFactory.getFunc("abc");
        assertNull(func);
    }
}
