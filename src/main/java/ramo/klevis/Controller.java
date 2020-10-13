package ramo.klevis;

import java.io.IOException;


public class Controller {

    DigitRecognizerView GUI;
    NeuralNetwork neuralNetwork;
    ConvolutionalNeuralNetwork convolutionalNeuralNetwork;
    MessageObservable observable;

    public Controller(DigitRecognizerView GUI,NeuralNetwork neuralNetwork,
                      ConvolutionalNeuralNetwork convolutionalNeuralNetwork) throws IOException {
        this.GUI = GUI;
        this.neuralNetwork = neuralNetwork;
        this.convolutionalNeuralNetwork = convolutionalNeuralNetwork;
        this.observable = new MessageObservable();
        observable.addObserver(GUI.getResultLabel());

        neuralNetwork.init();
        convolutionalNeuralNetwork.init();

        GUI.getRecognizeNN().addActionListener(e -> recognizeNeuralNetwork());
        GUI.getRecognizeCNN().addActionListener(e -> recognizeConvolutionalNeuralNetwork());
        GUI.getClear().addActionListener(e -> onClear());
        GUI.getAbout().addActionListener(e -> about());
    }

    private void about(){
        GUI.printAbout();
    }


    private void recognizeNeuralNetwork(){
        double[] scaledPixels = GUI.getDrawingCanvasView().getScaledPixels();
        LabeledImage labeledImage = new LabeledImage(0, scaledPixels);
        LabeledImage predict = neuralNetwork.predict(labeledImage);
        observable.changeData((int) predict.getLabel());
    }

    private void recognizeConvolutionalNeuralNetwork(){
        double[] scaledPixels = GUI.getDrawingCanvasView().getScaledPixels();
        LabeledImage labeledImage = new LabeledImage(0, scaledPixels);
        LabeledImage predict =
                convolutionalNeuralNetwork.predict(labeledImage);
        observable.changeData((int) predict.getLabel());
    }

    private void onClear(){
        GUI.clearCanvas();
    }
}
