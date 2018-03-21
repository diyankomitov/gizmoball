package view.gizmoviews;

import javafx.scene.shape.Rectangle;
import model.board.gizmos.AbsorberGizmo;
import model.board.gizmos.Gizmo;

import static util.Constants.ONE_L_IN_PIXELS;

public class AbsorberGizmoView extends GizmoViewContainer{

    private final Rectangle rectangle;

    public AbsorberGizmoView(Gizmo absorberGizmo) {
        super(absorberGizmo);

        AbsorberGizmo absorber = (AbsorberGizmo) absorberGizmo;

        double width, height;

        if (gizmo != null) {
            width = absorber.getWidth() * ONE_L_IN_PIXELS;
            height = absorber.getHeight() * ONE_L_IN_PIXELS;
        }
        else {
            width = ONE_L_IN_PIXELS;
            height = ONE_L_IN_PIXELS;
        }


        rectangle = new Rectangle(width, height);
        rectangle.getStyleClass().add("absorberGizmo");
        rectangle.setMouseTransparent(true);
        this.getChildren().add(rectangle);

    }

    public AbsorberGizmoView() {
        this(null);
//        square.setStyle("-fx-stroke: red; -fx-stroke-type: inside; -fx-stroke-width: 2");
    }

    @Override
    public void update() {
        super.update();
    }

    public void setWidth(double width) {
        rectangle.setWidth(width);
    }

    public void setHeight(double height) {
        rectangle.setHeight(height);
    }
}