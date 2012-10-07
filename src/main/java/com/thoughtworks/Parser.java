package com.thoughtworks;

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
        input = PrepareData(input);
        String result = parseMethod(input, null);
        result = outputMethodsCount(result);
        return result;
    }

    private String parseMethod(String input, MethodNode node) {
        String[] classAndMethods1 = splitToGetClass(input);
        String result11 = "";
        if (node == null)
            result11 += String.format("%s: %s\n", classAndMethods1[1], classAndMethods1[0]);
        else {
            result11 += String.format(">%s: %s -> %s\n", classAndMethods1[1], node.getInvoker(), classAndMethods1[0]);
        }
        methodCount++;
        String result = result11;
        String body = getBody(input);
        MethodNode currentNode;
        if (node == null)
        {
            currentNode = new MethodNode(classAndMethods1[0], classAndMethods1[1], body,0);
        }
        else
        {
            currentNode = new MethodNode(classAndMethods1[0], classAndMethods1[1], body,node.getInvokeLevel()+1);

        }
            String[] statements = splitBody(body);
        for (int i = 0; i < statements.length; i++) {
            if (statements[i] != "") {

                if (!statements[i].contains("{")) {

                    String[] classAndMethods = splitToGetClass(statements[i]);
                    assert classAndMethods.length == 2;
                        methodCount++;
                        String invokeFlag = ">";
                        for (int j = 0; j < currentNode.getInvokeLevel(); j++) {
                            invokeFlag += ">";
                        }
                        result += String.format("%s%s: %s -> %s\n", invokeFlag, classAndMethods[1], currentNode.getInvoker(), classAndMethods[0]);

                } else {
                    result += parseMethod(statements[i], currentNode);
                }

            }
        }

        return result;
    }

    private String outputMethodsCount(String result) {
        result += String.format("Method(s) number: %d", methodCount);
        return result;
    }

    private String setRootName(String input) {
        String root = getRootMethod(input);
        String result = "";
        result += String.format("%s: ROOT\n", root);
        methodCount++;
        return result;
    }

    private String PrepareData(String input) {
        input = input.replace("\n", "");
        input = input.replace(" ", "");
        return input;
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
        Pattern pattern = Pattern.compile("[;\\}]+");
        String[] result = pattern.split(input);
        for (int i = 0; i < result.length; i++) {
            if (result[i].contains("{")) {
                result[i] += "}";
            }
        }
        return result;
    }

    public String[] splitToGetClass(String input) {
        input = input.replace("()", "");
        input = getRootMethod(input);
        String[] result;
        if (input.contains(".")) {
            Pattern pattern = Pattern.compile("[\\.]+");
            result = pattern.split(input);
        } else {
            result = new String[2];
            result[0] = "ROOT";
            result[1] = input;

        }


        return result;
    }
}
