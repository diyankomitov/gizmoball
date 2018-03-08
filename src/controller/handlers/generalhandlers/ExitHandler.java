package controller.handlers.generalhandlers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ExitHandler implements EventHandler<ActionEvent> {
    public ExitHandler() {

    }

    @Override
    public void handle(ActionEvent event) {
        System.exit(0); //TODO: Add more functionality, like save before exit and other cleanup
    }
}
