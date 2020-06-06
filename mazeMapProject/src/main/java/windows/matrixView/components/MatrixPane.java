package windows.matrixView.components;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import windows.matrixView.matrix.Matrix;

public class MatrixPane extends GridPane {
    private final Matrix matrix;
    private final MatrixCell[][] cells;
    private final int size;

    public MatrixPane(int size, int level){
        this.size = size;
        this.matrix = new Matrix(size, level);
        this.cells = new MatrixCell[size][size];
        buildCells();
    }

    public MatrixPane(int size, Matrix matrix){
        this.size = size;
        this.matrix = matrix;
        this.cells = new MatrixCell[size][size];
        buildCells();
    }

    private void buildCells(){
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells[y][x] = new MatrixCell(matrix.getMatrix()[y][x], this);
                cells[y][x].updateStyle();
                add(cells[y][x], y, x);
                setHgrow(cells[y][x], Priority.ALWAYS);
                setVgrow(cells[y][x], Priority.ALWAYS);
            }
        }
        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);
    }

    public MatrixCell[][] getCells() {
        return cells;
    }
    public Matrix getMatrix() {
        return matrix;
    }

}
