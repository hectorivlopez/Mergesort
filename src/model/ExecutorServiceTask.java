package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class ExecutorServiceTask implements Callable<int[]> {
    ExecutorService executor;
    private int[] array;
    private int length;
    public ExecutorServiceTask(ExecutorService executor, int[] array, int length) {
        this.executor = executor;
        this.array = array;
        this.length = length;
    }

    @Override
    public int[] call() throws InterruptedException {
        int[] left = Arrays.copyOfRange(array, 0,length / 2);
        int[] right = Arrays.copyOfRange(array, length / 2, length );

        if(this.length > 2) {
            Collection<ExecutorServiceTask> tasks = new ArrayList<>();
            tasks.add(new ExecutorServiceTask(executor, left, left.length));
            tasks.add(new ExecutorServiceTask(executor, right, right.length));
            this.executor.invokeAll(tasks);
        }

        merge(left, right);

        return null;
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
