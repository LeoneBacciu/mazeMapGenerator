package windows.errors;

public class FileCorruptedException extends Exception{
    public FileCorruptedException(Throwable cause){
        super("The file is corrupted", cause);
    }
}
