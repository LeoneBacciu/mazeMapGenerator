package windows.matrixView.components;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import windows.utils.FileManager;
import windows.utils.SavedState;

import java.util.Optional;

public class ExitAlert extends Alert {
    private final TabPane tabPane;
    private final int size;
    private final ButtonType buttonTypeExit = new ButtonType("Exit");
    private final ButtonType buttonTypeSave = new ButtonType("Save");
    private final ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    public ExitAlert(TabPane tabPane, int size) {
        super(Alert.AlertType.CONFIRMATION);
        this.tabPane = tabPane;
        this.size = size;
        getButtonTypes().clear();
        getButtonTypes().add(buttonTypeExit);
        if (!SavedState.isSaved()) {
            getButtonTypes().add(buttonTypeSave);
            setContentText("You have some unsaved changes");
        }
        getButtonTypes().add(buttonTypeCancel);
        setTitle("Confirm Exit");
        setHeaderText("Are you sure you want to exit?");
    }

    public void launch(){
        Optional<ButtonType> result = showAndWait();
        if (result.isPresent() && result.get() == buttonTypeExit) {
            Platform.exit();
            System.exit(0);
        }else if (result.isPresent() && result.get() == buttonTypeSave){
            FileManager fileManager = new FileManager(tabPane, size);
            if(fileManager.save(tabPane.getScene())) {
                Platform.exit();
                System.exit(0);
            }
        }
    }
}
