package windows.matrixView.matrix;

import java.util.List;

public class JsonType {
    public int[] header;
    public List<List<JsonCell>> body;
}

class JsonCell {
    public int[] coord;
    public int[] walls;
    public boolean explored;
    public boolean black;
    public boolean checkpoint;
    public boolean victim;
    public String ramp;
}
