package controller.handlers.runhandlers;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StartLoopHandler implements EventHandler<ActionEvent> {
    private final Timeline timeline;

    public StartLoopHandler(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public void handle(ActionEvent event) {
        timeline.play();
    }
}
