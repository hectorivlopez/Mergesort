package view;

import components.CustomScrollBar;
import controller.WindowController;
import model.MergeSort;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.ParagraphView;
import javax.swing.text.ZoneView;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Window extends JFrame {
    private JPanel bgPanel;
    private JScrollPane scrollInput;
    private JTextArea inputArrayTxtArea;
    private JTextField inputSizeTxt;
    private JButton createArrayBtn;
    private JButton cleanBtn;
    private JButton fastModeBtn;
    private JScrollPane scrollOutput;
    private JTextArea outputArrayTxtArea;
    private JButton mergeSortBtn;
    private JButton forkJoinBtn;
    private JButton executorServiceBtn;
    private JLabel mergeSortLabel;
    private JLabel forkJoinLabel;
    private JLabel executorServiceLabel;


    public Window() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        UIManager.put("Button.arc", 10);

        final JRootPane rootPane = this.getRootPane();
        rootPane.putClientProperty("apple.awt.fullWindowContent", true);
        rootPane.putClientProperty("apple.awt.transparentTitleBar", true);


        setSize(1000,650);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        bgPanel = new JPanel();
        bgPanel.setBounds(0,0,this.getWidth(),this.getHeight());
        bgPanel.setBackground(new Color(15,17,26));
        bgPanel.setLayout(null);
        setContentPane(bgPanel);

        inputArrayTxtArea = new JTextArea();
        inputArrayTxtArea.setBounds(0,0,950,200);
        inputArrayTxtArea.setMargin(new Insets(10,10,10,10));
        inputArrayTxtArea.setBackground(new Color(34,37,51));
        inputArrayTxtArea.setForeground(Color.WHITE);
        inputArrayTxtArea.setLineWrap(true);
        inputArrayTxtArea.setEnabled(false);
        inputArrayTxtArea.setText("");

        scrollInput = new JScrollPane(inputArrayTxtArea);
        scrollInput.setVerticalScrollBar(new CustomScrollBar(SwingConstants.VERTICAL));
        scrollInput.setHorizontalScrollBar(new CustomScrollBar(SwingConstants.HORIZONTAL));

        scrollInput.setBounds(25,50,950,200);
        scrollInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        bgPanel.add(scrollInput);

        inputSizeTxt = new JTextField();
        inputSizeTxt.setBounds(25,260,104,30);
        inputSizeTxt.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));
        inputSizeTxt.setBackground(new Color(25,26,33));
        inputSizeTxt.setForeground(Color.WHITE);
        inputSizeTxt.setCaretColor(Color.WHITE);
        inputSizeTxt.setHorizontalAlignment(SwingConstants.RIGHT);
        inputSizeTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                WindowController.input(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                WindowController.validateInput();
                WindowController.format(e);
            }
        });
        bgPanel.add(inputSizeTxt);

        createArrayBtn = new JButton();
        createArrayBtn.setBounds(154,260,257, 30);
        createArrayBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        createArrayBtn.setFocusPainted(false);
        createArrayBtn.setBackground(new Color(50,50,50));
        createArrayBtn.setForeground(Color.WHITE);
        createArrayBtn.setText("Generar Array");
        createArrayBtn.setEnabled(false);
        createArrayBtn.addActionListener(e -> {
            WindowController.generateArray();
        });
        bgPanel.add(createArrayBtn);

        cleanBtn = new JButton();
        cleanBtn.setBounds(436,260,257, 30);
        cleanBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        cleanBtn.setFocusPainted(false);
        cleanBtn.setBackground(new Color(50,50,50));
        cleanBtn.setForeground(Color.WHITE);
        cleanBtn.setText("Limpiar");
        cleanBtn.addActionListener(e -> {
            WindowController.clean();
        });
        bgPanel.add(cleanBtn);

        fastModeBtn = new JButton();
        fastModeBtn.setBounds(718,260,257, 30);
        fastModeBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        fastModeBtn.setFocusPainted(false);
        fastModeBtn.setBackground(new Color(50,50,50));
        fastModeBtn.setForeground(Color.WHITE);
        fastModeBtn.setText("Modo Recio");
        fastModeBtn.addActionListener(e -> {
            WindowController.fastMode();
        });
        bgPanel.add(fastModeBtn);

        outputArrayTxtArea = new JTextArea();
        outputArrayTxtArea.setBounds(0,0,950,200);
        outputArrayTxtArea.setMargin(new Insets(10,10,10,10));
        outputArrayTxtArea.setBackground(new Color(34,37,51));
        outputArrayTxtArea.setForeground(Color.WHITE);
        outputArrayTxtArea.setLineWrap(true);
        outputArrayTxtArea.setText("");
        outputArrayTxtArea.setEnabled(false);

        scrollOutput = new JScrollPane(outputArrayTxtArea);
        scrollOutput.setVerticalScrollBar(new CustomScrollBar(SwingConstants.VERTICAL));
        scrollOutput.setHorizontalScrollBar(new CustomScrollBar(SwingConstants.HORIZONTAL));
        scrollOutput.setBounds(25,300,950,200);
        scrollOutput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        bgPanel.add(scrollOutput);

        mergeSortBtn = new JButton();
        mergeSortBtn.setBounds(25,520,300, 30);
        mergeSortBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        mergeSortBtn.setFocusPainted(false);
        mergeSortBtn.setBackground(new Color(50,50,50));
        mergeSortBtn.setForeground(Color.WHITE);
        mergeSortBtn.setText("MergeSort");
        mergeSortBtn.setEnabled(false);
        mergeSortBtn.addActionListener(e -> {
            WindowController.mergeSort();
        });
        bgPanel.add(mergeSortBtn);

        forkJoinBtn = new JButton();
        forkJoinBtn.setBounds(350,520,300, 30);
        forkJoinBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        forkJoinBtn.setFocusPainted(false);
        forkJoinBtn.setBackground(new Color(50,50,50));
        forkJoinBtn.setForeground(Color.WHITE);
        forkJoinBtn.setText("ForkJoin");
        forkJoinBtn.setEnabled(false);
        forkJoinBtn.addActionListener(e -> {
            WindowController.forkJoin();
        });
        bgPanel.add(forkJoinBtn);

        executorServiceBtn = new JButton();
        executorServiceBtn.setBounds(675,520,300, 30);
        executorServiceBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        executorServiceBtn.setFocusPainted(false);
        executorServiceBtn.setBackground(new Color(50,50,50));
        executorServiceBtn.setForeground(Color.WHITE);
        executorServiceBtn.setText("ExecutorService");
        executorServiceBtn.setEnabled(false);
        executorServiceBtn.addActionListener(e -> {
            try {
                WindowController.executorService();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        });
        bgPanel.add(executorServiceBtn);

        mergeSortLabel = new JLabel();
        mergeSortLabel.setBounds(25,570,300, 30);
        mergeSortLabel.setForeground(Color.WHITE);
        mergeSortLabel.setText("");
        mergeSortLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bgPanel.add(mergeSortLabel);

        forkJoinLabel = new JLabel();
        forkJoinLabel.setBounds(350,570,300, 30);
        forkJoinLabel.setForeground(Color.WHITE);
        forkJoinLabel.setText("");
        forkJoinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bgPanel.add(forkJoinLabel);

        executorServiceLabel = new JLabel();
        executorServiceLabel.setBounds(675,570,300, 30);
        executorServiceLabel.setForeground(Color.WHITE);
        executorServiceLabel.setText("");
        executorServiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bgPanel.add(executorServiceLabel);
    }

    public JPanel getBgPanel() {
        return bgPanel;
    }

    public void setBgPanel(JPanel bgPanel) {
        this.bgPanel = bgPanel;
    }

    public JScrollPane getScrollInput() {
        return scrollInput;
    }

    public void setScrollInput(JScrollPane scrollInput) {
        this.scrollInput = scrollInput;
    }

    public JTextArea getInputArrayTxtArea() {
        return inputArrayTxtArea;
    }

    public void setInputArrayTxtArea(JTextArea inputArrayTxtArea) {
        this.inputArrayTxtArea = inputArrayTxtArea;
    }

    public JTextField getInputSizeTxt() {
        return inputSizeTxt;
    }

    public void setInputSizeTxt(JTextField inputSizeTxt) {
        this.inputSizeTxt = inputSizeTxt;
    }

    public JButton getCreateArrayBtn() {
        return createArrayBtn;
    }

    public void setCreateArrayBtn(JButton createArrayBtn) {
        this.createArrayBtn = createArrayBtn;
    }

    public JScrollPane getScrollOutput() {
        return scrollOutput;
    }

    public void setScrollOutput(JScrollPane scrollOutput) {
        this.scrollOutput = scrollOutput;
    }

    public JTextArea getOutputArrayTxtArea() {
        return outputArrayTxtArea;
    }

    public void setOutputArrayTxtArea(JTextArea outputArrayTxtArea) {
        this.outputArrayTxtArea = outputArrayTxtArea;
    }

    public JButton getMergeSortBtn() {
        return mergeSortBtn;
    }

    public void setMergeSortBtn(JButton mergeSortBtn) {
        this.mergeSortBtn = mergeSortBtn;
    }

    public JButton getForkJoinBtn() {
        return forkJoinBtn;
    }

    public void setForkJoinBtn(JButton forkJoinBtn) {
        this.forkJoinBtn = forkJoinBtn;
    }

    public JButton getExecutorServiceBtn() {
        return executorServiceBtn;
    }

    public void setExecutorServiceBtn(JButton executorServiceBtn) {
        this.executorServiceBtn = executorServiceBtn;
    }

    public JLabel getMergeSortLabel() {
        return mergeSortLabel;
    }

    public void setMergeSortLabel(JLabel mergeSortLabel) {
        this.mergeSortLabel = mergeSortLabel;
    }

    public JLabel getForkJoinLabel() {
        return forkJoinLabel;
    }

    public void setForkJoinLabel(JLabel forkJoinLabel) {
        this.forkJoinLabel = forkJoinLabel;
    }

    public JLabel getExecutorServiceLabel() {
        return executorServiceLabel;
    }

    public JButton getCleanBtn() {
        return cleanBtn;
    }

    public void setCleanBtn(JButton cleanBtn) {
        this.cleanBtn = cleanBtn;
    }

    public JButton getFastModeBtn() {
        return fastModeBtn;
    }

    public void setFastModeBtn(JButton fastModeBtn) {
        this.fastModeBtn = fastModeBtn;
    }

    public void setExecutorServiceLabel(JLabel executorServiceLabel) {
        this.executorServiceLabel = executorServiceLabel;
    }


}
