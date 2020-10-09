package ramo.klevis;

import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class DigitRecognizerView {

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 628;

    private DrawingCanvasView drawingCanvasView;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel drawAndDigitPredictionPanel;
    private JPanel resultPanel;
    private ResultLabel resultLabel;
    private JMenuItem recognizeNN;
    private JMenuItem recognizeCNN;
    private JMenuItem clear;
    private JMenuItem about;


    public DigitRecognizerView() {
        mainFrame = createMainFrame();
        mainFrame.setMinimumSize(new Dimension(700,400));
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        drawAndDigitPredictionPanel = new JPanel(new GridLayout());
        addDrawAreaAndPredictionArea();
        mainPanel.add(drawAndDigitPredictionPanel, BorderLayout.CENTER);

        addSignature();

        //MenuBar Work in progress
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

        mainFrame.setJMenuBar(menubar);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
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

    public JMenuItem getClear() {
        return clear;
    }

    public ResultLabel getResultLabel() {
        return resultLabel;
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

    public void clearCanvas(){
        drawingCanvasView.setImage(null);
        drawingCanvasView.repaint();
        drawAndDigitPredictionPanel.updateUI();
        this.getResultLabel().setText("");
        resultLabel.updateUI();
    }

//    public void updateResult(LabeledImage predict) {
//        this.getResultLabel().setText(""+ (int) predict.getLabel());
//        resultLabel.updateUI();
//    }
}