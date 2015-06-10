package amazing.model;

/**
 * Created by nsimi on 6/8/15.
 */
public class Door {

    private MazePosition _doorPosition;
    private boolean _wallIsHorizontal;

    public Door(MazePosition doorPosition, Boolean isHorizontallyOriented){
        _wallIsHorizontal = isHorizontallyOriented;
        _doorPosition = doorPosition;
    }

    public boolean isInHorizontalWall(){
        return _wallIsHorizontal;
    }

    public MazePosition getDoorPosition(){
        return _doorPosition;
    }
}
