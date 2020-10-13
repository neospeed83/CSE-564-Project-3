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
    private static final String OUT_DIR = "resources/cnnCurrentTrainingModels";
    private static final String TRAINED_MODEL_FILE = "resources/cnnTrainedModels/bestModel.bin";

    private MultiLayerNetwork preTrainedModel;

    public void init() throws IOException {
        LOGGER.addHandler(loggerAreaHandler);
        preTrainedModel = ModelSerializer.restoreMultiLayerNetwork(new File(TRAINED_MODEL_FILE));
    }

    public LabeledImage predict(LabeledImage labeledImage) {
        double[] pixels = labeledImage.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] / 255d;
        }
        int[] predict = preTrainedModel.predict(Nd4j.create(pixels));
        labeledImage.setLabel(predict[0]);
        return labeledImage;
    }
}