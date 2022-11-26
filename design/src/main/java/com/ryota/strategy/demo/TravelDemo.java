package com.ryota.strategy.demo;

/**
 * @Description TODO
 * @Date 2022/11/7 20:52
 * @Author ryota
 */
public class TravelDemo {
    public static void main(String[] args) {
        TravelContext bike = new TravelContext(new ByBike());
        System.out.println(bike.going("自行车"));
        TravelContext car = new TravelContext(new ByCar());
        System.out.println(car.going("小汽车"));
    }
}
