package com.wolf.common.leetcode.base;

/**
 * @description: 归并排序算法
 * 思想：归并 递归 (左右排序,合并时左右比较后,放入新数组中,最后将新数组数据放入老数组)
 * java中Arrays.sort()采用了一种名为TimSort的排序算法，就是归并排序的优化版本
 * 归并排序的最好，最坏，平均时间复杂度均为O(nlogn)。
 * @author: hon.g
 * @create: 2021-05-16 15:33
 **/

public class MergeSort {

    public static void main(String[] args) {
        int[] a = {1, 4, 6, 7, 2, 3, 5, 8, 9};
        //在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        int[] temp = new int[a.length];
        sort(a, 0, a.length - 1, temp);
        print(a);
    }

    private static void sort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            //左边归并排序，使得左子序列有序
            sort(arr, left, mid, temp);
            //右边归并排序，使得右子序列有序
            sort(arr, mid + 1, right, temp);
            //将两个有序子数组合并操作
            merge(arr, left, mid, right, temp);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        //左序列指针  右序列指针  临时数组指针
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        //将左边剩余元素填充进temp中
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        //将右序列剩余元素填充进temp中
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        k = 0;
        //将temp中的元素全部拷贝到原数组中
        while (left <= right) {
            arr[left++] = temp[k++];
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
