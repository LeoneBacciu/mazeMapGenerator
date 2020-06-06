package windows.errors;

public class OpenMazeException extends Exception{
    public OpenMazeException(){
        super("The maze isn't closed");
    }
}
