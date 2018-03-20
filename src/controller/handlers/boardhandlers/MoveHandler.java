package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.Ball;
import model.board.BoardObjectType;
import view.BallView;
import view.gizmoviews.AbsorberGizmoView;
import view.gizmoviews.GizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class MoveHandler implements BoardHandler {

    private final GizmoballModel model;
    boolean firstClick;
    private double firstx;
    private double firsty;
    private boolean gizmo;
    private GizmoView gizmoView;
    private BallView ballView;
    private Label infoLabel;

    public MoveHandler(GizmoballModel model, Label infoLabel) {
        this.model = model;
        this.infoLabel = infoLabel;
        firstClick = false;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            Node test = ((Node)event.getTarget());
            System.out.println(test);


            MouseEvent mouseEvent = (MouseEvent) event;
            double mouseX = mouseEvent.getX()/ONE_L_IN_PIXELS;
            double mouseY = mouseEvent.getY()/ONE_L_IN_PIXELS;

            Node clicked = ((Node)event.getTarget());
            double nodeX = clicked.getTranslateX()/ONE_L_IN_PIXELS;
            double nodeY = clicked.getTranslateY()/ONE_L_IN_PIXELS;

            if (!firstClick) {

                Ball ball = model.getBall(mouseX, mouseY);

                if (ball != null) {
                    firstClick = true;
                    gizmo = false;
                    firstx = mouseX;
                    firsty = mouseY;
                    ballView = (BallView) clicked.getParent();
                    ballView.setSelected(true);

                }
                else {
                    gizmo = true;
                    firstClick = model.getGizmo(Math.floor(nodeX), Math.floor(nodeY)) != null;
                    firstx = Math.floor(nodeX);
                    firsty = Math.floor(nodeY);
                    gizmoView = (GizmoView) clicked;
                    gizmoView.setSelected(true);
                }


                System.out.println(firstx + " " + firsty);
            } else {
                firstClick = false;
                if (gizmo) {
                    System.out.println(firstx + " " + firsty + " " + Math.floor(mouseX)+ " " + Math.floor(mouseY));
                    model.moveGizmo(firstx, firsty, Math.floor(mouseX), Math.floor(mouseY));
                    gizmoView.setSelected(false);
                    gizmoView = null;
                }
                else {
                    model.moveBall(firstx, firsty, mouseX, mouseY);
                    ballView.setSelected(false);
                    ballView = null;
                }
            }
            if(!model.getErrorMessage().equals("")){
                infoLabel.setText(model.getErrorMessage());
                model.setMessage("");
            } else {
                infoLabel.setText(" ");
            }
        }
    }
}
