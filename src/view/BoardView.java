package view;

import javafx.scene.layout.Pane;
import model.*;
import model.board.Ball;
import model.board.gizmos.*;
import util.Observer;

import static util.Constants.ONE_L_IN_PIXELS;

public class BoardView extends Pane implements Observer{
    private int curCol;
    private int curRow;
    private Pane[][] cells;
    private BallView ballView;
    private GizmoballModel model;
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
//                cell.setStyle("-fx-border-color: white; -fx-background-insets: 0, 0 0 1 1 ;");
                cell.setPrefSize(cellSize, cellSize);
//                cell.setOnMouseDragReleased(event -> {
//                    curCol = getColumnIndex(cell);
//                    curRow = getRowIndex(cell);
//                });
                cells[y][x] = cell;
                cell.setTranslateX(x*ONE_L_IN_PIXELS);
                cell.setTranslateY(y*ONE_L_IN_PIXELS);
                this.getChildren().add(cell);
//                this.add(cell, x, y);
            }
        }
        isGridOn = true;
    }

    public void toggleGrid() {
        for (Pane[] cell : cells) {
            for (Pane pane : cell) {
                pane.getStyleClass().set(1, isGridOn ? "noGrid" : "grid");
            }
        }
        isGridOn = !isGridOn;
    }

    public void addBall(Ball ball) {
        ballView = new BallView(ball);

        this.getChildren().add(ballView);
    }

    public void removeBall() {
       // this.getChildren().remove();
    }

    public void addGizmo(Gizmo gizmo){
        int x = (int) gizmo.getX();
        int y = (int) gizmo.getY();

            switch(gizmo.getType()){
                case CIRCLE:
                   this.getChildren().add(new CircularBumperView((CircleGizmo) gizmo));
                   break;
                case TRIANGLE:
                    this.getChildren().add(new TriangularBumperView((TriangleGizmo) gizmo));
                    break;
                case SQUARE:
                    this.getChildren().add(new SquareBumperView((SquareGizmo) gizmo));
                    break;
                case LEFT_FLIPPER:
                    FlipperView flipperLeft = new FlipperView((FlipperGizmo)gizmo);
                    this.getChildren().add(flipperLeft);
                    break;
                case RIGHT_FLIPPER:
                    FlipperView flipperRight = new FlipperView((FlipperGizmo)gizmo);
                    this.getChildren().add(flipperRight);
//                    flipperRight.setX();
                    break;
                case ABSORBER:
                    AbsorberView absorberView = new AbsorberView((AbsorberGizmo)gizmo);
                    this.getChildren().add(absorberView);
                    System.out.println(absorberView.getTranslateY());
                    break;
            }
    }

    public void removeGizmo(Gizmo gizmo) {
        //TODO properly implement remove for later when placing and deleting gizmos

        this.getChildren().remove(gizmo.getName());
    }

    public void clearBoard() {
        this.getChildren().clear();
        cells = new Pane[20][20];

        for (int y = 0; y<20; y++) {
            for (int x = 0; x<20; x++) {
                Pane cell = new Pane();
                cell.getStyleClass().addAll("boardCell","grid");
//                cell.setStyle("-fx-border-color: white; -fx-background-insets: 0, 0 0 1 1 ;");
                cell.setPrefSize(cellSize, cellSize);
//                cell.setOnMouseDragReleased(event -> {
//                    curCol = getColumnIndex(cell);
//                    curRow = getRowIndex(cell);
//                });
                cells[y][x] = cell;
                cell.setTranslateX(x*ONE_L_IN_PIXELS);
                cell.setTranslateY(y*ONE_L_IN_PIXELS);
                this.getChildren().add(cell);
//                this.add(cell, x, y);
            }
        }
    }

    public int getCurRow() {
        return curRow;
    }

    public int getCurCol() {
        return curCol;
    }

//    public void setModel(GizmoballModel model) {
//        this.model = model;
//        model.subscribe(this);
//        ballView.setTranslateX(model.getBall().getX() * cellSize);
//        ballView.setTranslateY(model.getBall().getY() * cellSize);
//    }

    @Override
    public void update() {
        model.getBalls().forEach(ball -> {

            ballView.setTranslateX(ball.getX() * cellSize);
            ballView.setTranslateY(ball.getY() * cellSize);
        });

    }
}
