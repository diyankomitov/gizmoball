package view;

import javafx.scene.layout.Pane;
import view.gizmoviews.GizmoView;

import static util.Constants.ONE_L_IN_PIXELS;

public class BoardView extends Pane{
    private Pane[][] cells;
    private double cellSize;
    private boolean isGridOn;


    public BoardView() {    //TODO: Refactor
        this.cellSize = ONE_L_IN_PIXELS;
        this.setPrefSize(cellSize*20, cellSize*20);
        this.setMinSize(cellSize*20, cellSize*20);
        this.setMaxSize(cellSize*20, cellSize*20);
//        this.setStyle("-fx-background-color: white, gray; -fx-background-insets: 0, 1 1 0 0 ; -fx-padding: 1;");

        cells = new Pane[20][20];

        for (int y = 0; y<20; y++) {
            for (int x = 0; x<20; x++) {
                Pane cell = new Pane();
                cell.getStyleClass().addAll("boardCell","grid");
                cell.setPrefSize(cellSize, cellSize);
                cells[y][x] = cell;
                cell.setTranslateX(x*ONE_L_IN_PIXELS);
                cell.setTranslateY(y*ONE_L_IN_PIXELS);
                this.getChildren().add(cell);
            }
        }
        isGridOn = true;
    }

    public void toggleGrid() {
        isGridOn = !isGridOn;
        setGrid(isGridOn);
    }

    public void setGrid(boolean b) {
        isGridOn = b;
        for (Pane[] cell : cells) {
            for (Pane pane : cell) {
                pane.getStyleClass().set(1, isGridOn ? "grid" : "noGrid");
            }
        }
    }

    public void add(GizmoView gizmoView) {
        this.getChildren().add(gizmoView.getNode());
    }

    public void remove(GizmoView gizmoView) {
        this.getChildren().remove(gizmoView);
    }
    public void remove(BallView ballView) {
        this.getChildren().remove(ballView);
    }

//    public void addGizmo(Gizmo gizmo){
//        System.out.println(gizmo);
//        int x = (int) gizmo.getX();
//        int y = (int) gizmo.getY();
//
//            switch(gizmo.getType()){
//                case CIRCLE:
//                   this.getChildren().add(new CircleGizmoView((CircleGizmo) gizmo));
//                   break;
//                case TRIANGLE:
//                    this.getChildren().add(new TriangleGizmoView((TriangleGizmo) gizmo));
//                    break;
//                case SQUARE:
//                    this.getChildren().add(new SquareGizmoView((SquareGizmo) gizmo));
//                    break;
//                case LEFT_FLIPPER:
//                    FlipperGizmoView flipperLeft = new FlipperGizmoView((FlipperGizmo)gizmo);
//                    this.getChildren().add(flipperLeft);
//                    break;
//                case RIGHT_FLIPPER:
//                    FlipperGizmoView flipperRight = new FlipperGizmoView((FlipperGizmo)gizmo);
//                    this.getChildren().add(flipperRight);
////                    flipperRight.setX();
//                    break;
//                case ABSORBER:
//                    AbsorberGizmoView absorberView = new AbsorberGizmoView((AbsorberGizmo)gizmo);
//                    this.getChildren().add(absorberView);
//                    System.out.println(absorberView.getTranslateY());
//                    break;
//            }
//    }

    public void clearBoard() {
        this.getChildren().remove(400, this.getChildren().size());
        cells = new Pane[20][20];

        for (int y = 0; y<20; y++) {
            for (int x = 0; x<20; x++) {
                Pane cell = new Pane();
                cell.getStyleClass().addAll("boardCell","grid");
                cell.setPrefSize(cellSize, cellSize);
                cells[y][x] = cell;
                cell.setTranslateX(x*ONE_L_IN_PIXELS);
                cell.setTranslateY(y*ONE_L_IN_PIXELS);
                this.getChildren().add(cell);
            }
        }
    }

    public void add(BallView ballView) {
        this.getChildren().add(ballView);
    }
}
