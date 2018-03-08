package controller.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface BoardHandler extends EventHandler<ActionEvent>{
    void handle(ActionEvent event);
}
