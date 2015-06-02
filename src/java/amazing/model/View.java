package amazing.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nsimi on 6/1/15.
 */
public class View {
    public static enum ViewState{
        Wall,Open;
    }

    private Map<Direction,ViewState> _horizon = new HashMap<Direction, ViewState>(4);

    public View(){
        for (Direction direction : Direction.values())
            _horizon.put(direction,ViewState.Wall);
    }

    public void set(Direction direction, ViewState viewState) {
        _horizon.put(direction, viewState);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("{");
        for (Map.Entry<Direction,ViewState> entry : _horizon.entrySet()){
            builder.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"").append(",");
        }
        builder.setLength(builder.length()-1);
        builder.append("}");
        return builder.toString();
    }

}
