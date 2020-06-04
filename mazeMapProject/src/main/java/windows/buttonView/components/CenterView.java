package windows.buttonView.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import windows.matrixView.components.MatrixCell;
import windows.matrixView.matrix.Cell;
import windows.matrixView.matrix.Ramps;

public class CenterView extends VBox {
    private final CenterViewChildren exploredBox;
    private final CenterViewChildren blackBox;
    private final CenterViewChildren checkpointBox;
    private final CenterViewChildren victimBox;
    private final CenterViewChildren rampBox;
    private final Cell cell;
    private final MatrixCell matrixCell;

    public CenterView(MatrixCell matrixCell){
        this.matrixCell = matrixCell;
        this.cell = matrixCell.getCell();
        String[] yesNo = new String[]{"Si", "No"};

        exploredBox = new CenterViewChildren("Cella esplorata", yesNo[cell.isExplored()?0:1], yesNo);
        blackBox = new CenterViewChildren("Cella nera", yesNo[cell.isBlack()?0:1], yesNo);
        checkpointBox = new CenterViewChildren("Cella checkpoint", yesNo[cell.isCheckpoint()?0:1], yesNo);
        victimBox = new CenterViewChildren("Cella vittima", yesNo[cell.hasVictim()?0:1], yesNo);

        rampBox = new CenterViewChildren("Rampa", cell.getRamp(), Ramps.getAvailable());

        getChildren().addAll(
                exploredBox,
                blackBox,
                checkpointBox,
                victimBox,
                rampBox
        );
    }

    void save(){
        cell.setExplored(getExploredBox().getCurrent().equals("Si"));
        cell.setBlack(getBlackBox().getCurrent().equals("Si"));
        cell.setCheckpoint(getCheckpointBox().getCurrent().equals("Si"));
        cell.setVictim(getVictimBox().getCurrent().equals("Si"));

        String prev = cell.getRamp();
        cell.setRamp(getRampBox().getCurrent());
        if(!prev.equals("")) Ramps.reverse(prev);
        if(!prev.equals(cell.getRamp()))Ramps.selected(cell.getRamp());
        matrixCell.updateStyle();
    }

    public CenterViewChildren getExploredBox() {
        return exploredBox;
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

    public CenterViewChildren getRampBox() {
        return rampBox;
    }
}

class CenterViewChildren extends HBox{
    private String current;

    public CenterViewChildren(String text, String def, String[] choices){
        current = def;

        if(!def.equals("") && choices.length == 1 && !choices[0].equals(def))
            choices = new String[]{"A", "B"};

        if(!def.equals("") && choices.length == 0)
            choices = new String[]{def};

        getChildren().add(new Label(text));
        ChoiceBox<String> box = new ChoiceBox<>();
        ObservableList<String> observableArray = FXCollections.observableArrayList(choices);
        observableArray.add(0, "");
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
