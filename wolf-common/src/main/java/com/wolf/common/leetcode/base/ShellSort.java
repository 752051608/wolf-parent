package com.wolf.common.leetcode.base;

/**
 * @description: 希尔排序算法
 * 基本思想
 *  希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；
 *  随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
 *
 * 希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序，
 * 同时该算法是冲破O(n2）的第一批算法之一。

 * @author: hon.g
 * @create: 2021-05-16 15:33
 * System.out.println(Arrays.toString(arr));
 **/

import java.util.Arrays;
public class ShellSort {

    public static void main(String []args){
        int []arr ={1,4,2,7,9,8,3,6};
        sort(arr);
        print(arr);
        int []arr1 ={1,4,2,7,9,8,3,6};
        sort1(arr1);
        System.out.println(Arrays.toString(arr1));
    }

    /**
     * 希尔排序 针对有序序列在插入时采用交换法
     * @param arr
     */
    public static void sort(int []arr){
        //增量gap，并逐步缩小增量
        for(int gap=arr.length/2;gap>0;gap/=2){
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for(int i=gap;i<arr.length;i++){
                int j = i;
                while(j-gap>=0 && arr[j]<arr[j-gap]){
                    //插入排序采用交换法
                    swap(arr,j,j-gap);
                    j-=gap;
                }
            }
        }
    }

    /**
     * 希尔排序 针对有序序列在插入时采用移动法。
     * @param arr
     */
    public static void sort1(int []arr){
        //增量gap，并逐步缩小增量
        for(int gap=arr.length/2;gap>0;gap/=2){
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for(int i=gap;i<arr.length;i++){
                int j = i;
                int temp = arr[j];
                if(arr[j]<arr[j-gap]){
                    while(j-gap>=0 && temp<arr[j-gap]){
                        //移动法
                        arr[j] = arr[j-gap];
                        j-=gap;
                    }
                    arr[j] = temp;
                }
            }
        }
    }

    private static void print(int[] arr) {
        for (int k = 0; k < arr.length; k++) {
            System.out.print(arr[k]);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
