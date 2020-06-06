package windows.buttonView.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import windows.utils.SavedState;
import windows.matrixView.components.MatrixCell;
import windows.matrixView.matrix.Cell;

public class CenterView extends VBox {
    private final CenterViewChildren blackBox;
    private final CenterViewChildren checkpointBox;
    private final CenterViewChildren victimBox;
    private final Cell cell;
    private final MatrixCell matrixCell;

    public CenterView(MatrixCell matrixCell) {
        this.matrixCell = matrixCell;
        this.cell = matrixCell.getCell();
        String[] yesNo = new String[]{"Si", "No"};

        blackBox = new CenterViewChildren("Cella nera", yesNo[cell.isBlack() ? 0 : 1], yesNo);
        checkpointBox = new CenterViewChildren("Cella checkpoint", yesNo[cell.isCheckpoint() ? 0 : 1], yesNo);
        victimBox = new CenterViewChildren("Cella vittima", yesNo[cell.hasVictim() ? 0 : 1], yesNo);

        getChildren().addAll(
                blackBox,
                checkpointBox,
                victimBox
        );
    }

    void save() {
        boolean changed;
        changed = cell.setBlack(getBlackBox().getCurrent().equals("Si"));
        changed ^= cell.setCheckpoint(getCheckpointBox().getCurrent().equals("Si"));
        changed ^= cell.setVictim(getVictimBox().getCurrent().equals("Si"));
        if (changed) SavedState.setSaved(false);

        matrixCell.updateStyle();
    }


    public CenterViewChildren getBlackBox() {
        return blackBox;
    }

    public CenterViewChildren getCheckpointBox() {
        return checkpointBox;
    }

    public CenterViewChildren getVictimBox() {
        return victimBox;
    }

}

class CenterViewChildren extends HBox {
    private String current;

    public CenterViewChildren(String text, String def, String[] choices) {
        current = def;

        getChildren().add(new Label(text));
        ChoiceBox<String> box = new ChoiceBox<>();
        ObservableList<String> observableArray = FXCollections.observableArrayList(choices);
        box.setItems(observableArray);
        box.setValue(current);
        box.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    current = observableArray.get(newValue.intValue());
                    CenterView parent = (CenterView) getParent();
                    parent.save();
                }
        );
        getChildren().add(box);

        setPadding(new Insets(10, 20, 0, 20));
        setSpacing(30.0);
    }

    public String getCurrent() {
        return current;
    }
}
