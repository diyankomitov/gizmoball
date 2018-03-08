package controller.handlers.runhandlers;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StopLoopHandler implements EventHandler<ActionEvent> {
    private final Timeline timeline;

    public StopLoopHandler(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public void handle(ActionEvent event) {
        timeline.stop();
    }
}
