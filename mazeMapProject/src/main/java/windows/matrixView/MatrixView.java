package windows.matrixView;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import windows.matrixView.components.MatrixPane;
import windows.matrixView.components.TopBar;

public class MatrixView extends BorderPane {

    public MatrixView(int[] size){
        TabPane tabPane = new TabPane();
        for (int i = 0; i < size[1]; i++) {
            Tab tmpTab = new Tab();
            tmpTab.setText("Level "+i);
            tmpTab.setContent(new MatrixPane(size[0]));
            if (i==0) tmpTab.setClosable(false);
            tabPane.getTabs().add(tmpTab);
        }

        AddTab addTab = new AddTab();
        tabPane.getTabs().add(addTab);
        tabPane.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            int tabSize = tabPane.getTabs().size();
            if(newValue.intValue()==tabSize-1){
                tabPane.getSelectionModel().select(oldValue.intValue());
                Tab tmpTab = new Tab();
                tmpTab.setText("Level "+(tabSize-1));
                tmpTab.setContent(new MatrixPane(size[0]));
                tabPane.getTabs().add(tabSize-1, tmpTab);
            }
        });
        TopBar topBar = new TopBar(tabPane, size);
        setCenter(tabPane);
        setTop(topBar);
    }
}

class AddTab extends Tab{
    public AddTab(){
        setClosable(false);
        setText("+");
    }
}
