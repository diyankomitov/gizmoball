package util;

import javafx.scene.input.KeyCode;
import model.board.gizmos.Gizmo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Triggers {

    private Map<Gizmo, Gizmo> gizmoTriggers;
    private Map<KeyCode, Gizmo> keyTriggers;

    public Triggers() {
        gizmoTriggers = new HashMap<>();
        keyTriggers = new HashMap<>();
    }

    public void addTrigger(Gizmo g1, Gizmo g2){
        if(g1 != null && g2 != null){
            gizmoTriggers.put(g1, g2);
        }
    }

    public void addTrigger(KeyCode keyCode, Gizmo g){
        if(keyCode != null && g != null){
            keyTriggers.put(keyCode, g);
        }
    }

    public Gizmo getTriggeredGizmo(Gizmo g){
        if(gizmoTriggers.containsKey(g)){
            return gizmoTriggers.get(g);
        }
        return g;
    }

    public Gizmo getTriggeredGizmo(KeyCode keyCode){
        if(gizmoTriggers.containsKey(keyCode)){
            return gizmoTriggers.get(keyCode);
        }
        return null;
    }
}

