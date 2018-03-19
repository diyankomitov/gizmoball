package util;

import model.board.BoardObjectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GizmoNames {

    private int bCounter;
    private int cCounter;
    private int tCounter;
    private int sCounter;
    private int lfCounter;
    private int rfCounter;
    private int aCounter;

    private Set<String> nameSet;

    public GizmoNames() {
        nameSet = new HashSet<>();
        bCounter = 0;
        cCounter = 0;
        tCounter = 0;
        sCounter = 0;
        lfCounter = 0;
        rfCounter = 0;
        aCounter = 0;
    }

    public boolean addName(String name) {
        return nameSet.add(name);
    }

    public String addName(BoardObjectType type) {
        String name = "";
        switch (type) {
            case BALL:
                name = "B" + bCounter;
                bCounter++;
                break;
            case CIRCLE:
                name = "C" + cCounter;
                cCounter++;
                break;
            case TRIANGLE:
                name = "T" + tCounter;
                tCounter++;
                break;
            case SQUARE:
                name = "S" + sCounter;
                sCounter++;
                break;
            case LEFT_FLIPPER:
                name = "LF" + lfCounter;
                lfCounter++;
                break;
            case RIGHT_FLIPPER:
                name = "RF" + rfCounter;
                rfCounter++;
                break;
            case ABSORBER:
                name = "A" + aCounter;
                aCounter++;
                break;

        }

        if (addName(name)) {
            return name;
        }
        return addName(type);

    }

    public boolean nameExists(String name) {
        return nameSet.contains(name);
    }

    public void resetNames() {
        bCounter = 0;
        cCounter = 0;
        tCounter = 0;
        sCounter = 0;
        lfCounter = 0;
        rfCounter = 0;
        aCounter = 0;
        nameSet.clear();
    }
}
