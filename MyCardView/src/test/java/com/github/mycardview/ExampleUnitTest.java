package com.github.mycardview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public class Person {
        final String sex;
        public Person() {
            this.sex = "男";
        }
    }


    @Test
    public void asddf() {
        List<String> list=new ArrayList<>();
        list.add("*.iml");
        list.add("*.apk");
        list.add(".gradle/");
        list.add(".idea/");
        list.add("app/*.iml");
        list.add("build/");
        list.add("gradle/");
        list.add("mvp/build/");
        list.add("mvp/*.iml");
        list.add("RecyclerBanner/build/");
        list.add("RecyclerBanner/*.iml");
        list.add("service/build/");
        list.add("service/*.iml");
        list.add("umlibrary/build/");
        list.add("umlibrary/*.iml");
        list.add("v2/build/");
        list.add("v2/*.iml");
        list.add("v2/*.apk");
        list.add("zssqservice/build/");
        list.add("zssqservice/*.iml");
        list.add("local.properties");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("<ignored path=\""+list.get(i)+"\" />");
        }
    }
    @Test
    public void asdf() {
        String a="ab";
        String b="a"+"b";
        String c=new String("ab");
        String cd=new String("ab");
        String aa="a";
        String d=aa+"b";
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(aa==d);
        System.out.println(cd==c);
        System.out.println(cd.equals(c));
    }
}