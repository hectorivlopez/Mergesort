package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static int[] inputArray;
    public static Boolean fastMode = false;

    public static void generateArray(int length) {
        Random random = new Random();
        inputArray  = new int[length];
        for(int i = 0; i < length; i++) {
            inputArray [i] = random.nextInt(100 - 1 + 1) + 1;
        }
    }

    public static long mergeSort(int[] array) {
        MergeSort mergeSort = new MergeSort(array, array.length);
        long initTime = System.currentTimeMillis();
        mergeSort.sort();
        long endTime = System.currentTimeMillis();

        return endTime - initTime;
    }

    public static long forkJoinMergeSort(int[] array) {
        ForkJoinMergeSortAction mergeSort = new ForkJoinMergeSortAction(array, array.length);
        long initTime = System.currentTimeMillis();
        mergeSort.invoke();
        long endTime = System.currentTimeMillis();

        return endTime - initTime;
    }

    public static long executorServiceMergeSort(int[] array) throws InterruptedException, ExecutionException {
        ExecutorServiceMergeSort mergeSort = new ExecutorServiceMergeSort(array, array.length);
        long initTime = System.currentTimeMillis();
        mergeSort.sort();
        long endTime = System.currentTimeMillis();

        Thread.sleep(1000);
        mergeSort.getExecutor().shutdown();
        mergeSort.getExecutor().awaitTermination(1000, TimeUnit.MILLISECONDS);

        return endTime - initTime;
    }
}
