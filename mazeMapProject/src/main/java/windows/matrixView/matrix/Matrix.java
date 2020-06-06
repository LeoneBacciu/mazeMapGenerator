package windows.matrixView.matrix;

import windows.errors.FileCorruptedException;
import windows.errors.MissingRampsException;
import windows.errors.OpenMazeException;
import windows.utils.JsonCell;
import windows.utils.JsonType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    private final int size;
    private final int level;
    private final Cell[][] matrix;
    private final int[][] pairs = new int[][]{
            {1, 0},
            {0, -1},
            {-1, 0},
            {0, 1}
    };

    public Matrix(int size, int level){
        this.level = level;
        this.size = size;
        this.matrix = new Cell[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                this.matrix[y][x] = new Cell(new int[]{y, x});
            }
        }
    }

    public Matrix(int size, int level, JsonType jsonType) throws FileCorruptedException {
        this.level = level;
        this.size = size;
        this.matrix = new Cell[size][size];

        try{
            for (JsonCell jsonCell : jsonType.body.get(level)) {
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
        }catch (IndexOutOfBoundsException e){
            throw new FileCorruptedException(e);
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
        if(mask[y][x]) return;
        mask[y][x] = true;
        for (int i = 0; i < 4; i++) {
            if(matrix[y][x].getWalls()[i]==0){
                fillMask(x+pairs[i][1], y+pairs[i][0], mask);
            }
        }
    }

    private void fillMaskRamp(boolean[][] mask) throws MissingRampsException, OpenMazeException {
        int[] ramp = getRamp();
        try {
            fillMask(ramp[0], ramp[1], mask);
        }catch (ArrayIndexOutOfBoundsException ignored){
            throw new OpenMazeException(level);
        }
    }

    private void fillMaskInside(boolean[][] mask) throws OpenMazeException {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (matrix[y][x].getWalls()[0]==1){
                    try {
                        try {
                            fillMask(x, y, mask);
                            return;
                        }catch (ArrayIndexOutOfBoundsException ignored){
                            for (boolean[] m: mask) Arrays.fill(m, false);
                        }
                        fillMask(x+1, y, mask);
                        return;
                    }catch (ArrayIndexOutOfBoundsException ignored){
                            for (boolean[] m: mask) Arrays.fill(m, false);
                    }
                }
            }
        }
        throw new OpenMazeException(level);
    }

    private int[] getRamp() throws MissingRampsException {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (!matrix[y][x].getRamp().equals("")) return new int[]{x, y};
            }
        }
        throw new MissingRampsException(level);
    }

    public List<Cell> toList() throws OpenMazeException, MissingRampsException {
        List<Cell> cells = new ArrayList<>();
        boolean[][] mask = new boolean[size][size];

        if(level==0) {
            fillMaskInside(mask);
        } else {
            fillMaskRamp(mask);
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if(mask[y][x]) cells.add(matrix[y][x]);
            }
        }
        return cells;
    }
}
