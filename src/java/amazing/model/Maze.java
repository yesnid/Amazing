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
    private String _id = UUID.randomUUID().toString();
    private String _solvedBy;
    private Date _createdTime = new Date();
    private Date _solvedTime;
    private char[][] _area;

    private boolean _solved = false;

    public final static char WALL_CHAR = '#';
    public final static char OPEN_CHAR = ' ';
    public final static char UNUSED_CHAR = '+';

    public Maze(){
        initializeMaze(20,10);
    }

    public Maze(int x, int y){
        initializeMaze(x,y);
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
        if ( position.getYPosition() < 0 || position.getYPosition() > _area.length )
            return false;
        if ( position.getXPosition() < 0 || position.getXPosition() > _area[0].length )
            return false;
        if ( _area[position.getYPosition()][position.getXPosition()] == WALL_CHAR )
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

    private void initializeMaze(int x, int y) {
        if (x < 0) x = 20;
        if (y < 0) y = 10;
        if (x > 100) x = 20;
        if (y > 100) y = 10;
        _area = new char[y][x];

        for ( int i = 0; i < y; ++i ){
            _area[i][0] = WALL_CHAR;
            _area[i][x-1] = WALL_CHAR;
        }
        for ( int i = 0; i < x; ++i ){
            _area[0][i] = WALL_CHAR;
            _area[y-1][i] = WALL_CHAR;
        }
        for (int i = 0; i < y; ++i)
            for (int j = 0; j < x; ++j)
                if ( (i == 0 || i == y-1) || (j == 0 || j == x-1) )
                    _area[i][j] = WALL_CHAR;
                else _area[i][j] = UNUSED_CHAR;
    }

    public Maze makePrintReady() {
        for (int i = 0; i < _area.length; ++i)
            for (int j = 0; j < _area[0].length; ++j)
                if (_area[i][j] == UNUSED_CHAR)
                    _area[i][j] = OPEN_CHAR;
        return this;
    }

    public static String convertToString(char[][] area){
       StringBuilder builder = new StringBuilder();
        for ( char[] array : area  ) {
            for (char point : array)
                builder.append(point);
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public String toString(){
        return convertToString(_area);
    }

}
