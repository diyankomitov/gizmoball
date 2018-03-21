package controller.handlers.runhandlers;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.BoardView;

public class StartLoopHandler implements EventHandler<ActionEvent> {
    private final Timeline timeline;
    private final BoardView boardView;

    public StartLoopHandler(Timeline timeline, BoardView boardView) {
        this.timeline = timeline;
        this.boardView = boardView;
    }

    @Override
    public void handle(ActionEvent event) {
        timeline.play();
        boardView.requestFocus();
    }
}
