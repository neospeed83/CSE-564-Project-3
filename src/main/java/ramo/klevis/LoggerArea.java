package ramo.klevis;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class LoggerArea extends JTextArea implements Observer {

    /**
     * Constructor for the TextArea, sets a few options.
     */
    LoggerArea() {
        super();
        this.append("LOGGER:\n");
        this.setLineWrap(true);
        this.setEditable(false);
    }

    /**
     * Whenever the handler notifies the text area, the text area's text is updated.
     * The scroll bar is pushed down to the end.
     *
     * @param observable The handler
     * @param data       The new text to append to the text area.
     */
    @Override
    public void update(Observable observable, Object data) {
        this.append("\n" + data);
        this.setCaretPosition(this.getDocument().getLength());
    }
}
