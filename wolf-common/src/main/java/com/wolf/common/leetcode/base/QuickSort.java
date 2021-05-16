package com.wolf.common.leetcode.base;

/**
 * @description: 快速排序算法
 * 思想：选准轴pivot位置,小于该轴值的放左边，大于该轴值得放右边
 * 形成两个子表，按照上面规则继续遍历，直至最后一个元素
 * @author: hon.g
 * @create: 2021-05-16 15:33
 **/

public class QuickSort {

    public static void main(String[] args) {
        int[] a = {2, 7, 3, 6, 1, 6, 8, 7, 10, 5, 4};
        sort(a, 0, a.length - 1);
        print(a);
    }

    public static void sort(int[] a, int leftBound, int rightBound) {
        if (leftBound >= rightBound) {
            return;
        }
        int mid = partition(a, leftBound, rightBound);
        sort(a, leftBound, mid - 1);
        sort(a, mid + 1, rightBound);
    }

    private static int partition(int[] a, int leftBound, int rightBound) {
        int pivot = a[rightBound];
        int left = leftBound;
        int right = rightBound - 1;
        while (left <= right) {
            while (left <= right && a[left] <= pivot) {
                left++;
            }
            while (left <= right && a[right] > pivot) {
                right--;
            }
            if (left < right) {
                swap(a, left, right);
            }
        }
        swap(a, left, rightBound);
        return left;
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
