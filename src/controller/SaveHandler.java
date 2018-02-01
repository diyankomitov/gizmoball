package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveHandler implements EventHandler<ActionEvent> {
    private Stage stage;

    public SaveHandler(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //fileChooser.setInitialDirectory();
        FileChooser.ExtensionFilter eFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(eFilter);
        fileChooser.setSelectedExtensionFilter(eFilter);

        fileChooser.setTitle("Save Board");
        File file = fileChooser.showSaveDialog(stage);
        if(file!=null) {
            saveGame("", file); //We need to pass in the current state of the Board
        }
    }


        public void saveGame(String board, File file) {
            try{
                FileWriter fileWriter = null;
                fileWriter=new FileWriter(file);
                fileWriter.write(board);
                fileWriter.close();

                System.out.println("Game has been saved");

            } catch (IOException e) {
                System.out.println("Error when trying to save game. :(");
            }

        }


}
