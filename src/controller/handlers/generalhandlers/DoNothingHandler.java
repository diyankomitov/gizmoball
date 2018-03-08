package controller.handlers.generalhandlers;

import javafx.event.Event;
import javafx.event.EventHandler;

public class DoNothingHandler implements EventHandler<Event> {
    @Override
    public void handle(Event event) {
        event.consume();
    }
}
