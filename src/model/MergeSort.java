package model;

import java.util.Arrays;

public class MergeSort {
    private int[] inputArray;

    public MergeSort(int[] inputArray) {
        this.inputArray = inputArray;
    }

    public void merge(int[] array, int[] left, int[] right, int leftLength, int rightLength) {
        int i = 0, j = 0, k = 0;

        while (i < leftLength && j < rightLength) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
                k++;
            }
            else {
                array[k] = right[j];
                j++;
                k++;
            }
        }

        while (i < leftLength) {
            array[k] = left[i];
            i++;
            k++;
        }
        while (j < rightLength) {
            array[k] = right[j];
            j++;
            k++;
        }
    }

    public void divide(int[] array, int length) {
        int[] left = Arrays.copyOfRange(array, 0,length / 2);
        int[] right = Arrays.copyOfRange(array, length / 2, length );

        if(length > 2) {
            this.divide(left, left.length);
            this.divide(right, right.length);
        }

        merge(array, left, right, left.length, right.length);
    }

    public long sort() {
        long initTime = System.currentTimeMillis();
        this.divide(this.inputArray, this.inputArray.length);
        long endTime = System.currentTimeMillis();

        return endTime - initTime;
    }
}
