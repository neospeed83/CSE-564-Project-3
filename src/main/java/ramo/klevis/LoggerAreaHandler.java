package ramo.klevis;

import java.util.Observer;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class LoggerAreaHandler extends StreamHandler {
    MessageObservable observable = new MessageObservable();

    LoggerAreaHandler(Observer o)
    {
        observable.addObserver(o);
    }


    @Override
    public void publish(LogRecord record)
    {
        observable.changeData(record.getMessage());
    }
}
