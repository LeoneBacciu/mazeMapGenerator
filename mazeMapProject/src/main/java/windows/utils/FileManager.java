package windows.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import windows.errors.*;
import windows.matrixView.components.MatrixCell;
import windows.matrixView.components.MatrixPane;
import windows.matrixView.matrix.Cell;
import windows.matrixView.matrix.Matrix;
import windows.matrixView.matrix.Ramps;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class FileManager {
    private static boolean loading = false;
    private static int[] defaultStart = new int[]{0, 0, 0};
    private final TabPane tabPane;
    private final int size;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final FileChooser fileChooser = new FileChooser();

    public FileManager(TabPane tabPane, int size) {
        this.tabPane = tabPane;
        this.size = size;
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    public boolean load(Scene scene) {
        File selectedPath = fileChooser.showOpenDialog(scene.getWindow());
        if (selectedPath == null) return false;
        loading = true;

        try {
            JsonType map = loadFromFile(selectedPath);
            defaultStart = map.header.start;
            MatrixPane[] matrixPanes = new MatrixPane[map.header.dims[1]];
            for (int i = 0; i < map.header.dims[1]; i++) {
                matrixPanes[i] = new MatrixPane(size, new Matrix(size, i, map));
            }
            tabPane.getTabs().remove(0, tabPane.getTabs().size() - 1);
            for (int i = 0; i < map.header.dims[1]; i++) {
                Tab tmpTab = new Tab();
                tmpTab.setText("Level " + i);
                tmpTab.setContent(matrixPanes[i]);
                tabPane.getTabs().add(i, tmpTab);
                if (i == 0) tmpTab.setClosable(false);
            }
            tabPane.getSelectionModel().select(0);
        } catch (FileCorruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while loading");
            alert.setContentText(e.getMessage());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.getCause().printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
        }
        loading = false;
        return true;
    }

    public boolean save(Scene scene) {

        CenterChooserDialog centerChooserDialog = new CenterChooserDialog(tabPane, size, defaultStart);
        if (!centerChooserDialog.launch()) return false;

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedPath = fileChooser.showSaveDialog(scene.getWindow());
        if (selectedPath == null) return false;

        if (!selectedPath.getAbsolutePath().endsWith(".json"))
            selectedPath = new File(selectedPath.getAbsolutePath() + ".json");

        int[] startCell = centerChooserDialog.getCenterCell();
        List<List<Cell>> matrices = new ArrayList<>();
        ObservableList<Tab> tabs = tabPane.getTabs();
        try {
            String evenRamps = Ramps.evenRamps();
            if (!evenRamps.equals("")) throw new MismatchingRampsException(evenRamps);
            for (int i = 0, tabsSize = tabs.size(); i < tabsSize - 1; i++) {
                Tab tab = tabs.get(i);
                MatrixPane matrixPane = (MatrixPane) tab.getContent();
                List<Cell> cells = matrixPane.getMatrix().toList();
                if (startCell[0] == i) {
                    boolean startIn = false;
                    for (Cell c : cells) {
                        if (c.getCoord()[0] == startCell[1] && c.getCoord()[1] == startCell[2]) {
                            startIn = true;
                            break;
                        }
                    }
                    if (!startIn) throw new StartCellException();
                }
                matrices.add(cells);
            }

            Map<String, Object> json = new HashMap<>();
            Map<String, Object> header = new HashMap<>();
            header.put("dims", new int[]{size, tabPane.getTabs().size() - 1});
            header.put("start", startCell);
            json.put("header", header);
            json.put("body", matrices);
            this.saveToFile(selectedPath, json);
            SavedState.setSaved(true);
        } catch (OpenMazeException | MissingRampsException | MismatchingRampsException | StartCellException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occoured while saving");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void saveToFile(File path, Map<String, Object> objectMap) {
        try (FileWriter fr = new FileWriter(path.getAbsolutePath())) {
            gson.toJson(objectMap, fr);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        SavedState.setSaved(true);
    }

    private JsonType loadFromFile(File path) throws FileCorruptedException {
        JsonType out;
        try {
            String data = new String(Files.readAllBytes(path.toPath()), StandardCharsets.UTF_8);
            out = gson.fromJson(data, JsonType.class);
        } catch (IOException | JsonSyntaxException exception) {
            throw new FileCorruptedException(exception);
        }
        return out;
    }

    public static boolean isLoading() {
        return loading;
    }
}

class CenterChooserDialog extends Alert {
    private final TabPane tabPane;
    private final int[] coord;
    private final int maxLevel, maxSize;
    private final Spinner<Integer>[] children = new Spinner[3];

    public CenterChooserDialog(TabPane tabPane, int maxSize, int[] defaultStart) {
        super(AlertType.CONFIRMATION);
        this.tabPane = tabPane;
        this.coord = defaultStart;
        this.maxSize = maxSize;
        maxLevel = tabPane.getTabs().size() - 2;

        getMatrixCells()[coord[1]][coord[2]].setStart(true);

        setTitle("Starting point");
        setHeaderText(null);
        setContentText("Please, choose the starting point");
        GridPane gridPane = new GridPane();
        getDialogPane().setContent(gridPane);
        String[] labels = new String[]{"Level: ", "X: ", "Y: "};
        for (int i = 0; i < 3; i++) {
            gridPane.add(new Label(labels[i]), 0, i);
            children[i] = new Spinner<>(0, (i == 0) ? maxLevel : maxSize, defaultStart[i], 1);
            gridPane.add(children[i], 1, i);
            int finalI = i;
            children[i].valueProperty().addListener(((observable, oldValue, newValue) -> {
                handleChange(finalI, oldValue, newValue);
            }));
        }

        this.getDialogPane().getScene().setOnKeyPressed(event -> {
            switch (event.getText().toUpperCase()) {
                case "S":
                    addValue(2, 1);
                    break;
                case "W":
                    addValue(2, -1);
                    break;
                case "D":
                    addValue(1, 1);
                    break;
                case "A":
                    addValue(1, -1);
                    break;
                case "Q":
                    addValue(0, -1);
                    break;
                case "E":
                    addValue(0, 1);
                    break;
            }
        });
    }

    public boolean launch() {
        Optional<ButtonType> result = showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public int[] getCenterCell() {
        return coord;
    }

    private void handleChange(int index, int oldValue, int newValue) {
        getMatrixCells()[coord[1]][coord[2]].setStart(false);
        coord[index] = newValue;
        if (getMatrixCells()[coord[1]][coord[2]].setStart(true)) {
            tabPane.getSelectionModel().select(coord[0]);
        } else {
            children[index].getValueFactory().setValue(oldValue);
        }
    }

    private void addValue(int field, int value) {
        value += children[field].getValue();
        value = Math.max(0, Math.min((field == 0) ? maxLevel : maxSize - 1, value));
        children[field].getValueFactory().setValue(value);
    }

    private MatrixCell[][] getMatrixCells() {
        return ((MatrixPane) tabPane.getTabs().get(coord[0]).getContent()).getCells();
    }
}