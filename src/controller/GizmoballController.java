package controller;

import controller.handlers.ExitHandler;
import controller.handlers.LoadHandler;
import controller.handlers.SaveHandler;
import controller.handlers.SwitchModeHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.GizmoballModel;

import static javafx.geometry.HorizontalDirection.LEFT;
import static javafx.geometry.HorizontalDirection.RIGHT;

public class GizmoballController {
    public StackPane centerPane;
    public BorderPane mainRoot;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem load;
    @FXML
    private MenuItem exit;
    @FXML
    private BorderPane playView;
    @FXML
    private BorderPane buildView;

    @FXML
    private BuildController buildViewController;
    @FXML
    private PlayController playViewController;

    private GizmoballModel model;
    private Stage stage;


    private SaveHandler saveHandler;
    private LoadHandler loadHandler;
    private ExitHandler exitHandler;

    public void setup(Stage stage, GizmoballModel model) {
        this.stage = stage;
        this.model = model;

        BoardController boardController = new BoardController();
        buildViewController.setup(model, boardController);
        playViewController.setup(model, boardController);

        saveHandler = new SaveHandler(stage);
        loadHandler = new LoadHandler(stage, boardController.getBoardView(), model); //TODO: maybe change getBoard to getBoardController
        exitHandler = new ExitHandler();

        SwitchModeHandler switchToPlay = new SwitchModeHandler(buildViewController, playViewController, RIGHT);
        SwitchModeHandler switchToBuild = new SwitchModeHandler(buildViewController, playViewController, LEFT);

        buildViewController.setSwitchHandler(switchToPlay);
        playViewController.setSwitchHandler(switchToBuild);

        setupHandlers();
    }

    private void setupHandlers() {
        save.setOnAction(saveHandler);
        load.setOnAction(loadHandler);
        exit.setOnAction(exitHandler);
    }
}
