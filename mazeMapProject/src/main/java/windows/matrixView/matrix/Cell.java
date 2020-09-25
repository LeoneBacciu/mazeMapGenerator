package windows.matrixView.matrix;

import java.util.List;

public class Cell {
    private int[] coord;
    private final int[] walls;
    private boolean black;
    private boolean checkpoint;
    private final int[] victims;
    private String bump;
    private String ramp;

    public Cell(int[] coord) {
        this.coord = coord;
        this.walls = new int[4];
        this.black = false;
        this.checkpoint = false;
        this.victims = new int[4];
        this.bump = "";
        this.ramp = "";
    }

    public Cell(int[] coord, int[] walls, boolean black, boolean checkpoint, int[] victims, String bump, String ramp, List<String> ramps) {
        this.coord = coord;
        this.walls = walls;
        this.black = black;
        this.checkpoint = checkpoint;
        this.victims = victims;
        this.bump = bump;
        this.ramp = ramp;
        if (!this.ramp.equals("")) {
            Ramps.selected(this.ramp);
            ramps.add(this.ramp);
        }
    }

    public int[] getWalls() {
        return walls;
    }

    public int[] getVictims() {
        return victims;
    }

    public void invertWall(int wall) {
        this.walls[wall] = (this.walls[wall] == 1) ? 0 : 1;
    }

    public void invertVictim(int wall) {
        this.victims[wall] = (this.victims[wall] == 1) ? 0 : 1;
    }

    public void nextBump() {
        if (bump.equals("SMALL")) bump = "BIG";
        else if (bump.equals("BIG")) bump = "";
        else bump = "SMALL";
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

    public String getBump() {
        return bump;
    }

    public void setBump(String bump) {
        this.bump = bump;
    }
}
