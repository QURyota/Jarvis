package com.ryota.strategy;

/**
 * @Description TODO
 * @Date 2022/11/7 20:42
 * @Author ryota
 */
public class StrategyDemo {
    public static void main(String[] args) {
        Context add = new Context(new OperationAdd());
        System.out.println(add.executeStrategy(1, 2));

        Context sub = new Context(new OperationSub());
        System.out.println(sub.executeStrategy(1, 2));

        Context mul = new Context(new OperationMul());
        System.out.println(mul.executeStrategy(1, 2));
    }
}
