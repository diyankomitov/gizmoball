package controller;

import controller.handlers.boardhandlers.BoardHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.BoardView;

public class BoardController implements EventHandler<ActionEvent>{

    private BoardView boardView;
    private BoardHandler boardHandler;

    public BoardController() {
        this.boardView = new BoardView();
    }

    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    @Override
    public void handle(ActionEvent event) {
        boardHandler.handle(event);
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
