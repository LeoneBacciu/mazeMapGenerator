import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import windows.matrixView.MatrixView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new MatrixView(new int[]{10, 1}));
        primaryStage.setTitle("Maze");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
