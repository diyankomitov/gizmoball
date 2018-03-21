package controller.handlers.boardhandlers;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.Ball;
import view.BallView;
import view.gizmoviews.GizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class MoveHandler implements BoardHandler {

    private final GizmoballModel model;
    private boolean firstClick;
    private double firstx;
    private double firsty;
    private boolean gizmo;
    private GizmoView gizmoView;
    private BallView ballView;
    private final Label infoLabel;

    public MoveHandler(GizmoballModel model, ToggleButton moveButton) {
        this.model = model;
        this.infoLabel = new Label();
        firstClick = false;

        moveButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (gizmoView != null) {
                    gizmoView.setSelected(false);
                }
                if (ballView != null) {
                    ballView.setSelected(false);
                }
            }
        });
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {


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
                    if (firstClick) {
                        gizmoView = (GizmoView) clicked;
                        gizmoView.setSelected(true);
                    }
                }

            } else {
                firstClick = false;
                if (gizmo) {
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
