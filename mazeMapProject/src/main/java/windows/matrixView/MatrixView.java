package windows.matrixView;

import javafx.scene.layout.BorderPane;
import windows.matrixView.components.MatrixPane;
import windows.matrixView.components.TopBar;

public class MatrixView extends BorderPane {

    public MatrixView(){
        MatrixPane matrixPane = new MatrixPane(10);
        TopBar topBar = new TopBar(matrixPane.getMatrix());
        setCenter(matrixPane);
        setTop(topBar);
    }
}
