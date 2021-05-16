package com.wolf.common.leetcode.base;

/**
 * @description: 计数排序算法
 * 算法过程：
 * @author: hon.g
 * @create: 2021-05-16 15:33
 * 参考：https://www.cnblogs.com/developerY/p/3166462.html
 **/

import java.util.Arrays;

public class CountSort {

    public static void main(String []args){
        int []arr ={1,4,2,7,9,8,3,6};
        countSort(arr,100);
        System.out.println(Arrays.toString(arr));
    }

    private static int[] countSort(int[] array,int k){
        int[] C=new int[k+1];//构造C数组
        int length=array.length,sum=0;//获取A数组大小用于构造B数组
        int[] B=new int[length];//构造B数组
        for(int i=0;i<length;i++)
        {
            C[array[i]]+=1;// 统计A中各元素个数，存入C数组
        }
        for(int i=0;i<k+1;i++)//修改C数组
        {
            sum+=C[i];
            C[i]=sum;
        }
        for(int i=length-1;i>=0;i--)//遍历A数组，构造B数组
        {

            B[C[array[i]]-1]=array[i];//将A中该元素放到排序后数组B中指定的位置
            C[array[i]]--;//将C中该元素-1，方便存放下一个同样大小的元素

        }
        return B;//将排序好的数组返回，完成排序

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
