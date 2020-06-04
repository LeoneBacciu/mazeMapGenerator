package windows.buttonView;

import javafx.scene.layout.BorderPane;
import windows.buttonView.components.CenterView;
import windows.matrixView.components.MatrixCell;
import windows.matrixView.matrix.Cell;

public class ButtonView extends BorderPane {
    CenterView centerView;

    public ButtonView(MatrixCell matrixCell){
        centerView = new CenterView(matrixCell);
        this.setCenter(centerView);
    }
}
