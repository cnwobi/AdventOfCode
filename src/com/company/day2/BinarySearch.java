package com.company.day2;

public class BinarySearch {

    public static int search(int[] arr, int element){
        int start = 0;
        int end = arr.length-1;
        return search(arr, element, start, end);
    }

    public static int search (int[] arr, int element, int start, int end){
        if(start > end){
            return -1;
        }
        int mid = (end - start)/ 2 + start;
        if(arr[mid] == element){
            return mid;
        }
        if(arr[mid] > element){
            return search(arr,element,start,mid - 1);
        }

            return search(arr,element, mid+1, end);

    }

    public static void main(String[] args) {
        int[] a = {2,4,5,90,100,120,140,250};
        System.out.println(search(a,91));
    }
}
