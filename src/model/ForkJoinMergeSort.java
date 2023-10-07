package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class ForkJoinMergeSort extends RecursiveTask<int[]> {
    private int[] array;
    private int length;
    private int[] left;
    private int[] right;


    private static final int THRESHOLD = 2;

    public ForkJoinMergeSort(int[] array, int length) {
        this.array = array;
        this.length = length;

        this.left = Arrays.copyOfRange(array, 0,length / 2);
        this.right = Arrays.copyOfRange(array, length / 2, length );
    }

    protected int[] compute() {
        if (array.length > THRESHOLD) {
            Collection<ForkJoinMergeSort> subtasks = ForkJoinTask.invokeAll(createSubtasks());
            Stream<int[]> arrays = subtasks.stream().map(ForkJoinTask::join);
            this.array = arrays.flatMapToInt(Arrays::stream).toArray();

            this.left = Arrays.copyOfRange(array, 0,length / 2);
            this.right = Arrays.copyOfRange(array, length / 2, length );
        }
            return merge();
    }

    private Collection<ForkJoinMergeSort> createSubtasks() {
        List<ForkJoinMergeSort> dividedTasks = new ArrayList<>();

        dividedTasks.add(new ForkJoinMergeSort(left, left.length));
        dividedTasks.add(new ForkJoinMergeSort(right, right.length));

        return dividedTasks;
    }

    private int[] merge() {
        int i = 0, j = 0, k = 0;

        while (i < this.left.length && j < this.right.length) {
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

        while (i < this.left.length) {
            array[k] = left[i];
            i++;
            k++;
        }
        while (j < this.right.length) {
            array[k] = right[j];
            j++;
            k++;
        }
        return this.array;
    }
}
