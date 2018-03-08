package main;

import controller.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.GizmoballModel;

import java.lang.reflect.Constructor;


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

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}