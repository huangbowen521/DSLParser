package com.thoughtworks;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 10/7/12
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParserTest {


    @Test
    public void shouldGetTheRestOfPartIntheMethod()
    {
        // given
        String input = "method0{" +
                "A.method1();" +
                "}";

        // when
        String result = Parser.getMethodBody(input);

        // then
        assertThat(result, is("A.method1();"));
    }

    @Test
    public void shouldSplitMethodBody()
    {
        // given
        String input =
                " A.method1(){" +
                        "  C.method11()" +
                        " }" +
                        " B.method2();" +
                        "";

        // when
        String[] result = Parser.splitMethodBody(input);

        // then
        assertThat(result.length, is(2));
        assertThat(result[0],is(" A.method1(){" +
                "  C.method11()" +
                " }"));
        assertThat(result[1],is(" B.method2()"));
    }

    @Test
    public void shouldGetClassAndMethod()
    {
        // given
        String input = "A.method1()";

        // when
        MethodNode result = Parser.getMethodNode(input);

        // then
        assertThat(result.getInvoker(),is("A"));
        assertThat(result.getName(), is("method1"));
    }
}
