package com.thoughtworks;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 10/7/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodNode {
    private final String invoker;
    private final String name;
    private int invokeLevel;

    public MethodNode(String invoker, String name) {

        this.invoker = invoker;
        this.name = name;
        this.invokeLevel = invokeLevel;
    }

    public String getInvoker() {
        return invoker;
    }

    public int getInvokeLevel() {
        return invokeLevel;
    }

    public String getName() {
        return name;
    }
}
