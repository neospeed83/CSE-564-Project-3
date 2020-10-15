package ramo.klevis;

import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.sql.SparkSession;

import java.util.logging.Logger;

import static ramo.klevis.Driver.loggerAreaHandler;

public class NeuralNetwork {

    private final static Logger LOGGER = Logger.getLogger(NeuralNetwork.class.getName());
    private SparkSession sparkSession;
    private MultilayerPerceptronClassificationModel model;

    /**
     * Initializes the NN using a pre trained model file and outputs its status in the LoggerArea.
     * Sets up a spark session for the neural network.
     */
    public void init() {
        LOGGER.addHandler(loggerAreaHandler);
        if (sparkSession == null) {
            sparkSession = SparkSession.builder()
                    .master("local[*]")
                    .appName("Digit Recognizer")
                    .getOrCreate();

        }

        sparkSession.sparkContext().setCheckpointDir("checkPoint");

        if (model == null) {
            LOGGER.info("Loading neural network from saved model... ");
            model = MultilayerPerceptronClassificationModel.load("resources/nnTrainedModels/ModelWith60000");
            LOGGER.info(model.javaLayers().toString());
            LOGGER.info(model.explainParams());
            LOGGER.info("Neural network loaded.");
        }
    }

    /**
     * Attempts to predict on the input from the canvas.
     *
     * @param labeledImage The input from the canvas.
     * @return A prediction based on the input.
     */
    public LabeledImage predict(LabeledImage labeledImage) {
        double predict = model.predict(labeledImage.getFeatures());
        labeledImage.setLabel(predict);
        LOGGER.info(labeledImage.getFeatures().toString());
        LOGGER.info(labeledImage.toString());
        return labeledImage;
    }
}
