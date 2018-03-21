package controller;

import controller.handlers.boardhandlers.BoardHandler;
import javafx.event.Event;
import javafx.event.EventHandler;
import view.BallView;
import view.BoardView;
import view.gizmoviews.GizmoView;

public class BoardController implements EventHandler<Event>{

    private final BoardView boardView;
    private BoardHandler boardHandler;
    private boolean doNothing;

    public BoardController() {
        this.boardView = new BoardView();
        boardView.addEventHandler(Event.ANY, this);
        doNothing = true;
    }

    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
        doNothing = false;
    }

    public void setDoNothing() {
        this.doNothing = true;
    }

    @Override
    public void handle(Event event) {
        if (doNothing) {
            event.consume();
        }
        else {
            boardHandler.handle(event);
        }
    }

    public void addToBoardView(GizmoView gizmoView) {
        boardView.add(gizmoView);
    }

    public void addToBoardView(BallView ballView) {
        boardView.add(ballView);
    }

    public void removeFromBoardView(GizmoView gizmoView) {
        boardView.remove(gizmoView);
    }

    public void removeFromBoardView(BallView ballView) {
        boardView.remove(ballView);
    }

    public void clearBoardView() {
        boardView.clearBoard();
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
