package util;

import java.util.ArrayList;
import java.util.List;

public class BoardState {
    private static boolean saved = true;

    private static List<String> stateList = new ArrayList<>();

    public static void add(String state) {
        stateList.add(state);
        setSavedBoard(false);
    }

    public static List<String> getStateList() {
        return stateList;
    }

    public static void removeAll() {
        stateList.removeAll(getStateList());
        setSavedBoard(false);
    }

    public static boolean getSavedBoard() {
        return saved;
    }

    public static void setSavedBoard(boolean save) {
        saved=save;
    }
}
