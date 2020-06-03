package windows.buttonView.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import windows.matrixView.matrix.Cell;

public class BottomButtonBar extends HBox {

    public BottomButtonBar(CenterView centerView, Cell cell){
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.setOnAction(event -> {
            //explored
            cell.setExplored(centerView.getExploredBox().getCurrent().equals("Si"));
            //black
            cell.setBlack(centerView.getBlackBox().getCurrent().equals("Si"));
            //checkpoint
            cell.setCheckpoint(centerView.getCheckpointBox().getCurrent().equals("Si"));
            //victim
            cell.setVictim(centerView.getVictimBox().getCurrent().equals("Si"));

            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        //cancel button
        cancel.setOnAction(event -> ((Stage)(((Button)event.getSource()).getScene().getWindow())).close());

        getChildren().addAll(cancel, save);
    }
}
