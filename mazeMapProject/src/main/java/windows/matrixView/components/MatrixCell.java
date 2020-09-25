package windows.matrixView.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import windows.matrixView.matrix.Cell;
import windows.matrixView.matrix.Ramps;
import windows.utils.SavedState;

import java.util.Arrays;
import java.util.List;

public class MatrixCell extends BorderPane {
    private final Cell cell;
    private final MatrixPane matrixPane;
    private boolean isStart;
    private final Button settingsButton;
    private long clickStartTime;
    private final Button[] buttons;
    private final int[][] pairs = new int[][]{
            {1, 0},
            {0, -1},
            {-1, 0},
            {0, 1}
    };

    public MatrixCell(Cell cell, MatrixPane matrixPane) {
        this.cell = cell;
        this.matrixPane = matrixPane;
        this.isStart = false;
        this.buttons = new Button[4];
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);
        settingsButton = new Button("\u2699");
        settingsButton.addEventFilter(MouseEvent.ANY, this::settings);
        setCenter(settingsButton);
        setRight(buildButton(8594, 0));
        setTop(buildButton(8593, 1));
        setLeft(buildButton(8592, 2));
        setBottom(buildButton(8595, 3));
        updateStyle();
    }

    private Button buildButton(int arrow, int wall) {
        Button tmpButton = new Button(String.valueOf((char) arrow));
        tmpButton.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) invertVictim(wall);
            else if (e.getButton() == MouseButton.PRIMARY) invertCellWall(wall);
            updateStyle();
        });
        buttons[wall] = tmpButton;
        setAlignment(tmpButton, Pos.CENTER);
        return tmpButton;
    }

    private void settings(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                clickStartTime = System.currentTimeMillis();
            } else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                if (System.currentTimeMillis() - clickStartTime > 300) {
                    nextBump();
                } else {
                    if (cell.isBlack()) {
                        cell.setBlack(false);
                        cell.setCheckpoint(true);
                    } else if (cell.isCheckpoint()) {
                        cell.setCheckpoint(false);
                    } else {
                        cell.setBlack(true);
                    }
                }
                updateStyle();
                SavedState.setSaved(false);
            }
        } else if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED) && e.getButton() == MouseButton.SECONDARY) {
            List<String> ramps = Arrays.asList(Ramps.getAvailable(cell.getRamp(), matrixPane.getMatrix().getRamps()));
            int index = ramps.indexOf(cell.getRamp());
            Ramps.reverse(ramps.get(index));
            matrixPane.getMatrix().getRamps().remove(ramps.get(index));
            if (index == ramps.size() - 1) index = 0;
            else index += 1;
            cell.setRamp(ramps.get(index));
            Ramps.selected(cell.getRamp());
            matrixPane.getMatrix().getRamps().add(cell.getRamp());
            updateStyle();
            SavedState.setSaved(false);
        } else if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED) && e.getButton() == MouseButton.MIDDLE) {
            nextBump();
            SavedState.setSaved(false);
        }
    }

    private void invertCellWall(int wall) {
        this.cell.invertWall(wall);
        try {
            int newX = pairs[wall][1] + cell.getCoord()[1], newY = pairs[wall][0] + cell.getCoord()[0];
            Cell c = matrixPane.getMatrix().getMatrix()[newY][newX];
            c.getWalls()[mirrorWall(wall)] = cell.getWalls()[wall];
            matrixPane.getCells()[newY][newX].updateStyle();
        } catch (IndexOutOfBoundsException ignored) {
        }
        updateStyle();
        SavedState.setSaved(false);
    }

    private void invertVictim(int wall) {
        this.cell.invertVictim(wall);
        updateStyle();
    }

    private void nextBump() {
        this.cell.nextBump();
        updateStyle();
    }

    private int mirrorWall(int i) {
        switch (i) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
            default:
                return -1;
        }
    }

    public Cell getCell() {
        return cell;
    }

    public void updateStyle() {
        setStyle("-fx-background-color: " +
                getBackgroundColor() +
                "    ;\n-fx-background-radius:0;\n" +
                "    -fx-border-width: 2.5;\n" +
                "    -fx-border-color: " +
                getWallColor(cell.getWalls()[1]) +
                getWallColor(cell.getWalls()[0]) +
                getWallColor(cell.getWalls()[3]) +
                getWallColor(cell.getWalls()[2]) +
                ";");
        settingsButton.setText((cell.getRamp().equals("")) ? "\u2699" : cell.getRamp());
        settingsButton.setStyle(
                "-fx-background-color: " + getSettingsColor() + ";\n-fx-background-radius:0;\n"
        );
        for (int wall=0; wall< buttons.length; wall++) buttons[wall].setStyle(
                "-fx-background-color: " + getButtonColor(wall) + ";\n-fx-background-radius:0;\n"
        );
    }

    private String getBackgroundColor() {
        if (isStart) return "#0000ff";
        if (cell.isBlack()) return "#000000";
        if (cell.isCheckpoint()) return "#C0C0C0";
        return "transparent";
    }

    private String getButtonColor(int wall) {
        if (cell.getVictims()[wall] == 1) return "#ff0000";
        return "white";
    }

    private String getSettingsColor() {
        switch (this.cell.getBump()) {
            case "SMALL":
                return "#ff9900";
            case "BIG":
                return "#996600";
            default:
                return "white";
        }
    }

    private String getWallColor(int wall) {
        return (wall == 1) ? "black " : "transparent ";
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean setStart(boolean start) {
        if (start && (cell.isBlack() || cell.isCheckpoint() || !cell.getRamp().equals("") || !cell.getBump().equals("")))
            return false;
        isStart = start;
        updateStyle();
        return true;
    }
}