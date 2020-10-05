package ramo.klevis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by klevis.ramo on 11/24/2017.
 */
public class Driver {

    private final static Logger LOGGER = LoggerFactory.getLogger(Driver.class);
    private static JFrame mainFrame = new JFrame();

    public static void main(String[] args) throws Exception {

        LOGGER.info("Application is starting ... ");

        NeuralNetwork neuralNetwork = new NeuralNetwork();
        ConvolutionalNeuralNetwork convolutionalNeuralNetwork = new ConvolutionalNeuralNetwork();

        setHadoopHomeEnvironmentVariable();
        LoadingBarView loadingBarView = new LoadingBarView(mainFrame, true);
        loadingBarView.showProgressBar("Collecting data this make take several seconds!");
        DigitRecognizerView digitRecognizerView =
                new DigitRecognizerView();
        Executors.newCachedThreadPool().submit(()->{
            try {
                digitRecognizerView.initUI();
            } finally {
                loadingBarView.setVisible(false);
                mainFrame.dispose();
            }
        });
    }


    private static void setHadoopHomeEnvironmentVariable() throws Exception {
        HashMap<String, String> hadoopEnvSetUp = new HashMap<>();
        hadoopEnvSetUp.put("HADOOP_HOME", new File("resources/winutils-master/hadoop-2.8.1").getAbsolutePath());
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
