package windows.buttonView.components;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CenterView extends VBox {
    private final CenterViewChildren exploredBox;
    private final CenterViewChildren blackBox;
    private final CenterViewChildren checkpointBox;
    private final CenterViewChildren victimBox;

    public CenterView(){
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
                exploredBox,
                blackBox,
                checkpointBox,
                victimBox
        );

        //setSpacing(20.0);
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
    private String current;

    public CenterViewChildren(String text, String[] choices){
        current = "";
        getChildren().add(new Label(text));

        ChoiceBox<String> box = new ChoiceBox<>();
        //box.setValue("Si");
        box.setItems(FXCollections.observableArrayList(choices));
        box.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> current = choices[newValue.intValue()]
        );
        getChildren().add(box);

        setPadding(new Insets(10, 20, 0, 20));
        setSpacing(30.0);
    }

    public String getCurrent() {
        return current;
    }
}
