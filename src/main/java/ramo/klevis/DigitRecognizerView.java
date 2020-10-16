package ramo.klevis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DigitRecognizerView extends JFrame {

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 628;

    private static final int MIN_FRAME_WIDTH = 700;
    private static final int MIN_FRAME_HEIGHT = 400;

    private DrawingCanvasView drawingCanvasView;
    private final JPanel mainPanel;
    private final JPanel drawAndDigitPredictionPanel;
    private JPanel resultPanel;
    private ResultLabel resultLabel;
    private final LoggerArea loggerArea;
    private final JMenuItem recognizeNN;
    private final JMenuItem recognizeCNN;
    private final JMenuItem clear;
    private final JMenuItem about;

    /**
     * Constructor for the GUI. Sets up all the necessary panels and buttons.
     */
    public DigitRecognizerView() {
        super();
        setupFrame();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        drawAndDigitPredictionPanel = new JPanel(new GridLayout());
        addDrawAreaAndPredictionArea();
        mainPanel.add(drawAndDigitPredictionPanel, BorderLayout.CENTER);

        JMenuBar menubar = new JMenuBar();
        this.recognizeCNN = new JMenuItem("Run CNN");
        this.recognizeNN = new JMenuItem("Run NN");

        JMenu run = new JMenu("Run");
        run.add(this.recognizeCNN);
        run.add(this.recognizeNN);

        this.about = new JMenuItem("About");
        this.clear = new JMenuItem("clear");

        menubar.add(run);
        menubar.add(this.about);
        menubar.add(this.clear);

        this.loggerArea = new LoggerArea();
        JScrollPane scrollPane = new JScrollPane(loggerArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        drawAndDigitPredictionPanel.add(scrollPane);

        this.setJMenuBar(menubar);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public DrawingCanvasView getDrawingCanvasView() {
        return drawingCanvasView;
    }

    public JMenuItem getRecognizeNN() {
        return recognizeNN;
    }

    public JMenuItem getRecognizeCNN() {
        return recognizeCNN;
    }

    public JMenuItem getAbout() {
        return about;
    }

    public JMenuItem getClear() {
        return clear;
    }

    public ResultLabel getResultLabel() {
        return resultLabel;
    }

    public LoggerArea getLoggerArea() {
        return loggerArea;
    }

    /**
     * Method that gets called by the about button to print a message box containing
     * about information.
     */
    public void printAbout() {
        JOptionPane.showMessageDialog(this, "Created by ramok.tech\nRefactored by Rosty Hnatyshyn, Akash Devdhar and Vraj Kapadia.\n" +
                "https://github.com/neospeed83/CSE-564-Project-3\nLicensed under the MIT License.");
    }

    /**
     * Sets up the area that the user will draw in as well as
     * the area in which the program will attempt to predict on the input.
     */
    private void addDrawAreaAndPredictionArea() {
        drawingCanvasView = new DrawingCanvasView();
        drawAndDigitPredictionPanel.add(drawingCanvasView);
        resultPanel = new JPanel();
        resultPanel.setLayout(new GridBagLayout());
        resultLabel = new ResultLabel();
        resultPanel.add(resultLabel);

        drawAndDigitPredictionPanel.add(resultPanel);
    }

    /**
     * Sets up the frame itself, setting its minimum size, the title and other misc things.
     */
    private void setupFrame() {
        this.setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        this.setTitle("Digit Recognizer");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        ImageIcon imageIcon = new ImageIcon("icon.png");
        this.setIconImage(imageIcon.getImage());
        this.setEnabled(false);
    }

    /**
     * Method called by the clear button to remove any extraneous information from the GUI.
     */
    public void clearCanvas() {
        drawingCanvasView.setImage(null);
        drawingCanvasView.repaint();
        drawAndDigitPredictionPanel.updateUI();
        this.getResultLabel().setText("");
        resultLabel.updateUI();
    }

}