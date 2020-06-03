package windows.buttonView;

import javafx.scene.layout.BorderPane;
import windows.buttonView.components.BottomButtonBar;
import windows.buttonView.components.CenterView;
import windows.matrixView.components.MatrixButton;
import windows.matrixView.components.MainPane;
import windows.matrixView.components.MatrixPane;
import windows.matrixView.matrix.Cell;

public class ButtonView extends BorderPane {
    CenterView centerView;

    public ButtonView(Cell cell, MatrixButton button, MatrixPane matrix){
        centerView = new CenterView();
        this.setCenter(centerView);
        this.setBottom(new BottomButtonBar(centerView, cell, button, matrix));
    }
}
