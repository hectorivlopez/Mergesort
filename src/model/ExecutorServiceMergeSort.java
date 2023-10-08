package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExecutorServiceMergeSort {
    private final ExecutorService executor;
    private int[] array;
    private final int length;

    public ExecutorServiceMergeSort(int[] array, int length) {
        int processors = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(processors);
        this.array = array;
        this.length = length;
    }

    public void sort() throws InterruptedException {
        divide(this.array, this.length);
    }


    public void divide(int[] array, int length) throws InterruptedException {
        int[] left = Arrays.copyOfRange(array, 0,length / 2);
        int[] right = Arrays.copyOfRange(array, length / 2, length );

        if(length > 2) {
            this.executor.submit(() -> {
                try {
                    divide(left, left.length);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            this.executor.submit(() -> {
                try {
                    divide(right, right.length);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        merge(array, left, right);

    }


    private void merge(int[] array, int[] left, int[] right) {
        System.out.println(Arrays.toString(array));
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            }
            else {
                array[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            array[k] = left[i];
            i++;
            k++;
        }
        while (j < right.length) {
            array[k] = right[j];
            j++;
            k++;
        }
        System.out.println("Sorted: " + Arrays.toString(array));
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getLength() {
        return length;
    }
}
