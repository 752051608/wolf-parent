package com.wolf.common.leetcode.base;

/**
 * @description: 希尔排序算法
 * 基本思想
 *  一句话总结：划分多个范围相同的区间，每个子区间自排序，最后合并。
 * 桶排序是计数排序的扩展版本，计数排序可以看成每个桶只存储相同元素，
 * 而桶排序每个桶存储一定范围的元素，通过映射函数，将待排序数组中的元素映射到各个对应的桶中，
 * 对每个桶中的元素进行排序，最后将非空桶中的元素逐个放入原序列中。
 *
 * 桶排序需要尽量保证元素分散均匀，否则当所有数据集中在同一个桶中时，桶排序失效。
 * @author: hon.g
 * @create: 2021-05-16 15:33
 * System.out.println(Arrays.toString(arr));
 **/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，
 * 它也是不稳定排序。首先简单了解下堆结构。
 * 堆是具有以下性质的完全二叉树：
 * 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
 * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
 *
 *
 */
public class BucketSort {

    public static void main(String []args){
        int []arr ={1,4,2,7,9,8,3,6};
        bucketSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bucketSort(int[] arr){

        // 计算最大值与最小值
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        // 计算桶的数量
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for(int i = 0; i < bucketNum; i++){
            bucketArr.add(new ArrayList<Integer>());
        }

        // 将每个元素放入桶
        for(int i = 0; i < arr.length; i++){
            int num = (arr[i] - min) / (arr.length);
            bucketArr.get(num).add(arr[i]);
        }

        // 对每个桶进行排序
        for(int i = 0; i < bucketArr.size(); i++){
            Collections.sort(bucketArr.get(i));
        }

        // 将桶中的元素赋值到原序列
        int index = 0;
        for(int i = 0; i < bucketArr.size(); i++){
            for(int j = 0; j < bucketArr.get(i).size(); j++){
                arr[index++] = bucketArr.get(i).get(j);
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
