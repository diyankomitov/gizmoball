package view;

import javafx.scene.Group;
import model.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;

public class GizmoView extends Group {

    public GizmoView(Gizmo gizmo) {
        this.setTranslateX(gizmo.getXCoord()*ONE_L_IN_PIXELS);
        this.setTranslateY(gizmo.getYCoord()*ONE_L_IN_PIXELS);
//        this.setOnMousePressed(event -> controller.onMousePressed());
//        this.setOnDragDetected(event -> controller.onDragDetected());
//        this.setOnMouseDragged(controller::onMouseDragged);
//        this.setOnMouseReleased(event -> controller.onMouseReleased());
    }

}
