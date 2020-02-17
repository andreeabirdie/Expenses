package obs;

public abstract class Observable {
    public abstract void addObserver(Observer obs);
    public abstract void deleteObserver(Observer obs);
    public abstract void notifyObservers();

}
