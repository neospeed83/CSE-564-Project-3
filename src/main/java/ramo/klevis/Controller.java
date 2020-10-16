package ramo.klevis;

import java.util.logging.Logger;

import static ramo.klevis.Driver.loggerAreaHandler;

public class Controller {

    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    DigitRecognizerView GUI;
    NeuralNetwork neuralNetwork;
    ConvolutionalNeuralNetwork convolutionalNeuralNetwork;
    MessageObservable observable;

    /**
     * Constructor for the Controller class. This class follows the MVC pattern and allows us to separate
     * the look of the GUI from its functionality.
     *
     * @param GUI                        A reference to the GUI (DigitRecognizerView)
     * @param neuralNetwork              A reference to the neural network used to predict with.
     * @param convolutionalNeuralNetwork A reference to the convolutional neural network used to predict with.
     */
    public Controller(DigitRecognizerView GUI, NeuralNetwork neuralNetwork,
                      ConvolutionalNeuralNetwork convolutionalNeuralNetwork) {
        LOGGER.addHandler(loggerAreaHandler);
        this.GUI = GUI;
        this.neuralNetwork = neuralNetwork;
        this.convolutionalNeuralNetwork = convolutionalNeuralNetwork;
        this.observable = new MessageObservable();
        observable.addObserver(GUI.getResultLabel());

        neuralNetwork.init();
        convolutionalNeuralNetwork.init();

        LOGGER.info("Application loaded.");
        LOGGER.info("The GUI will be unlocked now.");

        GUI.setEnabled(true);

        GUI.getRecognizeNN().addActionListener(e -> recognizeNeuralNetwork());
        GUI.getRecognizeCNN().addActionListener(e -> recognizeConvolutionalNeuralNetwork());
        GUI.getClear().addActionListener(e -> onClear());
        GUI.getAbout().addActionListener(e -> about());
    }

    /**
     * Wires the about button to the method that the GUI
     * calls to draw a message box with the about information.
     */
    private void about() {
        GUI.printAbout();
    }

    /**
     * Predicts on whatever was inside the canvas view using the neural network.
     */
    private void recognizeNeuralNetwork() {
        double[] scaledPixels = GUI.getDrawingCanvasView().getScaledPixels();
        LabeledImage labeledImage = new LabeledImage(0, scaledPixels);
        LabeledImage predict = neuralNetwork.predict(labeledImage);
        observable.changeData((int) predict.getLabel());
    }

    /**
     * Predicts on whatever was inside the canvas view using the convolutional neural network.
     */
    private void recognizeConvolutionalNeuralNetwork() {
        double[] scaledPixels = GUI.getDrawingCanvasView().getScaledPixels();
        LabeledImage labeledImage = new LabeledImage(0, scaledPixels);
        LabeledImage predict = convolutionalNeuralNetwork.predict(labeledImage);
        observable.changeData((int) predict.getLabel());
    }

    /**
     * Wires the clear button to the method that the GUI
     * calls to clear itself.
     */
    private void onClear() {
        GUI.clearCanvas();
    }
}
