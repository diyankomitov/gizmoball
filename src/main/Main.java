package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Gizmoball");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/BuildModeView.fxml"))));
        primaryStage.show();
    }

}
