package windows.utils;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import windows.matrixView.components.MatrixPane;
import windows.matrixView.matrix.Cell;
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

public class FileManager {
    private static boolean loading = false;
    private final TabPane tabPane;
    private final int size;
    private final Gson gson = new Gson();
    private final FileChooser fileChooser = new FileChooser();

    public FileManager(TabPane tabPane, int size){
        this.tabPane = tabPane;
        this.size = size;
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    public boolean load(Scene scene){
        File selectedPath = fileChooser.showOpenDialog(scene.getWindow());
        if (selectedPath==null) return false;
        JsonType map = loadFromFile(selectedPath);
        loading = true;
        tabPane.getTabs().remove(0, tabPane.getTabs().size()-1);

        for (int i = 0; i < map.header[1]; i++) {
            Tab tmpTab = new Tab();
            tmpTab.setText("Level "+i);
            tmpTab.setContent(new MatrixPane(size, new Matrix(size, i, map)));
            tabPane.getTabs().add(i, tmpTab);
            if (i==0) tmpTab.setClosable(false);
        }
        tabPane.getSelectionModel().select(0);
        loading = false;
        return true;
    }

    public boolean save(Scene scene){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedPath = fileChooser.showSaveDialog(scene.getWindow());
        if (selectedPath==null) return false;

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
        json.put("header", new int[]{size, tabPane.getTabs().size()-1});
        json.put("body", matrices);
        this.saveToFile(selectedPath, json);
        return true;
    }

    private void saveToFile(File path, Map<String, Object> objectMap){
        try(FileWriter fr = new FileWriter(path.getAbsolutePath())){
            gson.toJson(objectMap, fr);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        SavedState.setSaved(true);
    }

    private JsonType loadFromFile(File path){
        JsonType out = null;
        try{
            String data = new String(Files.readAllBytes(path.toPath()), StandardCharsets.UTF_8);
            out = gson.fromJson(data, JsonType.class);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return out;
    }

    public static boolean isLoading() {
        return loading;
    }
}
