package com.thoughtworks;

import sun.misc.Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 9/26/12
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {
    private int methodCount;

    public String parse(String input) {
        String result = "";
        input = input.replace("\n", "");
        input = input.replace(" ", "");
        String root = getRootMethod(input);
        result += String.format("%s: ROOT\n", root);
        methodCount++;
        String body = getBody(input);
        String[] statements = splitBody(body);
        for (int i = 0; i < statements.length; i++) {
            String[] classAndMethods = splitToGetClass(statements[i]);
            if (classAndMethods.length == 2) {
                methodCount++;
                result += String.format(">%s: ROOT -> %s\n", classAndMethods[1], classAndMethods[0]);
            }
        }

        result += String.format("Method(s) number: %d", methodCount);
        return result;
    }

    private String getRootMethod(String input) {

        Pattern pattern = Pattern.compile("[^\\{]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(0) : "";
    }

    public String getBody(String input) {
        Pattern pattern = Pattern.compile("(?<=\\{).+(?<!\\})");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(0) : "";
    }

    public String[] splitBody(String input) {
        Pattern pattern = Pattern.compile("[;]+");
        String[] result = pattern.split(input);
        return result;
    }

    public String[] splitToGetClass(String input) {
        input = input.replace("()", "");
        Pattern pattern = Pattern.compile("[\\.]+");
        String[] result = pattern.split(input);

        return result;
    }
}
