package util;

import java.util.ArrayList;
import java.util.List;

public class BoardState {
    private static List<String> stateList = new ArrayList<>();

    public static void add(String state) {
        stateList.add(state);
    }

    public static List<String> getStateList() {
        return stateList;
    }
}
