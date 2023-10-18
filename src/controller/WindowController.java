package controller;

import view.Window;
import model.App;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class WindowController {
    public static Window window;

    public static void start() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, ExecutionException, InterruptedException {
        window = new Window();
    }

    public static void input(KeyEvent e) {
        char inputChar = e.getKeyChar();
        if((inputChar < '0' || inputChar > '9') && !(inputChar == KeyEvent.VK_ENTER || inputChar == KeyEvent.VK_BACK_SPACE || inputChar == KeyEvent.VK_DELETE)) {
            window.getToolkit().beep();
            e.consume();
        }
        validateInput();

        if(inputChar == KeyEvent.VK_ENTER && !window.getInputSizeTxt().getText().isEmpty()) {
            window.getCreateArrayBtn().doClick();
        }
    }

    public static void format(KeyEvent e) {
        char inputChar = e.getKeyChar();
        if(inputChar >= '0' &&  inputChar <= '9') {

            String inputStr = window.getInputSizeTxt().getText().replaceAll(",", "");
            long input = Long.parseLong(inputStr);

            window.getInputSizeTxt().setText(formatTime(input));
        }
    }


    public static void validateInput() {
        if(window.getInputSizeTxt().getText().isEmpty()) {
            window.getCreateArrayBtn().setEnabled(false);
        }
        else {
            window.getCreateArrayBtn().setEnabled(true);
        }
    }

    private static void setSortEnabled(Boolean state) {
        window.getMergeSortBtn().setEnabled(state);
        window.getForkJoinBtn().setEnabled(state);
        window.getExecutorServiceBtn().setEnabled(state);
    }

    public static void generateArray() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        //App.playMusic("chiquitita");

        int length = Integer.parseInt(window.getInputSizeTxt().getText().replaceAll(",", ""));
        if(length > 200000 && !App.fastMode) {
            fastMode();
        }
        App.generateArray(length);

        String arrayStr = Arrays.toString(App.inputArray).replaceAll("\\[|\\]|,|", "");

        window.getInputArrayTxtArea().setText(arrayStr);

        setSortEnabled(true);
    }

    public static void clean() {
        window.getInputArrayTxtArea().setText("");
        window.getOutputArrayTxtArea().setText("");
        //window.getInputSizeTxt().setText("");
        //window.getMergeSortLabel().setText("");
        //window.getForkJoinLabel().setText("");
        //window.getExecutorServiceLabel().setText("");
        //setSortEnabled(false);
        //window.getCreateArrayBtn().setEnabled(false);
        //window.getInputSizeTxt().requestFocusInWindow();
    }

    public static void fastMode() {
        App.fastMode = !App.fastMode;

        if(!App.fastMode) {
            window.getFastModeBtn().setText("Modo Rápido");
        }
        else {
            window.getFastModeBtn().setText("Modo lento");
        }
        window.getInputArrayTxtArea().setLineWrap(!App.fastMode);
        window.getOutputArrayTxtArea().setLineWrap(!App.fastMode);
    }

    public static String formatTime(long time) {
        StringBuilder format = new StringBuilder();
        String timeStr = String.valueOf(time);

        for(int i = 0; i < timeStr.length(); i++) {
            format.append("#");
            if((i + 1) % 3 == 0 && timeStr.length() > 3) {
                format.append(",");
            }
        }
        format.reverse();
        DecimalFormat formatter = new DecimalFormat(format.toString());
        return formatter.format(time);
    }

    public static void printSortedArray(int[] array) {
        String arrayStr = Arrays.toString(array).replaceAll("\\[|\\]|,|", "");
        window.getOutputArrayTxtArea().setText(arrayStr);
    }

    public static void mergeSort() {
        int[] newArray = Arrays.copyOf(App.inputArray, App.inputArray.length);

        long time = App.mergeSort(newArray);
        window.getMergeSortLabel().setText(formatTime(time) + " ns");

        printSortedArray(newArray);

    }

    public static void forkJoin() {
        int[] newArray = Arrays.copyOf(App.inputArray, App.inputArray.length);

        long time = App.forkJoinMergeSort(newArray);
        window.getForkJoinLabel().setText(formatTime(time) + " ns");

        printSortedArray(newArray);

    }

    public static void executorService() throws ExecutionException, InterruptedException {
        int[] newArray = Arrays.copyOf(App.inputArray, App.inputArray.length);

        long time = App.executorServiceMergeSort(newArray);;
        window.getExecutorServiceLabel().setText(formatTime(time) + " ns");

        printSortedArray(newArray);

    }

}
