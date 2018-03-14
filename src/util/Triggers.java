package util;

import javafx.scene.input.KeyCode;
import model.board.gizmos.Gizmo;

import java.util.*;

public class Triggers {

    private static Map<Gizmo, Set<Gizmo>> gizmoTriggers = new HashMap<>();
    private static Map<KeyCode, Set<Gizmo>> keyTriggers = new HashMap<>();


    public static void addTrigger(Gizmo trigger, Gizmo triggered){
        if(trigger != null && triggered != null){
            if (!gizmoTriggers.containsKey(trigger)) {
                gizmoTriggers.put(trigger, new HashSet<>());
            }
            gizmoTriggers.get(trigger).add(triggered);
        }

        printAllTriggers();
    }

    public static void addTrigger(KeyCode trigger, Gizmo triggered){
        if(trigger != null && triggered != null){
            if (!keyTriggers.containsKey(trigger)) {
                keyTriggers.put(trigger, new HashSet<>());
            }
            keyTriggers.get(trigger).add(triggered);
        }
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

    public static Set<Gizmo> getTriggeredGizmo(KeyCode trigger){
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
        for (KeyCode key : keyTriggers.keySet()) {
            System.out.print("Key: " + key.getName() + " Values: ");
            for (Gizmo value : keyTriggers.get(key)) {
                System.out.print("Gizmo " + value.getX() + ", " + value.getY());
            }
            System.out.println("");
        }
    }
}
