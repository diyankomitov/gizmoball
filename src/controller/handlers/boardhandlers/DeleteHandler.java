package controller.handlers.boardhandlers;


import controller.BoardController;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import view.BallView;
import view.gizmoviews.GizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class DeleteHandler implements BoardHandler{
    private final BoardController boardController;
    private final GizmoballModel model;

    public DeleteHandler(BoardController boardController, GizmoballModel model) {
        this.boardController = boardController;
        this.model = model;
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {

            Node clicked = ((Node)event.getTarget());
            double x = (int)(clicked.getTranslateX()/ONE_L_IN_PIXELS);
            double y = (int)(clicked.getTranslateY()/ONE_L_IN_PIXELS);

            if (model.removeBall(clicked.getParent().getTranslateX()/ONE_L_IN_PIXELS, clicked.getParent().getTranslateY()/ONE_L_IN_PIXELS)) {
                boardController.removeFromBoardView((BallView) clicked.getParent());
            }
            else if(model.removeGizmo(x,y)) {
                boardController.removeFromBoardView((GizmoView) clicked);
            }
        }
    }
}
