package view.gizmoviews;

import javafx.beans.NamedArg;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.board.BoardObjectType;
import model.board.gizmos.Gizmo;

import static javafx.scene.paint.Color.ORANGE;
import static util.Constants.ONE_L_IN_PIXELS;

public class FlipperGizmoView extends GizmoViewContainer{

    private Rotate flip;

    public FlipperGizmoView(Gizmo flipperGizmo) {
        super(flipperGizmo);

        double width = ONE_L_IN_PIXELS/2;
        double length = ONE_L_IN_PIXELS*2;

        Rectangle rectangle = new Rectangle(width, length);
        rectangle.setArcWidth(width);
        rectangle.setArcHeight(width);
        rectangle.setFill(ORANGE);

        if (flipperGizmo.getType() == BoardObjectType.RIGHT_FLIPPER) {
            rectangle.setX(length-width);
        }

        this.getChildren().add(rectangle);

        flip = new Rotate(0, width/2, width/2);
        this.getTransforms().add(flip);
    }

    public FlipperGizmoView(@NamedArg("type") BoardObjectType type) {
        super(null);

        double width = ONE_L_IN_PIXELS/2;
        double length = ONE_L_IN_PIXELS*2;

        Rectangle rectangle = new Rectangle(width, length);
        rectangle.setArcWidth(width);
        rectangle.setArcHeight(width);
        rectangle.setFill(ORANGE);

        if (type == BoardObjectType.RIGHT_FLIPPER) {
            rectangle.setX(length-width);
        }

        this.getChildren().add(rectangle);


//        this(null);
//        square.setStyle("-fx-stroke: red; -fx-stroke-type: inside; -fx-stroke-width: 2");
    }

    @Override
    public void update() {
        super.update();
        if (flip != null) {
            flip.setAngle(gizmo.getAngle());
        }
    }
}
