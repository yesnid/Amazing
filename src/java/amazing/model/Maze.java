package amazing.model;

import amazing.exceptions.AlreadySolved;
import amazing.exceptions.InvalidMove;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nsimi on 6/1/15.
 */
public class Maze {
    private MazePosition _startingPosition;
    private MazePosition _endingPosition;
    private String _id;
    private String _solvedBy;
    private Date _createdTime = new Date();
    private Date _solvedTime;
    private final char[][] _area;

    private boolean _solved = false;


    public Maze(){
        _id = UUID.randomUUID().toString();
        _area = new char[50][50];
    }

    public Maze(int x, int y){
        _id = UUID.randomUUID().toString();
        _area = new char[x][y];
    }

    public String getId(){
        return _id;
    }

    public char[][] getCanvas(){
        return _area;
    }

    public MazePosition getStartingPosition() {
        return _startingPosition;
    }
    public MazePosition getEndingPosition() {
        return _endingPosition;
    }

    public boolean isValidPosition(MazePosition position) {
        if ( position.getXPosition() < 0 || position.getXPosition() > _area.length )
            return false;
        if ( position.getYPosition() < 0 || position.getYPosition() > _area[0].length )
            return false;
        if ( _area[position.getXPosition()][position.getYPosition()] == '#' )
            return false;
        return true;
    }

    public String getSolvedBy(){
        return _solvedBy;
    }

    public boolean isSolved(){
        return _solved;
    }

    public Date getCreatedTime(){
        return _createdTime;
    }

    public Date getSolvedTime(){
        return _solvedTime;
    }

    public boolean IsSolved(SolverStatus status) throws AlreadySolved {
        if ( isSolved() )
            throw new AlreadySolved(_id);
        if ( status.getCurrentPosition().getXPosition() == getEndingPosition().getXPosition()
                && status.getCurrentPosition().getYPosition() == getEndingPosition().getXPosition() ) {
            _solvedTime = new Date();
            _solvedBy = status.getSolverId();
            return (_solved = true);
        }
        return false;
    }

    public View getView(SolverStatus status) {
        MazePosition position = status.getCurrentPosition();
        View view = new View();
        for (Direction d : Direction.values()){
            try {//not do or do not there is no try...
                d.applyDirection(position,this);
                view.set(d, View.ViewState.Open);
            }catch (InvalidMove e){
                view.set(d, View.ViewState.Wall);
            }
        }
        return view;
    }

    public String solutionStatus() {
        boolean solved = isSolved();
        StringBuilder builder = new StringBuilder("{").append("\"solved\":").append(solved);
        if ( solved ){
            builder.append(",\"by\":\"").append(_solvedBy).append("\",");
            builder.append("\"time\":\"").append(_solvedTime.toString()).append("\"");
        }
        builder.append("}");
        return builder.toString();
    }

    public String getSolverInfo() {
        if (!isSolved())
            return "{}";
        StringBuilder builder = new StringBuilder("{").append("\"by\":\"").append(_solvedBy).append("\",");
            builder.append("\"time\":\"").append(_solvedTime.toString()).append("\"");
        builder.append("}");
        return builder.toString();
    }
}
