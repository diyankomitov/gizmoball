package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.Ball;
import model.board.BoardObjectType;
import view.gizmoviews.AbsorberGizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class MoveHandler implements BoardHandler {

    private final GizmoballModel model;
    boolean firstClick;
    private double firstx;
    private double firsty;
    private boolean gizmo;

    public MoveHandler(GizmoballModel model) {
        this.model = model;
        firstClick = false;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;
            double mouseX = mouseEvent.getX()/ONE_L_IN_PIXELS;
            double mouseY = mouseEvent.getY()/ONE_L_IN_PIXELS;

            Node clicked = ((Node)event.getTarget()).getParent();
            double nodeX = clicked.getTranslateX()/ONE_L_IN_PIXELS;
            double nodeY = clicked.getTranslateY()/ONE_L_IN_PIXELS;

            if (!firstClick) {

                Ball ball = model.getBall(nodeX, nodeY);

                if (ball != null) {
                    firstClick = true;
                    gizmo = false;
                    firstx = nodeX;
                    firsty = nodeY;
                }
                else {
                    gizmo = true;
                    firstClick = model.getGizmo(Math.floor(nodeX), Math.floor(nodeY)) != null;
                    firstx = Math.floor(nodeX);
                    firsty = Math.floor(nodeY);
                }


                System.out.println(firstx + " " + firsty);
            } else {
                firstClick = false;
                if (gizmo) {
                    System.out.println(firstx + " " + firsty + " " + Math.floor(mouseX)+ " " + Math.floor(mouseY));
                    model.moveGizmo(firstx, firsty, Math.floor(mouseX), Math.floor(mouseY));
                }
                else {
                    model.moveBall(firstx, firsty, mouseX, mouseY);
                }
            }

        }
    }
}
