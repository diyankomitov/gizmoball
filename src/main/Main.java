package main;

import controller.GizmoballController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.GizmoballModel;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Gizmoball");
//        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        FXMLLoader mainFXML = new FXMLLoader(getClass().getResource("/view/fxml/GizmoballView.fxml"));
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