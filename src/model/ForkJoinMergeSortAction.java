package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        if (array.length > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks(left, right));
        }

        merge(this.array, left, right);
    }

    private List<ForkJoinMergeSortAction> createSubtasks(int[] left, int[] right) {
        List<ForkJoinMergeSortAction> dividedTasks = new ArrayList<>();

        dividedTasks.add(new ForkJoinMergeSortAction(left, left.length));
        dividedTasks.add(new ForkJoinMergeSortAction(right, right.length));

        return dividedTasks;
    }

    private void merge(int[] array, int[] left, int[] right) {
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

    }
}
