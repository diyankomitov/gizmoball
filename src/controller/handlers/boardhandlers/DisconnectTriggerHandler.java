package controller.handlers.boardhandlers;

import javafx.event.Event;
import javafx.scene.Node;
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
    private final Label infoLabel;

    public DisconnectTriggerHandler(GizmoballModel model, Label infoLabel) {
        this.model = model;
        this.infoLabel = infoLabel;
    }

    @Override
    public void handle(Event event) {

        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            Node clicked = ((Node)event.getTarget());
            double nodeX = Math.floor(clicked.getTranslateX()/ONE_L_IN_PIXELS);
            double nodeY = Math.floor(clicked.getTranslateY()/ONE_L_IN_PIXELS);



            Gizmo gizmo = model.getGizmo(nodeX, nodeY);

            if (gizmo != null) {
                Triggers.removeTriggers(gizmo);
                infoLabel.setText(gizmo.getName() + " has had its triggers removed.");
            }

        }
    }
}
