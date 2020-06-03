package windows.buttonView.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import windows.matrixView.components.MatrixButton;
import windows.matrixView.components.MainPane;
import windows.matrixView.components.MatrixPane;
import windows.matrixView.matrix.Cell;

public class BottomButtonBar extends HBox {
    private Button save;
    private Button cancel;
    private int[][] pairs;

    public BottomButtonBar(CenterView centerView, Cell cell, MatrixButton button,
                           MatrixPane matrixPane){
        pairs = new int[][]{
                {1, 0},
                {0, -1},
                {-1, 0},
                {0, 1}
        };
        save = new Button("Save");
        cancel = new Button("Cancel");

        save.setOnAction(event -> {
            //top wall
            cell.setTopWall(centerView.getTopWallBox().getCurrent().equals("Si"));
            //right wall
            cell.setRightWall(centerView.getRightWallBox().getCurrent().equals("Si"));
            //bottom wall
            cell.setBottomWall(centerView.getBottomWallBox().getCurrent().equals("Si"));
            //left wall
            cell.setLeftWall(centerView.getLeftWallBox().getCurrent().equals("Si"));
            //explored
            cell.setExplored(centerView.getExploredBox().getCurrent().equals("Si"));
            //black
            cell.setBlack(centerView.getBlackBox().getCurrent().equals("Si"));
            //checkpoint
            cell.setCheckpoint(centerView.getCheckpointBox().getCurrent().equals("Si"));
            //victim
            cell.setVictim(centerView.getVictimBox().getCurrent().equals("Si"));

            button.setStyle(cell.getStyle());

            for (int i = 0; i < cell.getWalls().length; i++) {
                if(cell.getWalls()[i] == 1){
                    Cell c;
                    try{
                        c = matrixPane.getMatrix()
                                .get(pairs[i][1] + cell.getCoord()[1]).get(pairs[i][0] + cell.getCoord()[0]);

                        c.getWalls()[mirrorWall(i)] = 1;
                        matrixPane.getButtons().get(pairs[i][1] + cell.getCoord()[1]).get(pairs[i][0] + cell.getCoord()[0])
                                .setStyle(c.getStyle());

                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                }
            }
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        //cancel button
        cancel.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        });

        getChildren().addAll(cancel, save);
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
