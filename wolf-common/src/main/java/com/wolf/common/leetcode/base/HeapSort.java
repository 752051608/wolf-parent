package com.wolf.common.leetcode.base;

import java.util.Arrays;

/**
 * @description: 归并排序算法
 * 思想：堆排序是一种选择排序，整体主要由构建初始堆+交换堆顶元素和末尾元素并重建堆两部分组成。
 * 其中构建初始堆经推导复杂度为O(n)，
 * 在交换并重建堆的过程中，需交换n-1次，
 * 而重建堆的过程中，根据完全二叉树的性质，[log2(n-1),log2(n-2)...1]逐步递减，近似为nlogn。
 * 所以堆排序时间复杂度一般认为就是O(nlogn)级。
 * @author: hon.g
 * @create: 2021-05-16 15:33
 * System.out.println(Arrays.toString(arr));
 **/

/**
 * 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，
 * 它也是不稳定排序。首先简单了解下堆结构。
 * 堆是具有以下性质的完全二叉树：
 * 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
 * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
 *
 * 再简单总结下堆排序的基本思路：
 *
 * 　　a.将无需序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆;
 *
 * 　　b.将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
 *
 * 　　c.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
 */
public class HeapSort {

    public static void main(String []args){
        int []arr = {9,8,7,6,5,4,3,2,1};
        sort(arr);
        print(arr);
    }
    public static void sort(int []arr){
        //1.构建大顶堆
        for(int i=arr.length/2-1;i>=0;i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr,i,arr.length);
        }
        //2.调整堆结构+交换堆顶元素与末尾元素
        for(int j=arr.length-1;j>0;j--){
            //将堆顶元素与末尾元素进行交换
            swap(arr,0,j);
            //重新对堆进行调整
            adjustHeap(arr,0,j);
        }

    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     * @param arr
     * @param i
     * @param length
     */
    public static void adjustHeap(int []arr,int i,int length){
        //先取出当前元素i
        int temp = arr[i];
        //从i结点的左子结点开始，也就是2i+1处开始
        for(int k=i*2+1;k<length;k=k*2+1){
            //如果左子结点小于右子结点，k指向右子结点
            if(k+1<length && arr[k]<arr[k+1]){
                k++;
            }
            //如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
            if(arr[k] >temp){
                arr[i] = arr[k];
                i = k;
            }else{
                break;
            }
        }
        //将temp值放到最终的位置
        arr[i] = temp;
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
