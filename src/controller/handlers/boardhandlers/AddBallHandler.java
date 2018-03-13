package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.Ball;
import model.board.BoardObjectType;
import view.*;

import static util.Constants.ONE_L_IN_PIXELS;

public class AddBallHandler extends AddHandler {

    public AddBallHandler(GizmoballModel model, BoardController boardController) {
        super(model, boardController, BoardObjectType.BALL);
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;

            double x = mouseEvent.getX() / ONE_L_IN_PIXELS;
            double y = mouseEvent.getY() / ONE_L_IN_PIXELS;

            if (model.addBall(x, y, 0, 0, "")) {
                Ball ball = model.getBall(x, y);
                boardController.addToBoardView(new BallView(ball));
            }
        }
    }

    @Override
    public void handle(String name) {
        Ball ball = model.getBall(name);
        boardController.addToBoardView(new BallView(ball));
    }
}
