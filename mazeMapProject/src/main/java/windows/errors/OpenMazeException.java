package windows.errors;

public class OpenMazeException extends Exception{
    public OpenMazeException(int level){
        super("The maze isn't closed (level "+level+")");
    }
}
