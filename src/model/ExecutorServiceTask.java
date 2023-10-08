package model;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class ExecutorServiceTask implements Callable<int[]> {
    private int[] array;
    private int lenght;
    public ExecutorServiceTask(int[] array, int length) {
        this.array = array;
        this.lenght = length;
    }

    @Override
    public int[] call() {
        MergeSort mergeSort = new MergeSort(this.array, this.lenght);
        mergeSort.sort();
        return null;
    }
}
