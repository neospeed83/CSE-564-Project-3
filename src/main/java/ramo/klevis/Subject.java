package ramo.klevis;

public interface Subject {

    public void addObserver(Watcher watcher);

    public void removeObserver(Watcher watcher);

    public void notifyObservers();
}
