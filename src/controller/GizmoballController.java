package controller;

import controller.handlers.generalhandlers.ExitHandler;
import controller.handlers.generalhandlers.LoadHandler;
import controller.handlers.generalhandlers.SaveHandler;
import controller.handlers.generalhandlers.SwitchModeHandler;
import javafx.beans.property.SimpleBooleanProperty;
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
    private BorderPane runView;
    @FXML
    private BorderPane buildView;

    @FXML
    private BuildController buildViewController;
    @FXML
    private RunController runViewController;

    public static final SimpleBooleanProperty disable = new SimpleBooleanProperty(false);

    private SaveHandler saveHandler;
    private LoadHandler loadHandler;
    private ExitHandler exitHandler;

    public void setup(Stage stage, GizmoballModel model) {

        BoardController boardController = new BoardController();

        saveHandler = new SaveHandler(stage, buildViewController);
        loadHandler = new LoadHandler(stage, boardController, buildViewController, model, buildViewController.getInfoLabel());
        exitHandler = new ExitHandler(buildViewController, stage);

        SwitchModeHandler switchToRun = new SwitchModeHandler(buildViewController, runViewController, RIGHT, loadHandler, saveHandler);
        SwitchModeHandler switchToBuild = new SwitchModeHandler(buildViewController, runViewController, LEFT, loadHandler, saveHandler);


        buildViewController.setup(model, boardController, switchToRun, stage);
        runViewController.setup(model, boardController, switchToBuild, stage, loadHandler);

        save.disableProperty().bind(disable);
        load.disableProperty().bind(disable);

        setupHandlers();
    }

    private void setupHandlers() {
        save.setOnAction(saveHandler);
        load.setOnAction(loadHandler);
        exit.setOnAction(exitHandler);
    }
}
