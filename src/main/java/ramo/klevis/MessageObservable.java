package ramo.klevis;

import java.util.Observable;

public class MessageObservable extends Observable {

    public MessageObservable() {
        super();
    }

    void changeData(Object data){
        setChanged();
        notifyObservers(data);
    }
}
