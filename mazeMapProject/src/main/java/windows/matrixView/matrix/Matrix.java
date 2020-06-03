package windows.matrixView.matrix;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix extends ArrayList<ArrayList<Cell>> {
    private int size;

    public Matrix(int size){
        this.size = size;
        for (int i = 0; i < size; i++)
            this.add(new ArrayList<>());

        for(ArrayList<Cell> cell : this){
            for(int i = 0; i < size; i++)
                cell.add(new Cell());
        }
    }

    public void save(File path){
        Gson gson = new Gson();
        try{
            gson.toJson(this, new FileWriter(path.getAbsolutePath() +
                    "/mazeMap.json"));
        } catch (IOException exception){
            //skip
            exception.printStackTrace();
        }
    }
}
