package com.thoughtworks;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 9/26/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleTest {

    private Console console;

    @Before
    public void setUp() throws Exception {
        console = new Console();
    }

    @Test
    public void shouldReturnCorrectRootMethodWhenAcceptAEmptyMethod()
    {
        // given
        String input = "method0";

        // when
        String result = console.parse(input);

        // then
        assertThat(result, is("method0: ROOT\n" +
                "Method(s) number: 1"));
    }

    @Test
    public void shouldReturnCorrectResultWhenAcceptAMethodWhichInvokeOneMethod()
    {
        // given
        String input = "method0{\n" +
                " A.method1();\n" +
                "}";

        // when
        String result = console.parse(input);

        // then
        assertThat(result, is("method0: ROOT\n" +
                ">method1: ROOT -> A\n" +
                "Method(s) number: 2"));
    }

    @Test
    public void shouldReturnCorrectResultWhenAcceptAMethodWhichInvokeTwoMethod()
    {
        // given
        String input = "method0{\n" +
                " A.method1();\n" +
                " B.method2();\n" +
                "}";

        // when
        String result = console.parse(input);

        // then
        assertThat(result, is("method0: ROOT\n" +
                ">method1: ROOT -> A\n" +
                ">method2: ROOT -> B\n" +
                "Method(s) number: 3"));
    }


    @Test
    public  void shouldReturnCorrectResultWhenAcceptAMethodWhichContainDefine()
    {
        // given
        String input = "method0{\n" +
                " A.method1(){\n" +
                "  C.method11()\n" +
                " }\n" +
                " B.method2();\n" +
                "}";

        // when
        String result = console.parse(input);

        // then
        assertThat(result, is("method0: ROOT\n" +
                ">method1: ROOT -> A\n" +
                ">>method11: A -> C\n" +
                ">method2: ROOT -> B\n" +
                "Method(s) number: 4"));
    }



}
