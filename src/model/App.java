package model;

import controller.WindowController;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.*;

public class App {
    public static int[] inputArray;
    public static ForkJoinPool forkJoinPool = new ForkJoinPool();
    public static ExecutorService executor = Executors.newFixedThreadPool(6);
    public static Boolean fastMode = false;
    private static final Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateArray(int length) {
        Random random = new Random();
        inputArray  = new int[length];
        for(int i = 0; i < length; i++) {
            inputArray [i] = random.nextInt(100 - 1 + 1) + 1;
        }
    }

    public static long mergeSort(int[] array) {
        MergeSort mergeSort = new MergeSort(array, array.length);
        long initTime = System.nanoTime();
        mergeSort.sort();
        long endTime = System.nanoTime();

        return endTime - initTime;
    }

    public static long forkJoinMergeSort(int[] array) {
        ForkJoinMergeSortAction mergeSort = new ForkJoinMergeSortAction(array, array.length);
        long initTime = System.nanoTime();
        ForkJoinPool.commonPool().invoke(mergeSort);
        long endTime = System.nanoTime();

        return endTime - initTime;
    }

    public static long executorServiceMergeSort(int[] array) throws InterruptedException, ExecutionException {
        ExecutorServiceMergeSort mergeSort = new ExecutorServiceMergeSort(array, array.length);
        long initTime = System.nanoTime();
        mergeSort.sort();
        long endTime = System.nanoTime();

        return endTime - initTime;
    }

    public static void warmUpThreadPools() throws ExecutionException, InterruptedException {
        generateArray(100000);
        mergeSort(inputArray);
        for(int i = 0; i < 100; i++) {
            generateArray(100000);
            forkJoinMergeSort(inputArray);
        }
        for(int i = 0; i < 4; i++) {
            generateArray(100000);
            executorServiceMergeSort(inputArray);
        }

        //calculateMedias();
    }

    private static void calculateMedias() throws ExecutionException, InterruptedException {
        long merges = 0;
        long forks = 0;
        long execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(5);
            merges += mergeSort(inputArray);
            generateArray(5);
            forks += forkJoinMergeSort(inputArray);
            generateArray(5);
            execs += executorServiceMergeSort(inputArray);
        }

        long mergesProm = merges / 100;
        long forskProm = forks / 100;
        long execsProm = execs / 100;

        System.out.println("-------------------------- 5 --------------------------");
        long[] proms = {mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }


        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(10);
            merges += mergeSort(inputArray);
            generateArray(10);
            forks += forkJoinMergeSort(inputArray);
            generateArray(10);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------- 10 --------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(50);
            merges += mergeSort(inputArray);
            generateArray(50);
            forks += forkJoinMergeSort(inputArray);
            generateArray(50);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------- 50 --------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }


        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(100);
            merges += mergeSort(inputArray);
            generateArray(100);
            forks += forkJoinMergeSort(inputArray);
            generateArray(100);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------- 100 -------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(500);
            merges += mergeSort(inputArray);
            generateArray(500);
            forks += forkJoinMergeSort(inputArray);
            generateArray(500);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------- 500 -------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(1000);
            merges += mergeSort(inputArray);
            generateArray(1000);
            forks += forkJoinMergeSort(inputArray);
            generateArray(1000);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------ 1 000 ------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(5000);
            merges += mergeSort(inputArray);
            generateArray(5000);
            forks += forkJoinMergeSort(inputArray);
            generateArray(5000);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------ 5 000 ------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(10000);
            merges += mergeSort(inputArray);
            generateArray(10000);
            forks += forkJoinMergeSort(inputArray);
            generateArray(10000);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------ 10 000 ------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(50000);
            merges += mergeSort(inputArray);
            generateArray(50000);
            forks += forkJoinMergeSort(inputArray);
            generateArray(50000);
            execs += executorServiceMergeSort(inputArray);
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("------------------------ 50 000 ------------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        /*merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(100000);
            merges += mergeSort(inputArray);
            generateArray(100000);
            forks += forkJoinMergeSort(inputArray)();
            generateArray(100000);
            execs += executorServiceMergeSort(inputArray)();
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("----------------------- 100 000 -----------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(500000);
            merges += mergeSort(inputArray);
            generateArray(500000);
            forks += forkJoinMergeSort(inputArray)();
            generateArray(500000);
            execs += executorServiceMergeSort(inputArray)();
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("----------------------- 500 000 -----------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }

        merges = 0;
        forks = 0;
        execs = 0;
        for(int i = 0; i < 100; i++) {
            generateArray(1000000);
            merges += mergeSort(inputArray);
            generateArray(1000000);
            forks += forkJoinMergeSort(inputArray)();
            generateArray(1000000);
            execs += executorServiceMergeSort(inputArray)();
        }

        mergesProm = merges / 100;
        forskProm = forks / 100;
        execsProm = execs / 100;

        System.out.println("---------------------- 1 000 000 ----------------------");
        proms = new long[]{mergesProm, forskProm, execsProm};
        Arrays.sort(proms);
        for(int i = 0; i < 3; i++) {
            if(proms[i] == mergesProm) {
                System.out.println((i + 1) + "º Merge: " + mergesProm + " µs");
            }
            if(proms[i] == forskProm) {
                System.out.println((i + 1) + "º MergeFork: " + forskProm + " µs - Diff: " + (mergesProm - forskProm) + " µs");
            }
            if(proms[i] == execsProm) {
                System.out.println((i + 1) + "º MergeExec: " + execsProm + " µs - Diff: " + (mergesProm - execsProm) + " µs");
            }
        }*/

    }

    private static Boolean testSort(int[] array) {
        for(int i = 0; i < array.length - 1; i++) {
            if(array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }


    // ------------------------------ Music ------------------------------
    public static void playMusic(String song) throws IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
        File songPath = new File(System.getProperty("user.dir") + "/src/audio/" + song + ".wav");

        if(songPath.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(songPath);

            clip.open(audioInput);
            clip.start();
            Thread.sleep(2200);
            clip.close();
        }
        else {
            System.out.println(System.getProperty("user.dir"));
        }
    }

    public static void stopMusic() {
        clip.close();
    }
}
