
package amazing.model;

/**
 * Created by nsimi on 6/8/15.
 */
public class Wall {
    private MazePosition _startingPoint;
    private MazePosition _endingPoint;

    public Wall(int x0, int y0, int x1, int y1){
        _startingPoint = new MazePosition(x0,y0);
        _endingPoint = new MazePosition(x1,y1);
    }

    public long length(){
        return Math.round(Math.sqrt( Math.pow(_startingPoint.getXPosition()-_endingPoint.getXPosition(),2) + Math.pow(_startingPoint.getXPosition()-_endingPoint.getYPosition(),2) ));
    }

    public MazePosition getStartingPoint(){
        return _startingPoint;
    }

    public MazePosition getEndingPoint(){
        return _endingPoint;
    }

    public boolean isHorizontalWall(){//Horizontal means we have the same Y value for both points.
        return _endingPoint.getYPosition() == _startingPoint.getYPosition();
    }
    public boolean isVerticalWall(){//Vertical means that we have the same X value for both points.
        return _endingPoint.getXPosition() == _startingPoint.getXPosition();
    }

    @Override
    public String toString(){
        return new StringBuilder("{ \"start\":").append(_startingPoint).append(", \"end\":").append(_endingPoint).append(" }").toString();
    }

}
