package controller.handlers.generalhandlers;

import controller.BuildController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import util.BoardState;

import java.util.Optional;

public class ExitHandler implements EventHandler<ActionEvent> {
    private final Stage stage;
    private BuildController controller;
    private final SaveHandler saveHandler;
    public ExitHandler(BuildController controller, Stage stage) {
        saveHandler = new SaveHandler(stage, controller);
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        if (BoardState.getSavedBoard()) {
            System.exit(0);
        } else {
            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            ButtonType cancel = new ButtonType("Cancel");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to save the layout before exiting?", yes, no, cancel);
            alert.setTitle("Board Layout Not Saved");
            alert.setHeaderText("Board layout has not been saved.");

            alert.initStyle(StageStyle.TRANSPARENT);
            stage.getScene().getRoot().setEffect(new GaussianBlur(50));
            alert.getDialogPane().getScene().getStylesheets().add("view/css/styles.css");


            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event1 -> window.hide());
            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(buttonType -> {
                if (buttonType == yes) {
                    saveHandler.handle(event);
                    System.exit(0);
                } else if (buttonType == no) {
                    System.exit(0);
                } else {
                    event.consume();
                }
            });

            stage.getScene().getRoot().setEffect(null);

        }
    }
}
