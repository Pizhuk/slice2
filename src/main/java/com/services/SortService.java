package com.services;

import java.util.Arrays;

public class SortService {
    public int[] quickSort(int[] array, int low, int high) {
        if (array == null || high > array.length - 1 || low < 0) {
            return null;
        }
        if (array.length == 0) {
            return array;
        }

        if (low >= high) {
            return array;
        }

        int middle = array[low + (high - low) / 2];

        int i = low;
        int j = high;
        while (i <= j) {
            while (array[i] < middle) {
                i++;
            }

            while (array[j] > middle) {
                j--;
            }

            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        if (low < j) {
            quickSort(array, low, j);
        }

        if (high > i) {
            quickSort(array, i, high);
        }

        return array;
    }
}
