import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import windows.matrixView.MatrixView;
import windows.matrixView.components.ExitAlert;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        int[] size = new int[]{10, 1};
        Scene scene = new Scene(new MatrixView(size));
        primaryStage.setTitle("Maze");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            TabPane tabPane = (TabPane) ((MatrixView) scene.getRoot()).getCenter();
            event.consume();
            ExitAlert exitAlert = new ExitAlert(tabPane, size[0]);
            exitAlert.launch();
        });
        primaryStage.show();
    }
}
