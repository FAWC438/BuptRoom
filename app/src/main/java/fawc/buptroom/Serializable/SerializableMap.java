package fawc.buptroom.Serializable;

import java.io.Serializable;
import java.util.Map;

public class SerializableMap implements Serializable {

    private Map<String, ?> map;

    public Map<String, ?> getMap() {
        return map;
    }

    public void setMap(Map<String, ?> map) {
        this.map = map;
    }
}
