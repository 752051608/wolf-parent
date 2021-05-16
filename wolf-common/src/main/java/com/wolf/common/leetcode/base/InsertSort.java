package com.wolf.common.leetcode.base;

/**
 * @description: 插入排序算法
 * 思想：依次遍历,从后往前，比前面还小,进行交换
 * 遍历--找到最小下标---交换---打印数据
 * @author: hon.g
 * @create: 2021-05-16 15:33
 **/

public class InsertSort {


    public static void main(String[] args) {
        int[] a = {2, 3, 6, 1, 8, 7, 10, 5, 4};
        sort(a);
        print(a);
    }
    private static void sort(int [] a){
        for (int i=1;i<a.length;i++) {
            for(int j=i;j>0;j--){
                if (a[j]<a[j-1]) {
                    swap(a,j,j-1);
                }
            }
        }

    }

    private static void print(int[] arr) {
        for (int k=0;k<arr.length;k++) {
            System.out.print(arr[k]);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
