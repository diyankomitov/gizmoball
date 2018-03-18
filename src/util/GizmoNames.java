package util;

import model.board.BoardObjectType;

import java.util.ArrayList;
import java.util.List;

public class GizmoNames {

    private static int bCounter=0;
    private static int cCounter=0;
    private static int tCounter=0;
    private static int sCounter=0;
    private static int lfCounter=0;
    private static int rfCounter=0;
    private static int aCounter=0;

    private static List<String> nameList = new ArrayList<>();

    public static boolean addName(String name) {
       return nameList.add(name);
    }

    public static String addName(BoardObjectType type){
        String name = "";
        switch(type) {
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

        if(addName(name)) {
            return name;
        }
        return addName(type);

    }

    public static boolean nameExists(String name) {
        return nameList.contains(name);
    }

    public static void resetNames() {
        bCounter=0;
        cCounter=0;
        tCounter=0;
        sCounter=0;
        lfCounter=0;
        rfCounter=0;
        aCounter=0;
        nameList.clear();
    }
}
