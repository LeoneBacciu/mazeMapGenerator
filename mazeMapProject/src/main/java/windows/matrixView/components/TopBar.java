package windows.matrixView.components;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import windows.matrixView.SavedState;
import windows.matrixView.matrix.Cell;
import windows.matrixView.matrix.JsonType;
import windows.matrixView.matrix.Matrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopBar extends MenuBar {
    private boolean loading = false;

    public TopBar(TabPane tabPane, int[] size){
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem exit = new MenuItem("Exit");

        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedPath = fileChooser.showSaveDialog(
                    getScene().getWindow()
            );
            if (selectedPath==null) return;

            if(!selectedPath.getAbsolutePath().endsWith(".json"))
                selectedPath = new File(selectedPath.getAbsolutePath()+".json");

            List<List<Cell>> matrices = new ArrayList<>();
            ObservableList<Tab> tabs = tabPane.getTabs();
            for (int i = 0, tabsSize = tabs.size(); i < tabsSize-1; i++) {
                Tab tab = tabs.get(i);
                MatrixPane matrixPane = (MatrixPane) tab.getContent();
                matrices.add(matrixPane.getMatrix().toList(i==0));
            }

            Map<String, Object> json = new HashMap<>();
            json.put("header", new int[]{size[0], tabPane.getTabs().size()-1});
            json.put("body", matrices);
            this.save(selectedPath, json);
        });

        load.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedPath = fileChooser.showOpenDialog(
                    getScene().getWindow()
            );
            if (selectedPath==null) return;
            JsonType map = load(selectedPath);
            loading = true;
            tabPane.getTabs().remove(0, tabPane.getTabs().size()-1);

            for (int i = 0; i < map.header[1]; i++) {
                Tab tmpTab = new Tab();
                tmpTab.setText("Level "+i);
                tmpTab.setContent(new MatrixPane(size[0], new Matrix(size[0], i, map)));
                tabPane.getTabs().add(i, tmpTab);
                if (i==0) tmpTab.setClosable(false);
            }
            tabPane.getSelectionModel().select(0);
            loading = false;
        });

        exit.setOnAction((ActionEvent event) -> System.exit(0));
        file.getItems().addAll(save, load, exit);
        getMenus().add(file);
    }

    private JsonType load(File path){
        Gson gson = new Gson();
        JsonType out = null;
        try{
            String data = new String(Files.readAllBytes(path.toPath()), StandardCharsets.UTF_8);
            out = gson.fromJson(data, JsonType.class);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return out;
    }

    private void save(File path, Map<String, Object> objectMap){
        Gson gson = new Gson();
        try(FileWriter fr = new FileWriter(path.getAbsolutePath())){
            gson.toJson(objectMap, fr);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        SavedState.setSaved(true);
    }

    public boolean isLoading() {
        return loading;
    }
}
