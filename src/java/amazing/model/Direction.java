package amazing.model;

import amazing.exceptions.InvalidMove;

/**
 * Created by nsimi on 6/1/15.
 */
public enum Direction {
    North(0,-1),East(1,0),South(0,1),West(-1,0);

    Direction(int xDiff, int yDiff){
        _xDiff = xDiff;
        _yDiff = yDiff;
    }

    int _xDiff, _yDiff;
    
    public MazePosition applyDirection(MazePosition currentPosition, Maze maze) throws InvalidMove {
        MazePosition result = new MazePosition(currentPosition.getXPosition()+_xDiff,currentPosition.getYPosition()+_yDiff);
        if ( !maze.isValidPosition(result) )
            throw new InvalidMove();
        return result;
    }

}
