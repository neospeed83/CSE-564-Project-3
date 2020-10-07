package ramo.klevis;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DigitRecognizerView {

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 628;

    private DrawingCanvasView drawingCanvasView;



    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel drawAndDigitPredictionPanel;
    private JSpinner trainField;
    private final Font sansSerifBold = new Font("SansSerif", Font.BOLD, 18);
    private JSpinner testField;
    private JPanel resultPanel;
    private JButton recognizeNN;
    private JButton recognizeCNN;
    private JButton clear;
    private JButton trainNN;
    private JButton trainCNN;


    public DigitRecognizerView() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
        UIManager.put("LoadingBarView.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
        initUI();
    }

    public DrawingCanvasView getDrawingCanvasView() {
        return drawingCanvasView;
    }

    public JFrame getMainFrame() { return mainFrame; }

    public JButton getRecognizeNN() {
        return recognizeNN;
    }

    public JButton getRecognizeCNN() {
        return recognizeCNN;
    }

    public JButton getClear() {
        return clear;
    }

    public JButton getTrainNN() {
        return trainNN;
    }

    public JButton getTrainCNN() {
        return trainCNN;
    }

    public Integer getTrainFieldValue() { return (Integer) trainField.getValue(); }
    public Integer getTestFieldValue() { return (Integer) testField.getValue(); }

    private void initUI() {
        // create main frame
        mainFrame = createMainFrame();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        addTopPanel();

        drawAndDigitPredictionPanel = new JPanel(new GridLayout());
        addActionPanel();
        addDrawAreaAndPredictionArea();
        mainPanel.add(drawAndDigitPredictionPanel, BorderLayout.CENTER);

        addSignature();

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

    }

    private void addActionPanel() {
        recognizeNN = new JButton("Recognize Digit With Simple NN");
        recognizeCNN = new JButton("Recognize Digit With Conv NN");

        clear = new JButton("Clear");

        JPanel actionPanel = new JPanel(new GridLayout(8, 1));
        actionPanel.add(recognizeCNN);
        actionPanel.add(recognizeNN);
        actionPanel.add(clear);
        drawAndDigitPredictionPanel.add(actionPanel);
    }

    private void addDrawAreaAndPredictionArea() {

        drawingCanvasView = new DrawingCanvasView();

        drawAndDigitPredictionPanel.add(drawingCanvasView);
        resultPanel = new JPanel();
        resultPanel.setLayout(new GridBagLayout());
        drawAndDigitPredictionPanel.add(resultPanel);
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout());
        trainNN = new JButton("Train NN");
        trainCNN = new JButton("Train Convolutional NN");

        topPanel.add(trainCNN);
        topPanel.add(trainNN);

        JLabel tL = new JLabel("Training Data");
        tL.setFont(sansSerifBold);
        topPanel.add(tL);
        int TRAIN_SIZE = 30000;
        SpinnerNumberModel modelTrainSize = new SpinnerNumberModel(TRAIN_SIZE, 10000, 60000, 1000);
        trainField = new JSpinner(modelTrainSize);
        trainField.setFont(sansSerifBold);
        topPanel.add(trainField);

        JLabel ttL = new JLabel("Test Data");
        ttL.setFont(sansSerifBold);
        topPanel.add(ttL);
        int TEST_SIZE = 10000;
        SpinnerNumberModel modelTestSize = new SpinnerNumberModel(TEST_SIZE, 1000, 10000, 500);
        testField = new JSpinner(modelTestSize);
        testField.setFont(sansSerifBold);
        topPanel.add(testField);

        mainPanel.add(topPanel, BorderLayout.NORTH);
    }


    private JFrame createMainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Digit Recognizer");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        ImageIcon imageIcon = new ImageIcon("icon.png");
        mainFrame.setIconImage(imageIcon.getImage());

        return mainFrame;
    }

    private void addSignature() {
        JLabel signature = new JLabel("Refactored by Rosty Hnatyshyn, Akash Devdhar, Vraj Kapadia", SwingConstants.CENTER);
        signature.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        signature.setForeground(Color.BLUE);
        mainPanel.add(signature, BorderLayout.SOUTH);
    }

    public void updatePrediction(LabeledImage predict){
        JLabel predictNumber = new JLabel("" + (int) predict.getLabel());
        predictNumber.setForeground(Color.RED);
        predictNumber.setFont(new Font("SansSerif", Font.BOLD, 128));
        resultPanel.removeAll();
        resultPanel.add(predictNumber);
        resultPanel.updateUI();
    }

    public void clearCanvas(){
        drawingCanvasView.setImage(null);
        drawingCanvasView.repaint();
        drawAndDigitPredictionPanel.updateUI();
    }
}