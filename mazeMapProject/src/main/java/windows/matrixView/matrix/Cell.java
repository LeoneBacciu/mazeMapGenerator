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
        return "-fx-background-color:transparent ;\n" +
                "    -fx-background-radius:0;\n" +
                "    -fx-border-width: 2.5;\n" +
                "    -fx-border-color: " +
                getColor(walls[1]) +
                getColor(walls[0]) +
                getColor(walls[3]) +
                getColor(walls[2]) +
                ";";
    }

    private String getColor(int wall){
        return (wall==1)?"#92a8d1 ":"transparent ";
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

    public void invertWall(int wall){
        this.walls[wall]=(this.walls[wall]==1)?0:1;
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
