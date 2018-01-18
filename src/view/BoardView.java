package view;

import javafx.beans.NamedArg;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.util.Arrays;

public class BoardView extends GridPane {
    private int curCol;
    private int curRow;
    private Pane[][] cells;

    public BoardView(@NamedArg("cellSize") double cellSize) {
        this.setPrefSize(cellSize*20, cellSize*20);
        this.setMinSize(cellSize*20, cellSize*20);
        this.setMaxSize(cellSize*20, cellSize*20);
        this.setStyle("-fx-background-color: white, gray; -fx-background-insets: 0, 1 1 0 0 ; -fx-padding: 1;");

        cells = new Pane[20][20];

        for (int y = 0; y<20; y++) {
            for (int x = 0; x<20; x++) {
                Pane cell = new Pane();
                cell.setStyle("-fx-border-color: white; -fx-background-insets: 0, 0 0 1 1 ;");
                cell.setPrefSize(cellSize, cellSize);
                cell.setOnMouseDragReleased(event -> {
                    curCol = getColumnIndex(cell);
                    curRow = getRowIndex(cell);
                });
                cells[y][x] = cell;
                this.add(cell, x, y);
            }
        }
    }

    public int getCurRow() {
        return curRow;
    }

    public int getCurCol() {
        return curCol;
    }
}
