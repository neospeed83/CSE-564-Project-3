package ramo.klevis;

import java.util.Observable;

public class MessageObservable extends Observable {

    /**
     * Constructor for the MessageObservable class.
     * It is meant to be a wrapper for the Observable class because
     * Java does not support multiple inheritance.
     */
    public MessageObservable() {
        super();
    }

    /**
     * Lets the observable's observers know that something has changed.
     *
     * @param data The data to push to the observers.
     */
    void changeData(Object data) {
        setChanged();
        notifyObservers(data);
    }
}
