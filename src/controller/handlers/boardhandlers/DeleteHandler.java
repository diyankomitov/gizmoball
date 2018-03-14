package controller.handlers.boardhandlers;


import controller.BoardController;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
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

            Node clicked = ((Node)event.getTarget()).getParent();
            double x = (int)(clicked.getTranslateX()/ONE_L_IN_PIXELS);
            double y = (int)(clicked.getTranslateY()/ONE_L_IN_PIXELS);

//TODO add info label for remove
            if (model.removeGizmo(x,y)) {
                boardController.removeFromBoardView((GizmoView) clicked);
            }
            else if(model.removeBall(clicked.getTranslateX()/ONE_L_IN_PIXELS, clicked.getTranslateY()/ONE_L_IN_PIXELS)){
                boardController.removeFromBoardView((BallView) clicked);

            }
        }
    }
}
