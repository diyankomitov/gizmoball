package controller.handlers.boardhandlers;

import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.gizmos.Gizmo;
import util.Triggers;

import static util.Constants.ONE_L_IN_PIXELS;

public class ConnectTriggerHandler implements BoardHandler {
    private final GizmoballModel model;
    private boolean triggeredSelected;
    private Gizmo triggeredGizmo;
    private Label infoLabel;

    public ConnectTriggerHandler(GizmoballModel model, Label infoLabel) {
        this.model = model;
        triggeredSelected = false;
        this.infoLabel = infoLabel;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;
            double x = Math.floor(mouseEvent.getX() / ONE_L_IN_PIXELS);
            double y = Math.floor(mouseEvent.getY() / ONE_L_IN_PIXELS);

            Gizmo gizmo = model.getGizmo(x, y);

            if (!triggeredSelected) {
                if (gizmo != null) {
                    triggeredSelected = true;
                    triggeredGizmo = gizmo;
                    infoLabel.setText(triggeredGizmo.getName() + " selected. Please select another gizmo or press a key to connect to.");
                }
            }
            else {
                if (gizmo != null) {
                    triggeredSelected = false;
                    Triggers.addTrigger(gizmo, triggeredGizmo);
                    infoLabel.setText(triggeredGizmo.getName() + " and " + gizmo.getName() + " have been connected.");
                }
            }
        }
        else if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            KeyCode keyCode = keyEvent.getCode();

            if (keyCode != KeyCode.ESCAPE) {
                if (triggeredSelected){
                    Triggers.addTrigger(keyCode, triggeredGizmo);
                    infoLabel.setText(keyCode + " and " + triggeredGizmo.getName() + " have been connected.");
                }
            }
            triggeredSelected = false;
        }
    }
}
