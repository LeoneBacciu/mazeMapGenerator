import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import windows.matrixView.MatrixView;
import windows.matrixView.SavedState;
import windows.matrixView.components.TopBar;

import java.util.Optional;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new MatrixView(new int[]{10, 1}));
        primaryStage.setTitle("Maze");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getButtonTypes().clear();
            ButtonType buttonTypeExit = new ButtonType("Exit");
            ButtonType buttonTypeSave = new ButtonType("Save");
            alert.getButtonTypes().add(buttonTypeExit);
            if (!SavedState.isSaved()) {
                alert.getButtonTypes().add(buttonTypeSave);
                alert.setContentText("You have some unsaved changes");
            }
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().add(buttonTypeCancel);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeCancel) {
                event.consume();
            }else if (result.isPresent() && result.get() == buttonTypeSave){
                TopBar view = (TopBar) ((MatrixView) scene.getRoot()).getTop();
                view.getMenus().get(0).getItems().get(0).fire();
            }
        });
        primaryStage.show();
    }
}
