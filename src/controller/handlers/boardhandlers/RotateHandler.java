package controller.handlers.boardhandlers;

import controller.BuildController;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.GizmoballModel;

import static util.Constants.ONE_L_IN_PIXELS;

public class RotateHandler implements BoardHandler{
    private final GizmoballModel model;
    private final BuildController buildController;

    public RotateHandler(GizmoballModel model,  BuildController buildController) {
        this.model = model;
        this.buildController = buildController;
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {

            Node clicked = ((Node)event.getTarget());
            double nodeX = Math.floor(clicked.getTranslateX()/ONE_L_IN_PIXELS);
            double nodeY = Math.floor(clicked.getTranslateY()/ONE_L_IN_PIXELS);


            model.rotateGizmo(nodeX,nodeY);
            if(!model.getErrorMessage().equals("")){
                buildController.setInformation(model.getErrorMessage());
                model.setMessage("");
            } else {
                buildController.setInformation(" ");
            }
        }
    }
}
