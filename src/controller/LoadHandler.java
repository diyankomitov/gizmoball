package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Gizmo;
import model.GizmoType;
import model.GizmoballModel;

import java.io.*;

public class LoadHandler implements EventHandler<ActionEvent> {
    private Stage stage;
    private GizmoballModel model; //Alistair thinks it might needs passed
    public LoadHandler(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter eFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(eFilter);
        fileChooser.setSelectedExtensionFilter(eFilter);

        fileChooser.setTitle("Load Board");
        File file = fileChooser.showOpenDialog(stage);

        if(file!=null) {
            loadGame(file);
        }
    }

    public void loadGame(File file) {
        try {
            model = new GizmoballModel();
            FileReader fileReader  = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int strCount = 0;
            String stringLine;
            String boardDetailStr[];
            while((stringLine = bufferedReader.readLine())!=null) {
                boardDetailStr = stringLine.split(" ");

            }

        } catch (FileNotFoundException e) {
            System.out.println("Error when trying to load the game. :(");
        } catch(IOException e ) {
            System.out.println("Error when trying to load the game. :(");
        }
    }

    public void checkAction(String[] string){
        double x;
        double y;

        switch(string[0]) {
            case "Triangle":

                 x = Double.parseDouble(string[2]);
                 y = Double.parseDouble(string[3]);

                 model.addGizmo(x, y, GizmoType.TRIANGLE, string[1] );
                break;
            case "Square":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, GizmoType.SQUARE, string[1]);
                break;
            case "Circle":
                x = Double.parseDouble(string[2]);
                y = Double.parseDouble(string[3]);
                model.addGizmo(x, y, GizmoType.CIRCLE, string[1]);
                break;
            case "LeftFlipper":

        }

    }
}
