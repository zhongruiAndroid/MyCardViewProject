package com.test.mycardview;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void asf() {
        String a="1f";
        System.out.println( Float.parseFloat("1.1F"));
        float l = Float.parseFloat(a);
        System.out.println(l);
    }
}