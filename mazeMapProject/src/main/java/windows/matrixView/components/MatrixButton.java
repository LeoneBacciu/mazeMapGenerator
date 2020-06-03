package windows.matrixView.components;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import windows.buttonView.ButtonView;
import windows.matrixView.matrix.Cell;
import windows.matrixView.matrix.Matrix;

public class MatrixButton extends Button {
    private Cell cell;
    private ButtonView buttonView;
    private MatrixPane matrixPane;
    private Matrix matrix;

    public MatrixButton(Matrix matrix, MatrixPane matrixPane){
        this.matrix = matrix;
        this.matrixPane = matrixPane;
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);

        setStyle("-fx-background-color:transparent ;\n" +
                "    -fx-background-radius:0;\n");
    }

    public void setCell(Cell cell) {
        this.cell = cell;

        buttonView = new ButtonView(cell, this, matrixPane);
        //stage settings
        Stage stage = new Stage();
        Scene scene = new Scene(buttonView, 600, 400);

        stage.setTitle("Cell - x: " + cell.getCoord()[0] +
                ", y: " + cell.getCoord()[1]);
        stage.setResizable(false);
        stage.setScene(scene);

        this.setOnAction(e -> {
            stage.show();
        } );
    }

    public Cell getCell() {
        return cell;
    }
}
