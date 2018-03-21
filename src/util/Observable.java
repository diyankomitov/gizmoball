package util;

import java.util.*;

public interface Observable {

    default void notifyObservers() {
        for (Observer observer : getObservers()) {
            observer.update();
        }
    }

    default void subscribe(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!getObservers().contains(observer)) {
            getObservers().add(observer);
        }
    }

    default void unsubscribe(Observer observer) {
        getObservers().remove(observer);
    }

    List<Observer> getObservers();
}
