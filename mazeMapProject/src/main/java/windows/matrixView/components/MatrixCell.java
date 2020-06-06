package windows.matrixView.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import windows.buttonView.ButtonView;
import windows.utils.SavedState;
import windows.matrixView.matrix.Cell;

public class MatrixCell extends BorderPane {
    private final Cell cell;
    private final MatrixPane matrixPane;
    private final Button settingsButton;
    private final int[][] pairs = new int[][]{
        {1, 0},
        {0, -1},
        {-1, 0},
        {0, 1}
    };

    public MatrixCell(Cell cell, MatrixPane matrixPane){
        this.cell = cell;
        this.matrixPane = matrixPane;
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        settingsButton = new Button("\u2699");
        settingsButton.setOnAction(e -> showDialog());
        setCenter(settingsButton);
        setRight(buildButton(8594, 0));
        setTop(buildButton(8593, 1));
        setLeft(buildButton(8592, 2));
        setBottom(buildButton(8595, 3));
    }

    private Button buildButton(int arrow, int wall){
        Button tmpButton = new Button(String.valueOf((char) arrow));
        tmpButton.setOnAction(e -> invertCellWall(wall));
        setAlignment(tmpButton, Pos.CENTER);
        return tmpButton;
    }

    private void invertCellWall(int wall){
        this.cell.invertWall(wall);
        try {
            int newX = pairs[wall][1]+cell.getCoord()[1], newY = pairs[wall][0]+cell.getCoord()[0];
            Cell c = matrixPane.getMatrix().getMatrix()[newY][newX];
            c.getWalls()[mirrorWall(wall)] = cell.getWalls()[wall];
            matrixPane.getCells()[newY][newX].updateStyle();
        }catch (IndexOutOfBoundsException ignored){}
        setStyle(cell.getStyle());
        SavedState.setSaved(false);
    }

    private void showDialog(){
        ButtonView buttonView = new ButtonView(this);
        Stage settingsStage = new Stage();
        Scene scene = new Scene(buttonView, 500, 300);
        settingsStage.setTitle("Cell\tx: " + cell.getCoord()[0] +
                " y: " + cell.getCoord()[1]);
        settingsStage.setResizable(false);
        settingsStage.setScene(scene);
        settingsStage.show();
    }

    public void updateStyle(){
        setStyle(cell.getStyle());
        settingsButton.setText((cell.getRamp().equals(""))?"\u2699":cell.getRamp());
        settingsButton.setStyle("-fx-background-color: " + ((cell.hasVictim())?"#c14e4e":"white") + ";");
    }

    private int mirrorWall(int i){
        switch (i){
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
            default:
                return -1;
        }
    }

    public Cell getCell() {
        return cell;
    }
}