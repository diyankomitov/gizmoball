package controller.handlers.runhandlers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.board.gizmos.Gizmo;
import util.KeyPress;
import util.Triggers;

public class TriggerHandler implements EventHandler<KeyEvent>{

    public TriggerHandler() {

    }

    @Override
    public void handle(KeyEvent event) {
        for (Gizmo gizmo : Triggers.getTriggeredGizmo(new KeyPress(event.getCode(), event.getEventType()))) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                gizmo.trigger(true, false);
            }
            else {
                if (gizmo.getKeyPressed()) {
                    gizmo.trigger(true, true);
                }
                else {
                    gizmo.trigger(false, true);
                }
            }
        }
    }
}