package windows.buttonView;

import javafx.scene.layout.BorderPane;
import windows.buttonView.components.BottomButtonBar;
import windows.buttonView.components.CenterView;
import windows.matrixView.matrix.Cell;

public class ButtonView extends BorderPane {
    CenterView centerView;

    public ButtonView(Cell cell){
        centerView = new CenterView();
        this.setCenter(centerView);
        this.setBottom(new BottomButtonBar(centerView, cell));
    }
}
