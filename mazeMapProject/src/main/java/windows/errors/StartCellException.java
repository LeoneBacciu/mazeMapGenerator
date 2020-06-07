package windows.errors;

public class StartCellException extends Exception{
    public StartCellException(){
        super("The start cell is not inside the maze");
    }
}
