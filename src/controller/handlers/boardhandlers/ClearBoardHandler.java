package controller.handlers.boardhandlers;

import controller.BoardController;
import javafx.event.Event;
import model.GizmoballModel;

public class ClearBoardHandler implements BoardHandler {
    private final BoardController boardController;
    private final GizmoballModel model;

    public ClearBoardHandler(BoardController boardController, GizmoballModel model) {
        this.boardController = boardController;
        this.model = model;
    }

    @Override
    public void handle(Event event) {
        this.boardController.clearBoardView();
        this.model.clearBoard();
    }
}
