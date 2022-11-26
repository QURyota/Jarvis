package com.ryota.strategy;

/**
 * @Description TODO
 * @Date 2022/11/7 20:41
 * @Author ryota
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}
