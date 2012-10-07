package com.thoughtworks;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 9/26/12
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Console {
    private int methodCount;

    public String parse(String input) {
        methodCount = 0;
        input = preProcess(input);
        String result = parseMethod(input, null, 0);
        result = outputMethodsCount(result);
        return result;
    }

    private String parseMethod(String input, MethodNode invokeNode, Integer invokeLevel) {
        MethodNode currentNode = Parser.getMethodNode(input);
        String result = getMethodInvokeString(invokeNode, invokeLevel, currentNode);

        result += processMethodBody(input, invokeLevel, currentNode);

        return result;
    }

    private String processMethodBody(String input, Integer invokeLevel, MethodNode currentNode) {
        String body = Parser.getMethodBody(input);
        String[] statements = Parser.splitMethodBody(body);
        if (statements.length > 0) {
            invokeLevel++;
        }
        String result = "";
        for (int i = 0; i < statements.length; i++) {
            result += parseMethod(statements[i], currentNode, invokeLevel);
        }
        return result;
    }

    private String getMethodInvokeString(MethodNode node, Integer invokeLevel, MethodNode currentNode) {
        methodCount++;
        if (node == null)
            return String.format("%s: %s\n", currentNode.getName(), currentNode.getInvoker());
        else {
            String invokeFlag = "";
            for (int j = 0; j < invokeLevel; j++) {
                invokeFlag += ">";
            }
            return String.format("%s%s: %s -> %s\n", invokeFlag, currentNode.getName(), node.getInvoker(), currentNode.getInvoker());
        }
    }

    private String outputMethodsCount(String result) {
        result += String.format("Method(s) number: %d", methodCount);
        return result;
    }

    private String preProcess(String input) {
        input = input.replace("\n", "");
        input = input.replace(" ", "");
        return input;
    }

}
