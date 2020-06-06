package windows.matrixView.matrix;

import windows.utils.JsonCell;
import windows.utils.JsonType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    private final int size;
    private final Cell[][] matrix;
    private final int[][] pairs = new int[][]{
            {1, 0},
            {0, -1},
            {-1, 0},
            {0, 1}
    };

    public Matrix(int size){
        this.size = size;
        this.matrix = new Cell[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                this.matrix[y][x] = new Cell(new int[]{y, x});
            }
        }
    }

    public Matrix(int size, int level, JsonType jsonType){
        this.size = size;
        this.matrix = new Cell[size][size];

        for (JsonCell jsonCell: jsonType.body.get(level)) {
            int x = jsonCell.coord[1], y = jsonCell.coord[0];
            matrix[y][x] = new Cell(
                    jsonCell.coord,
                    jsonCell.walls,
                    jsonCell.explored,
                    jsonCell.black,
                    jsonCell.checkpoint,
                    jsonCell.victim,
                    jsonCell.ramp
            );
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (this.matrix[y][x]==null)
                    this.matrix[y][x] = new Cell(new int[]{y, x});
            }
        }
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    private void fillMask(int x, int y, boolean[][] mask){
        if(x<0 || x>mask.length || y<0 || y>mask.length || mask[y][x]) return;
        mask[y][x] = true;
        for (int i = 0; i < 4; i++) {
            if(matrix[y][x].getWalls()[i]==0){
                fillMask(x+pairs[i][1], y+pairs[i][0], mask);
            }
        }
    }

    private void fillMaskInside(boolean[][] mask){
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (matrix[y][x].getWalls()[0]==1){
                    try {
                        fillMask(x, y, mask);
                    }catch (ArrayIndexOutOfBoundsException ignored1){
                        for (boolean[] m: mask) Arrays.fill(m, false);
                        try {
                            fillMask(x+1, y, mask);
                        }catch (ArrayIndexOutOfBoundsException ignored2){}
                    }
                }
            }
        }

    }

    private int[] getRamp(){
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (!matrix[y][x].getRamp().equals("")) return new int[]{x, y};
            }
        }
        return new int[]{0, 0};
    }

    public List<Cell> toList(boolean first){
        List<Cell> cells = new ArrayList<>();
        boolean[][] mask = new boolean[size][size];

        if(first) {
            fillMaskInside(mask);
        } else {
            int[] ramp = getRamp();
            fillMask(ramp[0], ramp[1], mask);
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if(mask[y][x]) cells.add(matrix[y][x]);
            }
        }
        return cells;
    }
}
