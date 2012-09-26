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
public class parserTest {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Parser();
    }

    @Test
    public void shouldReturnCorrectRootMethodWhenAcceptAEmptyMethod()
    {
        // given
        String input = "method0";

        // when
        String result = parser.parse(input);

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
        String result = parser.parse(input);

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
        String result = parser.parse(input);

        // then
        assertThat(result, is("method0: ROOT\n" +
                ">method1: ROOT -> A\n" +
                ">method2: ROOT -> B\n" +
                "Method(s) number: 3"));
    }



    @Test
    public void shouldGetTheRestOfPartIntheMethod()
    {
          // given
        String input = "method0{" +
                "A.method1();" +
                "}";

        // when
        String result = parser.getBody(input);

        // then
        assertThat(result, is("A.method1();"));
    }

    @Test
    public void shouldSplitMethodBody()
    {
        // given
        String input =
                "A.method1();" +
                "B.method2();";

        // when
        String[] result = parser.splitBody(input);

        // then
        assertThat(result.length, is(2));
        assertThat(result[0],is("A.method1()"));
        assertThat(result[1],is("B.method2()"));
    }

    @Test
    public void shouldGetClassAndMethod()
    {
        // given
        String input = "A.method1()";

        // when
        String[] result = parser.splitToGetClass(input);

        // then
        assertThat(result.length,is(2));
              assertThat(result[0],is("A"));
        assertThat(result[1], is("method1"));
    }

}
