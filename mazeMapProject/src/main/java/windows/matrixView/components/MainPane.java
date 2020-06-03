package windows.matrixView.components;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import windows.matrixView.matrix.Matrix;

public class MainPane extends GridPane {
    private final Matrix matrix;
    private final MatrixCell[][] buttons;

    public MainPane(int size){
        matrix = new Matrix(size);
        buttons = new MatrixCell[size][size];


        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                matrix.getMatrix()[y][x].setCoord(new int[]{y, x});
            }
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                buttons[y][x] = new MatrixCell(matrix.getMatrix()[y][x], this);
                buttons[y][x].updateStyle();
                add(buttons[y][x], y, x);
                setHgrow(buttons[y][x], Priority.ALWAYS);
                setVgrow(buttons[y][x], Priority.ALWAYS);
            }
        }


        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
    }

    public MatrixCell[][] getButtons() {
        return buttons;
    }
    public Matrix getMatrix() {
        return matrix;
    }
}
