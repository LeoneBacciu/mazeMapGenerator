package windows.matrixView.matrix;

import java.util.Arrays;

public class Ramps {
    static private final int[] ramps = new int[]{2, 2};

    public static String[] getAvailable(){
        if(ramps[0]>0 && ramps[1]>0) return new String[]{"A", "B"};
        else if (ramps[0]>0) return new String[]{"A"};
        else if (ramps[1]>0) return new String[]{"B"};
        else return new String[]{};
    }

    public static void Debug(){
        System.out.println("Debug: " + Arrays.toString(ramps));
    }

    public static void selected(String ramp){
        if (ramp.equals("")) return;
        if (ramp.equals("A")) ramps[0]--;
        else ramps[1]--;
    }

    public static void reverse(String ramp){
        if (ramp.equals("")) return;
        if (ramp.equals("A")) ramps[0]++;
        else ramps[1]++;
    }

}
