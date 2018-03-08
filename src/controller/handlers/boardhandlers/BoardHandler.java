package controller.handlers.boardhandlers;

import javafx.event.Event;
import javafx.event.EventHandler;

public interface BoardHandler extends EventHandler<Event>{
    void handle(Event event);
}
