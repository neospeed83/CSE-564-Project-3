package ramo.klevis;

import javax.swing.*;
import java.awt.*;

/**
 * Created by klevis.ramo on 11/29/2017.
 */
public class LoadingBarView {

    private final JFrame mainFrame;
    private JProgressBar progressBar;
    private boolean unDecorate = false;

    public LoadingBarView(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        progressBar = createProgressBar(mainFrame);
    }

    public LoadingBarView(JFrame mainFrame, boolean unDecorate) {
        this.mainFrame = mainFrame;
        progressBar = createProgressBar(mainFrame);
        this.unDecorate = unDecorate;
    }

    public void showProgressBar(String msg) {
        SwingUtilities.invokeLater(() -> {
            if (unDecorate) {
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setUndecorated(true);
            }
            progressBar = createProgressBar(mainFrame);
            progressBar.setString(msg);
            progressBar.setStringPainted(true);
            progressBar.setIndeterminate(true);
            progressBar.setVisible(true);
            mainFrame.add(progressBar, BorderLayout.NORTH);
            if (unDecorate) {
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
            mainFrame.repaint();
        });
    }


    private JProgressBar createProgressBar(JFrame mainFrame) {
        JProgressBar jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        jProgressBar.setVisible(false);
        mainFrame.add(jProgressBar, BorderLayout.NORTH);
        return jProgressBar;
    }

    public void setVisible(boolean visible) {
        progressBar.setVisible(visible);
    }
}
