package util;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.board.gizmos.Gizmo;

import java.util.*;

public class Triggers {

    private static Map<Gizmo, Set<Gizmo>> gizmoTriggers = new HashMap<>();
    private static Map<KeyPress, Set<Gizmo>> keyTriggers = new HashMap<>();


    public static void addTrigger(Gizmo trigger, Gizmo triggered){
        if(trigger != null && triggered != null){
            if (!gizmoTriggers.containsKey(trigger)) {
                gizmoTriggers.put(trigger, new HashSet<>());
            }
            gizmoTriggers.get(trigger).add(triggered);
            BoardState.add("Connect " + trigger.getName() + " " + triggered.getName());
        }
        printAllTriggers();
    }

    public static void addTrigger(KeyPress trigger, Gizmo triggered){
        if(trigger != null && triggered != null){
            if (!keyTriggers.containsKey(trigger)) {
                keyTriggers.put(trigger, new HashSet<>());
            }
            keyTriggers.get(trigger).add(triggered);

            String type = null;
            if (trigger.getType() == KeyEvent.KEY_PRESSED) {
                type = "up";
            }
            else if (trigger.getType() == KeyEvent.KEY_RELEASED) {
                type = "down";
            }

            BoardState.add("KeyConnect key " + "\"" + trigger.getCode().getName() + "\" " + type + " " + triggered.getName());
        }
        printAllTriggers();
    }

    public static void removeTriggers(Gizmo gizmo) {
        for (Set<Gizmo> triggeredSet : gizmoTriggers.values()) {
            triggeredSet.remove(gizmo);
        }

        for (Set<Gizmo> triggeredSet : keyTriggers.values()) {
            triggeredSet.remove(gizmo);
        }

        gizmoTriggers.keySet().remove(gizmo);
    }

    public static void clear() {
        gizmoTriggers.clear();
        keyTriggers.clear();
    }

    public static Set<Gizmo> getTriggeredGizmos(Gizmo trigger){
        if(gizmoTriggers.containsKey(trigger)){
            return gizmoTriggers.get(trigger);
        }
        return new HashSet<>();
    }

    public static Set<Gizmo> getTriggeredGizmo(KeyPress trigger){
        if(keyTriggers.containsKey(trigger)){
            return keyTriggers.get(trigger);
        }
        return new HashSet<>();
    }

    private static void printAllTriggers() {

        System.out.println("GIZMO TRIGGERS:");
        for (Gizmo key : gizmoTriggers.keySet()) {
            System.out.print("Key: " + key.getX() + ", " + key.getY() + " Values: ");
            for (Gizmo value : gizmoTriggers.get(key)) {
                System.out.print("Gizmo " + value.getX() + ", " + value.getY());
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");

        System.out.println("KEY TRIGGERS:");
        for (KeyPress key : keyTriggers.keySet()) {
            System.out.print("Key: " + key.getCode() + ", " + key.getType() + " Values: ");
            for (Gizmo value : keyTriggers.get(key)) {
                System.out.print("Gizmo " + value.getX() + ", " + value.getY()+" ");
            }
            System.out.println("");
        }
    }
}