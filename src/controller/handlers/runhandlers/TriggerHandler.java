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
                System.out.println("key " + true + " " + false);
                gizmo.trigger(true, false);
            }
            else {
                if (gizmo.getKeyPressed()) {
                    System.out.println("key " + true + " " + true);
                    gizmo.trigger(true, true);
                }
                else {
                    System.out.println("key " + false + " " + true);
                    gizmo.trigger(false, true);
                }
            }
        }
    }
}