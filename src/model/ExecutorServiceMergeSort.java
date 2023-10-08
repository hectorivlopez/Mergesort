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
        this.executor = Executors.newCachedThreadPool();
        this.array = array;
        this.length = length;
    }

    public void sort() throws InterruptedException {
        Collection<ExecutorServiceTask> tasks = new ArrayList<>();
        tasks.add(new ExecutorServiceTask(this.executor, this.array, this.length));
        this.executor.invokeAll(tasks);
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
