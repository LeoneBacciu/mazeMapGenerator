package windows.matrixView.components;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import windows.matrixView.matrix.Cell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopBar extends MenuBar {

    public TopBar(TabPane tabPane, int[] size){
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");

        save.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedPath = directoryChooser.showDialog(
                    getScene().getWindow()
            );

            List<List<Cell>> matrices = new ArrayList<>();
            for(Tab tab: tabPane.getTabs().subList(0, tabPane.getTabs().size()-1)){
                MatrixPane matrixPane = (MatrixPane) tab.getContent();
                matrices.add(matrixPane.getMatrix().toList());
            }

            Map<String, Object> json = new HashMap<>();
            json.put("header", new int[]{size[0], tabPane.getTabs().size()});
            json.put("body", matrices);
            this.save(selectedPath, json);
        });

        exit.setOnAction((ActionEvent event) -> System.exit(0));
        file.getItems().addAll(save, exit);
        getMenus().add(file);
    }

    private void save(File path, Map<String, Object> objectMap){
        Gson gson = new Gson();

        try(FileWriter fr = new FileWriter(path.getAbsolutePath() +
                "/mazeMap.json")){
            gson.toJson(objectMap, fr);
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
