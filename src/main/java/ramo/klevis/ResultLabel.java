package ramo.klevis;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ResultLabel extends JLabel implements Observer {

    /**
     * Sets up the look of the result label.
     */
    public ResultLabel() {
        this.setForeground(Color.RED);
        this.setFont(new Font("SansSerif", Font.BOLD, 128));
    }

    /**
     * Updates the text of the result label.
     *
     * @param o    The observable that requested the change.
     * @param data The new value of the result label.
     */
    @Override
    public void update(Observable o, Object data) {
        this.setText("" + data);
    }
}
