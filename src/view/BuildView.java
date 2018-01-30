package view;

import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class BuildView {
    private BorderPane root;

    public BuildView() {

        root = new BorderPane();

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem saveItem = new MenuItem("Save");
        MenuItem loadItem = new MenuItem("Load");
        MenuItem exitItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(saveItem, loadItem, exitItem);
        menuBar.getMenus().addAll(fileMenu);
        root.setTop(menuBar);

        HBox leftBars = new HBox();

        ToolBar toolBarOne = new ToolBar();
        toolBarOne.setOrientation(Orientation.VERTICAL);
        VBox buttons = new VBox();
        Button addButton = new Button("Add");
        Button moveButton = new Button("Move");
        Button rotateButton = new Button("Rotate");
        Button deleteButton = new Button("Delete");
        Button switchButton = new Button("Switch Mode");



    }

}
