package controller.handlers.generalhandlers;

import controller.BuildController;
import controller.RunController;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HorizontalDirection;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.geometry.HorizontalDirection.*;

/**
 * ActionEvent handler for switching between two modes
 */
public class SwitchModeHandler implements EventHandler<ActionEvent>{

    private final Pane from;
    private final Pane to;
    private final BuildController buildController;
    private final RunController runController;
    private final HorizontalDirection direction;
    private final TranslateTransition moveFrom;
    private final TranslateTransition moveTo;
    private final ParallelTransition switchTransition;

    /**
     * Constructor for the SwitchModeHandler
     * @requires from and to must be the only two children of a StackPane
     * @param buildController
     * @param runController
     * @param direction the direction of the animation
     */
    public SwitchModeHandler(BuildController buildController, RunController runController, HorizontalDirection direction) {
        this.buildController = buildController;
        this.runController = runController;
        this.direction = direction;

        if (direction == RIGHT) {
            from = buildController.getRoot();
            to = runController.getRoot();
        }
        else {
            from = runController.getRoot();
            to = buildController.getRoot();
        }

        moveFrom = new TranslateTransition(Duration.millis(500), from);
        moveFrom.setOnFinished(event -> from.toBack());
        moveTo = new TranslateTransition(Duration.millis(500), to);

        switchTransition = new ParallelTransition(moveFrom, moveTo);
    }


    @Override
    public void handle(ActionEvent event) {
        if (direction == RIGHT) {
            buildController.setDoNothing(true);
            runController.setDoNothing(false);
            runController.toggleBoard();
        }
        else {
            buildController.setDoNothing(false);
            runController.setDoNothing(true);
            buildController.toggleBoard();
        }


        int negate = direction == LEFT ? -1 : 1;
        moveFrom.setFromX(0);
        moveFrom.setToX(from.getWidth() * negate);
        moveTo.setFromX(-to.getWidth() * negate);
        moveTo.setToX(0);
        switchTransition.play();
    }
}
