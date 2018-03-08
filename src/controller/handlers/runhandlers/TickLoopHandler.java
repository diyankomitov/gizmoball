package controller.handlers.runhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GizmoballModel;

public class TickLoopHandler implements EventHandler<ActionEvent> {
    private final GizmoballModel model;

    public TickLoopHandler(GizmoballModel model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {
        model.moveBalls();
    }
}
