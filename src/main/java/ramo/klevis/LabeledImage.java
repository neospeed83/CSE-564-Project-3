package ramo.klevis;

import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;

import java.io.Serializable;

public class LabeledImage implements Serializable {
    private final double[] pixels;
    private final Vector features;
    private double label;

    /**
     * Constructor for the neural network's output.
     *
     * @param label  The label of the image.
     * @param pixels The pixel content of the image.
     */
    public LabeledImage(int label, double[] pixels) {
        double[] meanNormalizedPixel = meanNormalizeFeatures(pixels);
        this.pixels = pixels;
        features = Vectors.dense(meanNormalizedPixel);
        this.label = label;
    }

    /**
     * Gets the pixels of the image.
     *
     * @return Array of doubles of the image.
     */
    public double[] getPixels() {
        return pixels;
    }

    /**
     * Normalizes the pixels in the image.
     *
     * @param pixels The pixels to normalize.
     * @return The pixels normalized.
     */
    private double[] meanNormalizeFeatures(double[] pixels) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0;
        for (double pixel : pixels) {
            sum = sum + pixel;
            if (pixel > max) {
                max = pixel;
            }
            if (pixel < min) {
                min = pixel;
            }
        }
        double mean = sum / pixels.length;

        double[] pixelsNorm = new double[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            pixelsNorm[i] = (pixels[i] - mean) / (max - min);
        }
        return pixelsNorm;
    }

    /**
     * Gets the features of the image.
     *
     * @return The features of the image.
     */
    public Vector getFeatures() {
        return features;
    }

    /**
     * Gets the label of the image.
     *
     * @return The label of the image.
     */
    public double getLabel() {
        return label;
    }

    /**
     * Sets the label of the image.
     *
     * @param label The value to set the label to.
     */
    public void setLabel(double label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "LabeledImage{" +
                "label=" + label +
                '}';
    }
}
