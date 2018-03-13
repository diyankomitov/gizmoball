package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.BoardObjectType;

import static util.Constants.ONE_L_IN_PIXELS;

public class RotateHandler implements BoardHandler{
    private final GizmoballModel model;

    public RotateHandler(GizmoballModel model) {
        this.model = model;
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;

            double x = (int)(mouseEvent.getX()/ONE_L_IN_PIXELS);
            double y = (int)(mouseEvent.getY()/ONE_L_IN_PIXELS);

            model.rotateGizmo(x,y);
        }
    }
}
