package windows.matrixView.matrix;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    private int size;
    private Cell[][] matrix;

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

    public void save(File path){
        Gson gson = new Gson();
        List<Cell> cells = new ArrayList<>();

        for (int y = 0; y < size; y++) {
            cells.addAll(Arrays.asList(matrix[y]).subList(0, size));
        }

        try(FileWriter fr = new FileWriter(path.getAbsolutePath() +
                "/mazeMap.json")){
            gson.toJson(cells, fr);
        } catch (IOException exception){
            //skip
            exception.printStackTrace();
        }
    }
}
