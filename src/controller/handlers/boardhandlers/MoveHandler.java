package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
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
    boolean ballTruegizmoFalse;

    public MoveHandler(GizmoballModel model) {
        this.model = model;
        firstClick = false;
        ballTruegizmoFalse = false;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;
            double x = Math.floor(mouseEvent.getX() / ONE_L_IN_PIXELS);
            double y = Math.floor(mouseEvent.getY() / ONE_L_IN_PIXELS);
            double preciseX = (mouseEvent.getX()/ONE_L_IN_PIXELS);
            double preciseY = (mouseEvent.getY()/ONE_L_IN_PIXELS);

            for(Ball ball : model.getBalls()){
                System.out.println("ball location: " + ball.getX() + " " + ball.getY()) ;
            }


            if (!firstClick) {
                firstClick = model.getBall(preciseX,preciseY) != null;
                if(model.getBall(preciseX,preciseY) != null){
                    System.out.println("A BALL EXISTS HERE!");
                    ballTruegizmoFalse = true;
                    firstx = model.getBall(preciseX,preciseY).getX();
                    firsty = model.getBall(preciseX,preciseY).getY();
                }
                firstClick = model.getGizmo(x, y) != null;
                if(model.getGizmo(x,y) != null){
                    System.out.println("A GIZMO EXISTS HERE!");
                    ballTruegizmoFalse = false;
                    firstx = x;
                    firsty = y;
                }
                System.out.println(preciseX + " " + preciseY);
                System.out.println(x + " " + y);

            } else {
                firstClick = false;
                System.out.println(x + " " + y);
                if(ballTruegizmoFalse){
                    model.moveBall(firstx, firsty, preciseX, preciseY);
                }
                else {
                    model.moveGizmo(firstx, firsty, x, y);
                }
            }

        }
    }
}
