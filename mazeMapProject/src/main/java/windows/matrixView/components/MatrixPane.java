package windows.matrixView.components;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import windows.matrixView.matrix.Matrix;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MatrixPane extends GridPane {
    private Matrix matrix;
    private ArrayList<ArrayList<MatrixButton>> buttons;
    private int size;

    public MatrixPane(int size){
        matrix = new Matrix(size);
        buttons = new ArrayList<>();
        for(int i = 0; i < size; i++)
            buttons.add(new ArrayList<>());

        buttons.forEach(row -> {
            for(int i = 0; i < size; i++)
                row.add(new MatrixButton(matrix, this));
        });

        this.size = size;

        this.setMaxWidth(Double.MAX_VALUE);
        this.setMaxHeight(Double.MAX_VALUE);

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

        AtomicInteger mcol = new AtomicInteger();
        buttons.forEach((row) -> {
            AtomicInteger pos = new AtomicInteger();
            row.forEach(button -> {
                button.setCell(matrix.get(mcol.get()).get(pos.get()));
                button.setText("Cell {" + button.getCell().getCoord()[0] +
                        ", " + button.getCell().getCoord()[1] + "}");
                // adding current button to PushButton
                add(button, pos.getAndIncrement(), mcol.get());
                setHgrow(button, Priority.ALWAYS);
                setVgrow(button, Priority.ALWAYS);
            });
            mcol.getAndIncrement();
        });
    }

    public ArrayList<ArrayList<MatrixButton>> getButtons() {
        return buttons;
    }
    public Matrix getMatrix() {
        return matrix;
    }
}
