package ramo.klevis;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.Executors;

public class Controller {

    DigitRecognizerView GUI;
    NeuralNetwork neuralNetwork;
    ConvolutionalNeuralNetwork convolutionalNeuralNetwork;

    public Controller(DigitRecognizerView GUI,NeuralNetwork neuralNetwork,
                      ConvolutionalNeuralNetwork convolutionalNeuralNetwork) throws IOException {
        this.GUI = GUI;
        this.neuralNetwork = neuralNetwork;
        this.convolutionalNeuralNetwork = convolutionalNeuralNetwork;
        neuralNetwork.init();
        convolutionalNeuralNetwork.init();
        initController();
    }

    private void initController(){
        GUI.getRecognizeNN().addActionListener(e -> recognizeNeuralNetwork());
        GUI.getRecognizeCNN().addActionListener(e -> recognizeConvolutionalNeuralNetwork());
        GUI.getClear().addActionListener(e -> onClear());
        GUI.getTrainNN().addActionListener(e -> trainNN());
        GUI.getTrainCNN().addActionListener(e -> trainCNN());
    }

    private void recognizeNeuralNetwork(){
        double[] scaledPixels = GUI.getDrawingCanvasView().getScaledPixels();
        LabeledImage labeledImage = new LabeledImage(0, scaledPixels);
        LabeledImage predict = neuralNetwork.predict(labeledImage);
        GUI.updatePrediction(predict);
    }

    private void recognizeConvolutionalNeuralNetwork(){
        double[] scaledPixels = GUI.getDrawingCanvasView().getScaledPixels();
        LabeledImage labeledImage = new LabeledImage(0, scaledPixels);
        LabeledImage predict =
                convolutionalNeuralNetwork.predict(labeledImage);
        GUI.updatePrediction(predict);
    }

    private void onClear(){
        GUI.clearCanvas();
    }

    private void trainNN(){

        int i = JOptionPane.showConfirmDialog(mainFrame, "Are you sure this may take some time to train?");

        if (i == JOptionPane.OK_OPTION) {
            LoadingBarView loadingBarView = new LoadingBarView(mainFrame);
            SwingUtilities.invokeLater(() -> loadingBarView.showProgressBar("Training may take one or two minutes..."));
            Executors.newCachedThreadPool().submit(() -> {
                try {
                    LOGGER.info("Start of train Neural Network");
                    neuralNetwork.train((Integer) trainField.getValue(), (Integer) testField.getValue());
                    LOGGER.info("End of train Neural Network");
                } finally {
                    loadingBarView.setVisible(false);
                }
            });
        }
    }

    private void trainCNN(){
        int i = JOptionPane.showConfirmDialog(mainFrame, "Are you sure, training requires >10GB memory and more than 1 hour?");

        if (i == JOptionPane.OK_OPTION) {
            LoadingBarView loadingBarView = new LoadingBarView(mainFrame);
            SwingUtilities.invokeLater(() -> loadingBarView.showProgressBar("Training may take a while..."));
            Executors.newCachedThreadPool().submit(() -> {
                try {
                    LOGGER.info("Start of train Convolutional Neural Network");
                    convolutionalNeuralNetwork.train((Integer) trainField.getValue(), (Integer) testField.getValue());
                    LOGGER.info("End of train Convolutional Neural Network");
                } catch (IOException e1) {
                    LOGGER.error("CNN not trained " + e1);
                    throw new RuntimeException(e1);
                } finally {
                    loadingBarView.setVisible(false);
                }
            });
        }
    }

}
