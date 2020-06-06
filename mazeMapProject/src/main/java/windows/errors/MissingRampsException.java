package windows.errors;

public class MissingRampsException extends Exception{
    public MissingRampsException(int level){
        super("No ramps detected on level "+level);
    }
}
