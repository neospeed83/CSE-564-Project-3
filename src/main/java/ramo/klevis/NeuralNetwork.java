package ramo.klevis;

import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.sql.SparkSession;

import java.util.logging.Logger;

import static ramo.klevis.Driver.loggerAreaHandler;


public class NeuralNetwork {

    private final static Logger LOGGER = Logger.getLogger(NeuralNetwork.class.getName());

    private SparkSession sparkSession;
    private MultilayerPerceptronClassificationModel model;

    public void init() {
        initSparkSession();
        LOGGER.addHandler(loggerAreaHandler);
        if (model == null) {
            LOGGER.info("Loading the Neural Network from saved model ... ");
            model = MultilayerPerceptronClassificationModel.load("resources/nnTrainedModels/ModelWith60000");
            LOGGER.info("Loading from saved model is done");
        }
    }

    private void initSparkSession() {
        if (sparkSession == null) {
            sparkSession = SparkSession.builder()
                    .master("local[*]")
                    .appName("Digit Recognizer")
                    .getOrCreate();
        }

        sparkSession.sparkContext().setCheckpointDir("checkPoint");
    }

    public LabeledImage predict(LabeledImage labeledImage) {
        double predict = model.predict(labeledImage.getFeatures());
        labeledImage.setLabel(predict);
        return labeledImage;
    }
}
