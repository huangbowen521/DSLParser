package com.thoughtworks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 10/7/12
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    private static String getMethodHead(String input) {

        Pattern pattern = Pattern.compile("[^\\{]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(0) : "";
    }

    public static MethodNode getMethodNode(String input) {
        input = input.replace("()", "");
        input = getMethodHead(input);
        String[] result;
        if (input.contains(".")) {
            Pattern pattern = Pattern.compile("[\\.]+");
            result = pattern.split(input);
        } else {
            result = new String[2];
            result[0] = "ROOT";
            result[1] = input;
        }

        return new MethodNode(result[0],result[1]);
    }

    public static String getMethodBody(String input) {
        Pattern pattern = Pattern.compile("(?<=\\{).+(?<!\\})");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(0) : "";
    }

    public static String[] splitMethodBody(String input) {
        Pattern pattern = Pattern.compile("[;\\}]+");
        String[] result = pattern.split(input);

        List<String> resultList = new ArrayList<String>();

        for (int i = 0; i < result.length; i++) {
            if (result[i].contains("{")) {
                result[i] += "}";
            }

            if (result[i] != "")
            {
            resultList.add(result[i]);
            }
        }

        return resultList.toArray(new String[resultList.size()]);
    }
}
