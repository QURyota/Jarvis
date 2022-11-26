package com.ryota.strategy.demo;

/**
 * @Description TODO
 * @Date 2022/11/7 20:51
 * @Author ryota
 */
public class TravelContext {
    private Travel travel;

    public TravelContext(Travel travel) {
        this.travel = travel;
    }

    public String going(String str) {
        return travel.byWhat(str);
    }
}
