package util;

import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPress {

    private KeyCode code;
    private EventType<KeyEvent> type;

    public KeyPress(KeyCode code, EventType<KeyEvent> type) {
        this.code = code;
        this.type = type;
    }

    public KeyCode getCode() {
        return code;
    }

    public EventType<KeyEvent> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof KeyPress)) {
            return false;
        }

        KeyPress keyPress = (KeyPress) o;

        return keyPress.code.equals(code) &&
                keyPress.type.equals(type);
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + code.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
