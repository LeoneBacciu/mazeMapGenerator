package windows.matrixView.components;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import windows.matrixView.matrix.Cell;

public class MatrixCell extends GridPane {
    private Cell cell;
    private final MainPane matrixPane;
    private final int[][] pairs = new int[][]{
        {1, 0},
        {0, -1},
        {-1, 0},
        {0, 1}
    };

    public MatrixCell(Cell cell, MainPane matrixPane){
        this.cell = cell;
        this.matrixPane = matrixPane;
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        Text text = new Text();
        text.setText(cell.getCoord()[0]+" "+cell.getCoord()[1]);
        add(text, 1, 1);
        int[][] wallButtons = new int[][]{{2, 1, 8594}, {1, 0, 8593}, {0, 1, 8592}, {1, 2, 8595}};
        for (int i = 0; i < wallButtons.length; i++) {
            int[] pair = wallButtons[i];
            Button tmpButton = new Button(String.valueOf((char) pair[2]));
            int finalI = i;
            tmpButton.setOnAction(e -> {
                setCellWalls(finalI);
                setStyle(cell.getStyle());
            } );
            add(tmpButton, pair[0], pair[1]);
        }
    }

    private void setCellWalls(int wall){
        this.cell.invertWall(wall);
//        for (int i = 0; i < cell.getWalls().length; i++) {
//            Cell c;
//            try{
//                c = matrixPane.getMatrix()
//                        .get(pairs[i][1] + cell.getCoord()[1]).get(pairs[i][0] + cell.getCoord()[0]);
//                c.getWalls()[mirrorWall(i)] = cell.getWalls()[i];
//                matrixPane.getButtons()[pairs[i][1] + cell.getCoord()[1]][pairs[i][0] + cell.getCoord()[0]]
//                        .setStyle(c.getStyle());
//            } catch (ArrayIndexOutOfBoundsException ignored) {}
//        }
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