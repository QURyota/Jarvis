package com.ryota.strategy;

import java.util.ArrayList;

/**
 * @Description TODO
 * @Date 2022/11/12 16:46
 * @Author ryota
 */
public class Test
{
    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        String[] strings1 = strings.toArray(new String[strings.size()]);
        System.out.println(strings1[0]);
    }
}
