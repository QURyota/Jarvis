package com.ryota.strategy.demo;

/**
 * @Description TODO
 * @Date 2022/11/7 20:50
 * @Author ryota
 */
public class ByBike implements Travel{
    @Override
    public String byWhat(String str) {
        return str + "出行方式";
    }
}
