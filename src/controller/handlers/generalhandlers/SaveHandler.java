package controller.handlers.generalhandlers;

import controller.BuildController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.BoardState;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SaveHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private BuildController controller;
    public SaveHandler(Stage stage, BuildController controller) {
        this.controller = controller;
        this.stage = stage;
    }

    public SaveHandler() {

    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //fileChooser.setInitialDirectory();
        FileChooser.ExtensionFilter eFilter = new FileChooser.ExtensionFilter("Gizmoball files (*.gzb)", "*.gzb");
        FileChooser.ExtensionFilter eFilter2 = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

        fileChooser.getExtensionFilters().add(eFilter);
        fileChooser.getExtensionFilters().add(eFilter2);

        fileChooser.setSelectedExtensionFilter(eFilter);
        fileChooser.setInitialFileName("untitled");
        fileChooser.setTitle("Save Board");
        File file = fileChooser.showSaveDialog(stage);
        if(file!=null) {
            saveGame(file);
        }
    }


    public void saveGame(File file) {
        try{
            PrintWriter fileWriter;
            fileWriter=new PrintWriter(file);
            List<String> stateList = BoardState.getStateList();
            for(String s: stateList) {
                fileWriter.println(s);
            }
            fileWriter.close();
            BoardState.setSavedBoard(true);
            controller.setInformation("Game has been saved");


        } catch (IOException e) {
            controller.setInformation("Error when trying to save game.");
            System.err.println("Error when trying to save game. :(" + e);
        }

    }


}
