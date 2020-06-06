import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import windows.matrixView.MatrixView;
import windows.matrixView.components.ExitAlert;
import windows.utils.SavedState;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        int[] size = new int[]{10, 1};
        Scene scene = new Scene(new MatrixView(size));
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.jpeg")));
        }catch (NullPointerException ignored){
            primaryStage.getIcons().add(new Image("file:src/main/assets/icon.jpeg"));
        }
        primaryStage.setTitle("Maze");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            TabPane tabPane = (TabPane) ((MatrixView) scene.getRoot()).getCenter();
            if(!SavedState.isSaved()){
                event.consume();
                ExitAlert exitAlert = new ExitAlert(tabPane, size[0]);
                exitAlert.launch();
            }else {
                Platform.exit();
                System.exit(0);
            }

        });
        primaryStage.show();
    }
}
