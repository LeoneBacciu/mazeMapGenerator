package windows.matrixView.matrix;

import java.util.Arrays;
import java.util.List;

public class Cell {
    private int[] coord;
    private final int[] walls;
    private boolean black;
    private boolean checkpoint;
    private boolean victim;

    private String ramp;

    public Cell(int[] coord){
        this.coord = coord;
        this.walls = new int[4];
        Arrays.fill(walls, 0);
        this.black = false;
        this.checkpoint = false;
        this.victim = false;
        this.ramp = "";
    }

    public Cell(int[] coord, int[] walls, boolean black, boolean checkpoint, boolean victim, String ramp, List<String> ramps){
        this.coord = coord;
        this.walls = walls;
        this.black = black;
        this.checkpoint = checkpoint;
        this.victim = victim;
        this.ramp = ramp;
        if(!this.ramp.equals("")){
            Ramps.selected(this.ramp);
            ramps.add(this.ramp);
        }
    }

    public String getStyle(){
        return "-fx-background-color: " +
                getBackgroundColor() +
                "    ;\n-fx-background-radius:0;\n" +
                "    -fx-border-width: 2.5;\n" +
                "    -fx-border-color: " +
                getColor(walls[1]) +
                getColor(walls[0]) +
                getColor(walls[3]) +
                getColor(walls[2]) +
                ";";
    }

    private String getBackgroundColor(){
        if (isBlack()) return "#000000";
        if (isCheckpoint()) return "#C0C0C0";
        return "transparent";
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

    public boolean setBlack(boolean black) {
        boolean ret = black != isBlack();
        this.black = black;
        return ret;
    }

    public boolean setCheckpoint(boolean checkpoint) {
        boolean ret = checkpoint != isCheckpoint();
        this.checkpoint = checkpoint;
        return ret;
    }

    public boolean setVictim(boolean victim) {
        boolean ret = victim != hasVictim();
        this.victim = victim;
        return ret;
    }

    public void setRamp(String ramp) {
        this.ramp = ramp;
    }

    public String getRamp() {
        return this.ramp;
    }

    public boolean isBlack() {
        return black;
    }

    public boolean isCheckpoint() {
        return checkpoint;
    }

    public boolean hasVictim() {
        return victim;
    }

}
