package controller.handlers.boardhandlers;

import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;
import model.board.gizmos.Gizmo;
import util.Triggers;

import static util.Constants.ONE_L_IN_PIXELS;

public class DisconnectTriggerHandler implements BoardHandler {
    private final GizmoballModel model;
    //private boolean triggeredSelected;
   // private Gizmo triggeredGizmo;
    private Label infoLabel;

    public DisconnectTriggerHandler(GizmoballModel model, Label infoLabel) {
        this.model = model;
        this.infoLabel = infoLabel;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            MouseEvent mouseEvent = (MouseEvent) event;
            double x = Math.floor(mouseEvent.getX() / ONE_L_IN_PIXELS);
            double y = Math.floor(mouseEvent.getY() / ONE_L_IN_PIXELS);

            Gizmo gizmo = model.getGizmo(x, y);

            if (gizmo != null) {
                Triggers.removeTriggers(gizmo);
                infoLabel.setText(gizmo + " has had its triggers removed.");
            }

        }
    }
}
