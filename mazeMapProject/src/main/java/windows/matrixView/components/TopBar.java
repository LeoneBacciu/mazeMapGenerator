package windows.matrixView.components;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import windows.matrixView.matrix.Matrix;

import java.io.File;

public class TopBar extends MenuBar {

    public TopBar(Matrix matrix){
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");

        save.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedPath = directoryChooser.showDialog(
                    (Stage) getScene().getWindow()
            );
            matrix.save(selectedPath);
        });

        exit.setOnAction(event -> {
            System.exit(0);
        });
        file.getItems().addAll(save, exit);
        getMenus().add(file);
    }
}
