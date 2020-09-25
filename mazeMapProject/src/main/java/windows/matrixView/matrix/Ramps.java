package windows.matrixView.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ramps {
    static private final String[] rampNames = new String[]{"A", "B", "C"};
    static private final int[] ramps = new int[]{2, 2, 2};


    public static String[] getAvailable(String def, List<String> not){
        return getAvailable(true, def, not);
    }

    public static String[] getAvailable(boolean first, String def, List<String> not){
        List<String> outList = new ArrayList<>();
        for (int i = 0; i < ramps.length; i++) {
            if(rampNames[i].equals(def) || (ramps[i]>0 && !not.contains(rampNames[i])))
                outList.add(rampNames[i]);
        }
        if (first) outList.add(0, "");
        String[] out = new String[outList.size()];
        outList.toArray(out);
        return out;
    }

    public static void selected(String ramp){
        for (int i = 0; i < ramps.length; i++) {
            if(ramp.equals(rampNames[i])) {
                ramps[i]--;
                break;
            }
        }
    }

    public static void reverse(String ramp){
        for (int i = 0; i < ramps.length; i++) {
            if(ramp.equals(rampNames[i])) {
                ramps[i]++;
                break;
            }
        }
    }

    public static String evenRamps(){
        for (int i = 0; i < ramps.length; i++) {
            if(ramps[i]%2!=0) return rampNames[i];
        }
        return "";
    }

}
