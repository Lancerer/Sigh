package com.example.lancer.sigh;

/**
 * Created by Lancer on 2018/6/4.
 */

public class Test {
    @org.junit.Test
    public int getMax(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}
