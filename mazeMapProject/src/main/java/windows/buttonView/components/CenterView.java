package windows.buttonView.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CenterView extends VBox {
    private CenterViewChildren topWallBox;
    private CenterViewChildren rightWallBox;
    private CenterViewChildren bottomWallBox;
    private CenterViewChildren leftWallBox;
    private CenterViewChildren exploredBox;
    private CenterViewChildren blackBox;
    private CenterViewChildren checkpointBox;
    private CenterViewChildren victimBox;

    public CenterView(){
        topWallBox = new CenterViewChildren("Muro sopra", new String[]{
                "Si", "No"
        });

        rightWallBox = new CenterViewChildren("Muro destra", new String[]{
                "Si", "No"
        });
        bottomWallBox = new CenterViewChildren("Muro sotto", new String[]{
                "Si", "No"
        });
        leftWallBox = new CenterViewChildren("Muro sinistra", new String[]{
                "Si", "No"
        });
        exploredBox = new CenterViewChildren("Cella esplorata", new String[]{
                "Si", "No"
        });
        blackBox = new CenterViewChildren("Cella nera", new String[]{
                "Si", "No"
        });
        checkpointBox = new CenterViewChildren("Cella checkpoint", new String[]{
                "Si", "No"
        });
        victimBox = new CenterViewChildren("Cella vittima", new String[]{
                "Si", "No"
        });

        getChildren().addAll(
                topWallBox,
                rightWallBox,
                bottomWallBox,
                leftWallBox,
                exploredBox,
                blackBox,
                checkpointBox,
                victimBox
        );

        //setSpacing(20.0);
    }

    public CenterViewChildren getTopWallBox() {
        return topWallBox;
    }

    public CenterViewChildren getRightWallBox() {
        return rightWallBox;
    }

    public CenterViewChildren getBottomWallBox() {
        return bottomWallBox;
    }

    public CenterViewChildren getLeftWallBox() {
        return leftWallBox;
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
}

class CenterViewChildren extends HBox{
    private String text;
    private String[] choices;
    private String current;

    public CenterViewChildren(String text, String[] choices){
        this.text = text;
        this.choices = choices;
        current = "";
        getChildren().add(new Label(text));

        ChoiceBox<String> box = new ChoiceBox<>();
        //box.setValue("Si");
        box.setItems(FXCollections.observableArrayList(this.choices));
        box.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        current = choices[newValue.intValue()];
                    }
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
