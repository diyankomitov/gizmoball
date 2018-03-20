package controller.handlers.boardhandlers;

import controller.BuildController;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.Ball;
import view.BallView;
import view.gizmoviews.GizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class SelectBallHandler implements BoardHandler {

    private final GizmoballModel model;
    private BuildController buildController;
    private BallView ballView;

    public SelectBallHandler(GizmoballModel model, BuildController buildController, ToggleButton selectBallButton) {
        this.model = model;
        this.buildController = buildController;
        ballView = null;
        selectBallButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
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
            double mouseX = mouseEvent.getX() / ONE_L_IN_PIXELS;
            double mouseY = mouseEvent.getY() / ONE_L_IN_PIXELS;

            Node clicked = ((Node) event.getTarget());
            double nodeX = clicked.getTranslateX() / ONE_L_IN_PIXELS;
            double nodeY = clicked.getTranslateY() / ONE_L_IN_PIXELS;

            Ball ball = model.getBall(mouseX, mouseY);
            if (ball != null) {
                if (ballView != null) {
                    ballView.setSelected(false);
                }
                ballView = (BallView) clicked.getParent();
                ballView.setSelected(true);
                buildController.setSelectedBall(ball);
            }
        }
    }
}
