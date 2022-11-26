package com.ryota.strategy;

/**
 * @Description TODO
 * @Date 2022/11/7 20:40
 * @Author ryota
 */
public class OperationSub implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}
