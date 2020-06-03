package windows.matrixView.matrix;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
        try{
            gson.toJson(this.matrix, new FileWriter(path.getAbsolutePath() +
                    "/mazeMap.json"));
        } catch (IOException exception){
            //skip
            exception.printStackTrace();
        }
    }
}
