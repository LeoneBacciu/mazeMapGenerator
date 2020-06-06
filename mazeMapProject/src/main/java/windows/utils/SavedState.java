package windows.utils;

public class SavedState {
    private static boolean saved = true;

    public static boolean isSaved() {
        return saved;
    }

    public static void setSaved(boolean saved) {
        SavedState.saved = saved;
    }
}
