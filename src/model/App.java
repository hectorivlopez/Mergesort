package model;

import java.util.Arrays;
import java.util.Random;

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
}
