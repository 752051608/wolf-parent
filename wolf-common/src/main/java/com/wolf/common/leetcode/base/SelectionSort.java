package com.wolf.common.leetcode.base;

/**
 * @description: 选择排序算法
 * 思想：依次遍历,找到最小的,进行交换
 * 遍历--找到最小下标---交换---打印数据
 * @author: hon.g
 * @create: 2021-05-16 15:33
 **/

public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {2,3,6,1,8,7,10,5,4};

        for (int i=0;i<arr.length-1;i++) {
            int minPos=i;
            //找到最小
            for(int j=i+1;j<arr.length;j++){
                minPos = arr[j]<arr[minPos]?j:minPos;
            }
            System.out.println("minPos:"+minPos);
            swap(arr, i, minPos);
            System.out.println("经过几次交换i=:"+i);
            print(arr);
        }
    }

    private static void print(int[] arr) {
        for (int k=0;k<arr.length;k++) {
            System.out.print(arr[k]);
        }
    }

    private static void swap(int[] arr, int i, int minPos) {
        int temp = arr[i];
        arr[i] = arr[minPos];
        arr[minPos] = temp;
    }
}
