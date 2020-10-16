package ramo.klevis;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static ramo.klevis.Driver.loggerAreaHandler;

public class ConvolutionalNeuralNetwork {
    private static final Logger LOGGER = Logger.getLogger(ConvolutionalNeuralNetwork.class.getName());
    private static final String TRAINED_MODEL_FILE = "resources/cnnTrainedModels/bestModel.bin";
    private MultiLayerNetwork preTrainedModel;

    /**
     * Initializes the CNN using a preTrainedModel file and outputs its status in the LoggerArea.
     */
    public void init() {
        LOGGER.addHandler(loggerAreaHandler);
        try {
            LOGGER.info("Initializing convolutional neural network...");
            preTrainedModel = ModelSerializer.restoreMultiLayerNetwork(new File(TRAINED_MODEL_FILE));
        } catch (IOException e) {
            LOGGER.info("Failed to initialize CNN");
        }
        LOGGER.info("Convolutional neural network initialized.");
    }

    /**
     * Attempts to predict on the input from the canvas.
     *
     * @param labeledImage The input from the canvas.
     * @return A prediction based on the input.
     */
    public LabeledImage predict(LabeledImage labeledImage) {
        double[] pixels = labeledImage.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] / 255d;
        }
        int[] predict = preTrainedModel.predict(Nd4j.create(pixels));
        labeledImage.setLabel(predict[0]);
        LOGGER.info("Predicting using convolutional neural network...");
        LOGGER.info(labeledImage.toString());
        return labeledImage;
    }
}