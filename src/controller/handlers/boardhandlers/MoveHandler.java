package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.BoardObjectType;
import view.gizmoviews.AbsorberGizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class MoveHandler implements BoardHandler {

    private final GizmoballModel model;
    boolean firstClick;
    private double firstx;
    private double firsty;

    public MoveHandler(GizmoballModel model) {
        this.model = model;
        firstClick = false;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;
            double x = Math.floor(mouseEvent.getX() / ONE_L_IN_PIXELS);
            double y = Math.floor(mouseEvent.getY() / ONE_L_IN_PIXELS);


            if (!firstClick) {
                firstClick = model.getGizmo(x, y) != null;  //TODO: check for move ball

                System.out.println(x + " " + y);
                firstx = x;
                firsty = y;
            } else {
                firstClick = false;
                System.out.println(x + " " + y);
                model.moveGizmo(firstx, firsty, x, y);
            }

        }
    }
}
