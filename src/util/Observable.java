package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();

    default void notifyObservers() {
        observers.forEach(Observer::update);
    }

    default void subscribe(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    default void unsubscribe(Observer observer) {
        observers.remove(observer);
    }
}
