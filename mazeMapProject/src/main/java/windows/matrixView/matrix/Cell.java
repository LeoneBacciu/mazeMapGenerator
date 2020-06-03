package windows.matrixView.matrix;

import java.util.Arrays;

public class Cell {
    private int[] coord;
    private int[] walls;
    private boolean explored;
    private boolean black;
    private boolean checkpoint;
    private boolean victim;

    public Cell(){
        walls = new int[4];
        Arrays.fill(walls, 0);
        this.explored = false;
        this.black = false;
        this.checkpoint = false;
        this.victim = false;
    }

    public String getStyle(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-fx-background-color:transparent ;\n" +
                "    -fx-background-radius:0;\n" +
                "    -fx-border-color: #92a8d1;\n" +
                "    -fx-border-width: ");
        stringBuilder.append(walls[1]).append(" ");
        stringBuilder.append(walls[0]).append(" ");
        stringBuilder.append(walls[3]).append(" ");
        stringBuilder.append(walls[2]).append(" ");
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    public int[] getWalls() {
        return walls;
    }

    public void setRightWall(boolean b){
        this.walls[0] = b ? 1 : 0;
    }
    public void setTopWall(boolean b){
        this.walls[1] = b ? 1 : 0;
    }
    public void setLeftWall(boolean b){
        this.walls[2] = b ? 1 : 0;
    }
    public void setBottomWall(boolean b){
        this.walls[3] = b ? 1 : 0;
    }

    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public boolean isCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(boolean checkpoint) {
        this.checkpoint = checkpoint;
    }

    public boolean isVictim() {
        return victim;
    }

    public void setVictim(boolean victim) {
        this.victim = victim;
    }
}
