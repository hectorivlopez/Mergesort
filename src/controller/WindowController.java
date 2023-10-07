package controller;

import model.ForkJoinMergeSortAction;
import model.ForkJoinMergeSortTask;
import model.MergeSort;
import view.Window;
import model.App;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Arrays;

public class WindowController {
    public static Window window;

    public static void start() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
            StringBuilder format = new StringBuilder();
            String inputStr = window.getInputSizeTxt().getText().replaceAll(",", "");

            for(int i = 0; i < inputStr.length(); i++) {
                format.append("#");
                if((i + 1) % 3 == 0 && inputStr.length() > 3) {
                    format.append(",");
                }
            }
            format.reverse();
            DecimalFormat formatter = new DecimalFormat(format.toString());

            int inputInt = Integer.parseInt(inputStr);
            window.getInputSizeTxt().setText(formatter.format(inputInt));

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

    public static void generateArray() {
        int length = Integer.parseInt(window.getInputSizeTxt().getText().replaceAll(",", ""));
        if(length > 200000 && !App.fastMode) {
            fastMode();
        }
        App.generateArray(length);

        String arrayStr = Arrays.toString(App.inputArray).replaceAll("\\[|\\]|,|", "");

        /*if(!App.fastMode) {
            System.out.println(arrayStr.length());
            int limit = arrayStr.length() / 138;
            System.out.println(limit);

            String cosa = "";
            int i;
            for(i = 0; i < limit ; i++) {
                cosa += arrayStr.substring(i * 138, 138 + (i * 138));
                cosa += "\n";
            }
            cosa += arrayStr.substring(i * 138);
            window.getInputArrayTxtArea().setText(cosa);
        }
        else {
            window.getInputArrayTxtArea().setText(arrayStr);
        }*/

        window.getInputArrayTxtArea().setText(arrayStr);

        setSortEnabled(true);
    }

    public static void clean() {
        window.getInputArrayTxtArea().setText("");
        window.getOutputArrayTxtArea().setText("");
        window.getInputSizeTxt().setText("");
        window.getMergeSortLabel().setText("");
        window.getForkJoinLabel().setText("");
        window.getExecutorServiceLabel().setText("");
        setSortEnabled(false);
        window.getCreateArrayBtn().setEnabled(false);
        window.getInputSizeTxt().requestFocusInWindow();
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

    public static void mergeSort() {
        int[] newArray = Arrays.copyOf(App.inputArray, App.inputArray.length);

        long time = App.mergeSort(newArray);
        window.getMergeSortLabel().setText(time + " millis");

        String arrayStr = Arrays.toString(newArray).replaceAll("\\[|\\]|,|", "");
        window.getOutputArrayTxtArea().setText(arrayStr);
    }

    public static void forkJoin() {
        int[] newArray = Arrays.copyOf(App.inputArray, App.inputArray.length);
        /* ForkJoinMergeSortTask mergeSort = new ForkJoinMergeSortTask(newArray, newArray.length);

        long initTime = System.currentTimeMillis();
        int[] sortedArray = mergeSort.invoke();
        long endTime = System.currentTimeMillis(); */

        long time = App.forkJoinMergeSort(newArray);
        window.getForkJoinLabel().setText(time + " millis");

        String arrayStr = Arrays.toString(newArray).replaceAll("\\[|\\]|,|", "");
        window.getOutputArrayTxtArea().setText(arrayStr);

    }

    public static void executorService() {
        window.getOutputArrayTxtArea().setText("No");
    }

}
