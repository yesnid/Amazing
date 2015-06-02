package amazing.model;

/**
 * Created by nsimi on 6/1/15.
 */
public class MazePosition {
    private final int _xPosition;
    private final int _yPosition;

    @Override
    public String toString(){
        return String.format("{\"x\":%d,\"y\":%d}",_xPosition,_yPosition);
    }

    public MazePosition(int x, int y) {
        _xPosition = x;
        _yPosition = y;
    }

    public int getXPosition() {
        return _xPosition;
    }

    public int getYPosition() {
        return _yPosition;
    }
}
