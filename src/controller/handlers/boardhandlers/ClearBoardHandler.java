package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import javafx.scene.control.Label;
import model.GizmoballModel;

public class ClearBoardHandler implements BoardHandler {
    private final BoardController boardController;
    private final GizmoballModel model;
    private final Label information;

    public ClearBoardHandler(BoardController boardController, GizmoballModel model, Label information) {
        this.information = information;
        this.boardController = boardController;
        this.model = model;
    }

    @Override
    public void handle(Event event) {
        this.boardController.clearBoardView();
        this.model.clearBoard();
        information.setText("Board Cleared");
    }
}
