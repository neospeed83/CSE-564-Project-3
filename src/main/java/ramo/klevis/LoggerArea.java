package ramo.klevis;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class LoggerArea extends JTextArea implements Observer {

    LoggerArea(){
        super();
        this.append("LOGGER:\n");
        this.setLineWrap(true);
        this.setEditable(false);
    }

    @Override
    public void update(Observable observable, Object data) {
        this.append("" + data);
    }
}
