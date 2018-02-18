package controller;

import javafx.fxml.FXML;
import javafx.geometry.HorizontalDirection;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.GizmoballModel;
import view.BoardView;

import java.io.File;

import static javafx.geometry.HorizontalDirection.LEFT;
import static javafx.geometry.HorizontalDirection.RIGHT;

public class GizmoballController {
    public MenuItem save;
    public MenuItem load;
    public MenuItem exit;
    @FXML
    private BorderPane playView;
    @FXML
    private BorderPane buildView;
    @FXML
    private BuildController buildViewController;
    @FXML
    private PlayController playViewController;
    private GizmoballModel model;


    public GizmoballController() {
    }

    @FXML
    public void initialize() {
        //TODO: make proper handler and exit properly
        exit.setOnAction(event -> {
            System.exit(0);
        });


        SwitchModeHandler switchToPlay = new SwitchModeHandler(buildView, playView, buildViewController, playViewController, RIGHT);
        SwitchModeHandler switchToBuild = new SwitchModeHandler(playView, buildView,  buildViewController, playViewController, LEFT);

        buildViewController.setSwitchHandler(switchToPlay);
        playViewController.setSwitchHandler(switchToBuild);
    }

    public BoardView getBoard() {
        return buildViewController.getBoard();
    }

    public void setSaveHandler(SaveHandler saveHandler) {
        save.setOnAction(saveHandler);
    }

    public void setLoadHandler(LoadHandler loadHandler) {
        load.setOnAction(loadHandler);
    }

    public void setModel(GizmoballModel model) {
        this.model = model;
        playViewController.setModel(model);
    }
}
