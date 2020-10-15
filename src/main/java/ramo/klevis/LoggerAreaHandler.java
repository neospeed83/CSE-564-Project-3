package ramo.klevis;

import java.util.Observer;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class LoggerAreaHandler extends StreamHandler {
    MessageObservable observable = new MessageObservable();

    /**
     * Constructor for the handler, adds an observer immediately.
     *
     * @param o The observer to add. We use the text area as the observer in this case.
     */
    LoggerAreaHandler(Observer o) {
        observable.addObserver(o);
    }

    /**
     * Whenever the log is updated, we push it to the LoggerArea.
     *
     * @param record The new message to push to the LoggerArea.
     */
    @Override
    public void publish(LogRecord record) {
        observable.changeData(record.getMessage());
    }
}
