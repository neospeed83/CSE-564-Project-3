package ramo.klevis;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ResultLabel extends JLabel implements Observer{

    public ResultLabel() {
        this.setForeground(Color.RED);
        this.setFont(new Font("SansSerif", Font.BOLD, 128));
    }

    @Override
    public void update(Observable o, Object data) {
        this.setText(""+data);
    }
}
