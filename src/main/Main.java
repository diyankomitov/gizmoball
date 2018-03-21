package main;

import controller.GizmoballController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GizmoballModel;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Gizmoball");

        FXMLLoader mainFXML = new FXMLLoader(getClass().getResource("../view/fxml/GizmoballView.fxml"));
        Parent root = mainFXML.load();

        GizmoballController gizmoballController = mainFXML.getController();
        GizmoballModel model = new GizmoballModel();

        gizmoballController.setup(primaryStage, model);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("view/css/styles.css");

//        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println(scene.getWidth() + " " + scene.getHeight());
    }
}