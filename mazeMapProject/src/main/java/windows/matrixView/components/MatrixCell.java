package windows.matrixView.components;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import windows.buttonView.ButtonView;
import windows.matrixView.matrix.Cell;

public class MatrixCell extends GridPane {
    private final Cell cell;
    private final MatrixPane matrixPane;
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
        Button text = new Button("\u2699");
        text.setOnAction(e -> showDialog());
        add(text, 1, 1);
        int[][] wallButtons = new int[][]{{2, 1, 8594}, {1, 0, 8593}, {0, 1, 8592}, {1, 2, 8595}};
        for (int i = 0; i < wallButtons.length; i++) {
            int[] pair = wallButtons[i];
            Button tmpButton = new Button(String.valueOf((char) pair[2]));
            int finalI = i;
            tmpButton.setOnAction(e -> setCellWalls(finalI));
            add(tmpButton, pair[0], pair[1]);
        }
    }

    private void setCellWalls(int wall){
        this.cell.invertWall(wall);
        try {
            int newX = pairs[wall][1]+cell.getCoord()[1], newY = pairs[wall][0]+cell.getCoord()[0];
            Cell c = matrixPane.getMatrix().getMatrix()[newY][newX];
            c.getWalls()[mirrorWall(wall)] = cell.getWalls()[wall];
            matrixPane.getButtons()[newY][newX].updateStyle();
        }catch (IndexOutOfBoundsException ignored){}
        setStyle(cell.getStyle());
    }

    private void showDialog(){
        ButtonView buttonView = new ButtonView(cell);
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

}