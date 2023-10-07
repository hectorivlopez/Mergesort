package model;

import java.util.Arrays;

public class MergeSort {
    private final int[] inputArray;
    private final int length;

    public MergeSort(int[] inputArray, int length) {
        this.inputArray = inputArray;
        this.length = length;
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

    public void sort() {
        this.divide(this.inputArray, this.length);
    }
}
