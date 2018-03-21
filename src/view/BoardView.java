package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import view.gizmoviews.GizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class BoardView extends Pane{
    private final Group cellGroup;
    private boolean isGridOn;
    private final Group objectGroup;


    public BoardView() {
        double cellSize = ONE_L_IN_PIXELS;
        this.setPrefSize(cellSize *20, cellSize *20);
        this.setMinSize(cellSize *20, cellSize *20);
        this.setMaxSize(cellSize *20, cellSize *20);

        cellGroup = new Group();

        for (int y = 0; y<20; y++) {
            for (int x = 0; x<20; x++) {
                Pane cell = new Pane();
                cell.getStyleClass().add("grid");
                cell.setPrefSize(cellSize, cellSize);
                cell.setTranslateX(x*ONE_L_IN_PIXELS);
                cell.setTranslateY(y*ONE_L_IN_PIXELS);
                cellGroup.getChildren().add(cell);
                cellGroup.setMouseTransparent(true);
            }
        }
        this.getChildren().add(cellGroup);

        objectGroup = new Group();
        this.getChildren().add(objectGroup);

        isGridOn = true;

        this.getStyleClass().add("board");

    }

    public void toggleGrid() {
        isGridOn = !isGridOn;
        setGrid(isGridOn);
    }

    public void setGrid(boolean b) {
        isGridOn = b;
        for (Node cell : cellGroup.getChildren()) {
            cell.getStyleClass().set(0, isGridOn ? "grid" : "noGrid");
        }
    }

    public void add(GizmoView gizmoView) {
        objectGroup.getChildren().add(gizmoView.getNode());
        cellGroup.toFront();
    }

    public void add(BallView ballView) {
        objectGroup.getChildren().add(ballView);
        cellGroup.toFront();
    }
    public void remove(GizmoView gizmoView) {
        objectGroup.getChildren().remove(gizmoView.getNode());
    }

    public void remove(BallView ballView) {
        objectGroup.getChildren().remove(ballView);
    }

    public void clearBoard() {
        objectGroup.getChildren().clear();
    }
}
