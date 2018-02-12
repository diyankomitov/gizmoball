package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadHandler implements EventHandler<ActionEvent> {
    private Stage stage;

    public LoadHandler(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Board");
        fileChooser.showOpenDialog(stage);
    }
}
