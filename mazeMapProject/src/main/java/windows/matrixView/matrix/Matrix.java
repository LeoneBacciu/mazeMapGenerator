package windows.matrixView.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    private final int size;
    private final Cell[][] matrix;

    public Matrix(int size){
        this.size = size;
        this.matrix = new Cell[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                this.matrix[y][x] = new Cell();
            }
        }
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public List<Cell> toList(){
        List<Cell> cells = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            cells.addAll(Arrays.asList(matrix[y]).subList(0, size));
        }
        return cells;
    }
}
