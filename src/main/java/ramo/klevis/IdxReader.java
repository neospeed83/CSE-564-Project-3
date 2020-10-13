package ramo.klevis;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdxReader {

    private final static Logger LOGGER = Logger.getLogger(IdxReader.class.getName());

    public static final String INPUT_IMAGE_PATH = "resources/train-images.idx3-ubyte";
    public static final String INPUT_LABEL_PATH = "resources/train-labels.idx1-ubyte";

    public static final String INPUT_IMAGE_PATH_TEST_DATA = "resources/t10k-images.idx3-ubyte";
    public static final String INPUT_LABEL_PATH_TEST_DATA = "resources/t10k-labels.idx1-ubyte";

    public static final int VECTOR_DIMENSION = 784; //square 28*28 as from data set -> array 784 items


    public static List<LabeledImage> loadData(final int size) {
        return getLabeledImages(INPUT_IMAGE_PATH, INPUT_LABEL_PATH, size);
    }


    private static List<LabeledImage> getLabeledImages(final String inputImagePath,
                                                       final String inputLabelPath,
                                                       final int amountOfDataSet) {

        final List<LabeledImage> labeledImageArrayList = new ArrayList<>(amountOfDataSet);

        try (FileInputStream inImage = new FileInputStream(inputImagePath);
             FileInputStream inLabel = new FileInputStream(inputLabelPath)) {

            inImage.skip(16);
            inLabel.skip(8);
            LOGGER.log(Level.parse("DEBUG"), "Available bytes in inputImage stream after read: " + inImage.available());
            LOGGER.log(Level.parse("DEBUG"), "Available bytes in inputLabel stream after read: " + inLabel.available());

            double[] imgPixels = new double[VECTOR_DIMENSION];

            LOGGER.info("Creating ADT filed with Labeled Images ...");
            long start = System.currentTimeMillis();
            for (int i = 0; i < amountOfDataSet; i++) {

                if (i % 1000 == 0) {
                    LOGGER.info("Number of images extracted: " + i);
                }

                for (int index = 0; index < VECTOR_DIMENSION; index++) {
                    imgPixels[index] = inImage.read();
                }

                int label = inLabel.read();
                labeledImageArrayList.add(new LabeledImage(label, imgPixels));
            }
            LOGGER.info("Time to load LabeledImages in seconds: " + ((System.currentTimeMillis() - start) / 1000d));
        } catch (Exception e) {
            LOGGER.log(Level.parse("SEVERE"),"Something went wrong: \n" + e);
            throw new RuntimeException(e);
        }

        return labeledImageArrayList;
    }

}