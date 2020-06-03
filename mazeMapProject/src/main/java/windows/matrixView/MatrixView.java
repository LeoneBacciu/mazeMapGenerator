package windows.matrixView;

import javafx.scene.layout.BorderPane;
import windows.matrixView.components.MainPane;
import windows.matrixView.components.TopBar;

public class MatrixView extends BorderPane {

    public MatrixView(){
        MainPane matrixPane = new MainPane(10);
        TopBar topBar = new TopBar(matrixPane.getMatrix());
        setCenter(matrixPane);
        setTop(topBar);
    }
}
