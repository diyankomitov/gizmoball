package controller.handlers.boardhandlers;

import controller.BoardController;
import controller.BuildController;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.Ball;
import model.board.BoardObjectType;
import view.*;

import static util.Constants.ONE_L_IN_PIXELS;

public class AddBallHandler extends AddHandler {
    private double vx;
    private double vy;
    private boolean global;


    public AddBallHandler(GizmoballModel model, BoardController boardController, BuildController buildController, Label infoLabel) {
        super(model, boardController, buildController, BoardObjectType.BALL, infoLabel);
        vx = 0;
        vy = 0;
        global = false;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;

            double x = mouseEvent.getX() / ONE_L_IN_PIXELS;
            double y = mouseEvent.getY() / ONE_L_IN_PIXELS;

            Node clicked = ((Node) event.getTarget());

                if (global) {
                    if (model.addBall(x, y, 0, 0, "")) {
                        Ball ball = model.getBall(x, y);
                        ball.setGlobalVelocity(true);
                        ball.setXVelocity(vx);
                        ball.setYVelocity(vy);
                        boardController.addToBoardView(new BallView(ball));
                    }
                } else {
                    if (model.addBall(x, y, vx, vy, "")) {
                        Ball ball = model.getBall(x, y);
                        boardController.addToBoardView(new BallView(ball));
                    }
                }

            if(!model.getErrorMessage().equals("")){
                buildController.setInformation(model.getErrorMessage());
                model.setMessage("");
            } else {
                buildController.setInformation(" ");
            }
        }
    }

    @Override
    public void handle(String name) {
        Ball ball = model.getBall(name);
        boardController.addToBoardView(new BallView(ball));
        if(!model.getErrorMessage().equals("")){
            buildController.setInformation(model.getErrorMessage());
            model.setMessage("");
        } else {
            buildController.setInformation(" ");
        }
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }
}
