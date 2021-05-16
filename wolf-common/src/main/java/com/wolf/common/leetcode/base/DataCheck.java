package com.wolf.common.leetcode.base;

import java.util.Arrays;
import java.util.Random;

/**
 * 选泡插，快归堆希统计基。
 * 恩方恩老恩一三，对恩加k恩乘k。
 * 不稳稳稳不稳稳，不稳不稳稳稳稳。
 *
 * @description: 数据校验
 * @author: hon.g
 * @create: 2021-05-16 17:05
 *
 * https://www.cnblogs.com/chengxiao/p/6129630.html
 *
 **/

public class DataCheck {

    static int[] generateRandomArray(){
        int a[] = new int[1000];
        Random random = new Random();
        for (int i=0;i<a.length;i++){
            a[i]=random.nextInt(1000);
        }
        return a;
    }

    static void check(){
        int arr[] = generateRandomArray();
        int arr2[] = new int[arr.length];
        System.arraycopy(arr,0,arr2,0,arr.length);
        Arrays.sort(arr);
        QuickSort.sort(arr2,0,arr2.length-1);
        boolean same= true;
        for(int i=0;i<arr2.length-1;i++){
            if (arr[i]!= arr2[i]){
                same =false;
            }
        }
        System.out.println(same==true?"right":"wrong");
    }

    public static void main(String[] args) {
        check();
    }
}
