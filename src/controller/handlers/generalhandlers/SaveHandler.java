package controller.handlers.generalhandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.BoardState;
import view.BoardView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
            saveGame(file); //We need to pass in the current state of the Board
        }
    }


        public void saveGame(File file) {
            try{
                PrintWriter fileWriter = null;
                fileWriter=new PrintWriter(file);
                List<String> stateList = BoardState.getStateList();
                for(String s: stateList) {
                    fileWriter.println(s);
                }
                fileWriter.close();

                System.out.println("Game has been saved");

            } catch (IOException e) {
                System.out.println("Error when trying to save game. :(");
            }

        }


}
