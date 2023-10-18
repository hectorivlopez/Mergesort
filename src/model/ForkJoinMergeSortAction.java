package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;


public class ForkJoinMergeSortAction extends RecursiveAction {

    private final int[] array;
    private final int length;
    private static final int THRESHOLD = 2;


    public ForkJoinMergeSortAction(int[] array, int length) {

        this.array = array;
        this.length = length;
    }

    @Override
    protected void compute() {
        int[] left = Arrays.copyOfRange(this.array, 0,this.length / 2);
        int[] right = Arrays.copyOfRange(this.array, this.length / 2, this.length );

        ForkJoinMergeSortAction leftTask = new ForkJoinMergeSortAction( left, left.length);
        ForkJoinMergeSortAction rightTask = new ForkJoinMergeSortAction(right, right.length);

        if (this.length > THRESHOLD) {
            invokeAll(leftTask, rightTask);
        }

        merge(left, right);
    }

    private void merge(int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                this.array[k] = left[i];
                i++;
            }
            else {
                this.array[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            this.array[k] = left[i];
            i++;
            k++;
        }
        while (j < right.length) {
            this.array[k] = right[j];
            j++;
            k++;
        }

    }
}
