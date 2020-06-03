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

    public void invertWall(int wall){
        this.walls[wall]=(this.walls[wall]==1)?0:1;
    }

    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public void setCheckpoint(boolean checkpoint) {
        this.checkpoint = checkpoint;
    }

    public void setVictim(boolean victim) {
        this.victim = victim;
    }
}
