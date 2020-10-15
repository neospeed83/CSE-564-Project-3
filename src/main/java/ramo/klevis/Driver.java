package ramo.klevis;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Driver {

    private static final String HADOOP_HOME = "resources/winutils-master/hadoop-2.8.1";
    private final static Logger LOGGER = Logger.getLogger(Driver.class.getName());
    public static LoggerAreaHandler loggerAreaHandler;

    public static void main(String[] args) throws Exception {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
        UIManager.put("LoadingBarView.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));

        DigitRecognizerView digitRecognizerView = new DigitRecognizerView();
        loggerAreaHandler = new LoggerAreaHandler(digitRecognizerView.getLoggerArea());
        LOGGER.addHandler(loggerAreaHandler);
        LOGGER.info("Application is starting ... ");
        LOGGER.info("The GUI will be unresponsive until the neural networks are initialized.");
        setHadoopHomeEnvironmentVariable();
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        ConvolutionalNeuralNetwork convolutionalNeuralNetwork = new ConvolutionalNeuralNetwork();

        new Controller(digitRecognizerView, neuralNetwork, convolutionalNeuralNetwork);
    }

    /**
     * Sets the HADOOP environment variable
     *
     * @throws Exception
     */
    private static void setHadoopHomeEnvironmentVariable() throws Exception {
        HashMap<String, String> hadoopEnvSetUp = new HashMap<>();
        hadoopEnvSetUp.put("HADOOP_HOME", new File(HADOOP_HOME).getAbsolutePath());
        Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
        Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
        theEnvironmentField.setAccessible(true);
        Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
        env.clear();
        env.putAll(hadoopEnvSetUp);
        Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
        theCaseInsensitiveEnvironmentField.setAccessible(true);
        Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
        cienv.clear();
        cienv.putAll(hadoopEnvSetUp);
    }
}
