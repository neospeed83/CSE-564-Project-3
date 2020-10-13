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
    private JPanel mainPanel;
    private JPanel drawAndDigitPredictionPanel;
    private JPanel resultPanel;
    private ResultLabel resultLabel;
    private LoggerArea loggerArea;
    private JMenuItem recognizeNN;
    private JMenuItem recognizeCNN;
    private JMenuItem clear;
    private JMenuItem about;

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

    public LoggerArea getLoggerArea() {return loggerArea;}

    public DigitRecognizerView()  {
        super();
        setupFrame();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        drawAndDigitPredictionPanel = new JPanel(new GridLayout());
        addDrawAreaAndPredictionArea();
        mainPanel.add(drawAndDigitPredictionPanel, BorderLayout.CENTER);

        JMenuBar menubar = new JMenuBar();
        this.recognizeCNN =new JMenuItem("Run CNN");
        this.recognizeNN =new JMenuItem("Run NN");
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

    public void printAbout() {
        JOptionPane.showMessageDialog(this, "Refactored by Rosty Hnatyshyn, Akash Devdhar and Vraj Kapadia.\n" +
                                                         "https://github.com/neospeed83/CSE-564-Project-3");
    }

    private void addDrawAreaAndPredictionArea() {
        drawingCanvasView = new DrawingCanvasView();
        drawAndDigitPredictionPanel.add(drawingCanvasView);
        resultPanel = new JPanel();
        resultPanel.setLayout(new GridBagLayout());
        resultLabel = new ResultLabel();
        resultPanel.add(resultLabel);

        drawAndDigitPredictionPanel.add(resultPanel);
    }

    private void setupFrame() {
        this.setMinimumSize(new Dimension(MIN_FRAME_WIDTH,MIN_FRAME_HEIGHT));
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
    }


    public void clearCanvas(){
        drawingCanvasView.setImage(null);
        drawingCanvasView.repaint();
        drawAndDigitPredictionPanel.updateUI();
        this.getResultLabel().setText("");
        resultLabel.updateUI();
    }

}