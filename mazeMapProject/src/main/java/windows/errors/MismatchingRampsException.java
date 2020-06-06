package windows.errors;

public class MismatchingRampsException extends Exception{
    public MismatchingRampsException(String ramp){
        super("Mismatching ramp "+ramp);
    }
}
