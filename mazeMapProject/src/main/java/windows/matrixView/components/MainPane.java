package windows.matrixView.components;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import windows.matrixView.matrix.Matrix;

import java.util.concurrent.atomic.AtomicInteger;

public class MainPane extends GridPane {
    private Matrix matrix;
    private MatrixCell[][] buttons;
    private int size;

    public MainPane(int size){
        matrix = new Matrix(size);
        buttons = new MatrixCell[size][size];
        this.size = size;

        AtomicInteger col = new AtomicInteger();
        matrix.forEach((row) -> {
            AtomicInteger pos = new AtomicInteger();
            row.forEach(cell -> {
                //cell proprieties
                cell.setCoord(new int[]{
                        pos.get(),
                        col.get()
                });
                //adding current button to PushButton
                pos.getAndIncrement();
            });
            col.getAndIncrement();
        });

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                buttons[y][x] = new MatrixCell(matrix.get(y).get(x), this);
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
