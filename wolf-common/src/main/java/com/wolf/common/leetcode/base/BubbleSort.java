package com.wolf.common.leetcode.base;

/**
 * @description: 冒泡排序
 * 算法思想：依次遍历，两两比较,把最大的放在后面
 * @author: hon.g
 * @create: 2021-05-16 16:10
 **/

public class BubbleSort {


    public static void main(String[] args) {
        int[] a = {2, 3, 6, 1, 8, 7, 10, 5, 4};
        sort(a);
        print(a);
    }
    private static void sort(int [] a){
        for (int i=a.length-1;i>0;i--) {
            finMax(a, i);
        }

    }

    private static void finMax(int[] a, int i) {
        for(int j=0;j<i;j++){
            if(a[j]>a[j+1]){
                swap(a,j,j+1);
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
