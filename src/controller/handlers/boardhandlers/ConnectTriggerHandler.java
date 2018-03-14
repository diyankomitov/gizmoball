package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.BoardObjectType;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;


public class ConnectTriggerHandler implements BoardHandler{
    protected final GizmoballModel model;
    protected final BoardController boardController;
    protected final BoardObjectType type;

    public ConnectTriggerHandler(GizmoballModel model, BoardController boardController, BoardObjectType type) {
        this.model = model;
        this.boardController = boardController;
        this.type = type;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;

            double x = (int)(mouseEvent.getX()/ONE_L_IN_PIXELS);
            double y = (int)(mouseEvent.getY()/ONE_L_IN_PIXELS);

            if (model.getGizmo(x,y) != null){
                Gizmo gizmo = model.getGizmo(x,y);
                System.out.println(gizmo);
            }
        }

    }
}
