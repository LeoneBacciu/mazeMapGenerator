package windows.matrixView.components;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import windows.utils.FileManager;


public class TopBar extends MenuBar {

    public TopBar(TabPane tabPane, int[] size){
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem exit = new MenuItem("Exit");
        FileManager fileManager = new FileManager(tabPane, size[0]);

        save.setOnAction(event -> fileManager.save(getScene()));

        load.setOnAction(event -> fileManager.load(getScene()));

        exit.setOnAction((ActionEvent event) -> exit(tabPane, size[0]));
        file.getItems().addAll(save, load, exit);
        getMenus().add(file);
    }

    private void exit(TabPane tabPane, int size){
        ExitAlert exitAlert = new ExitAlert(tabPane, size);
        exitAlert.launch();
    }
}
