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
        //here we can do a chain of responsibility or something; the UI shouldn't work until the neural networks
        //are completely init
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        ConvolutionalNeuralNetwork convolutionalNeuralNetwork = new ConvolutionalNeuralNetwork();

        setHadoopHomeEnvironmentVariable();
        DigitRecognizerView digitRecognizerView = new DigitRecognizerView();
        LoadingBarView loadingBarView = new LoadingBarView(digitRecognizerView.getMainFrame());
        loadingBarView.showProgressBar("Collecting data... this may take several seconds!");
        // or maybe an observer - in any case; you need to make the application wait before showing the UI
        Executors.newCachedThreadPool().submit(()->{
            try {

                Controller c = new Controller(digitRecognizerView, neuralNetwork, convolutionalNeuralNetwork);
            }
            catch(Exception e)
            {
                LOGGER.error("Something went wrong.");
            }
            finally {
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
