package amazing.model;

/**
 * Created by nsimi on 6/1/15.
 */
public class SolverStatus {

    private final String _solverId;
    private MazePosition _currentPosition;


    public SolverStatus(String solverId, Maze currentMaze) {
        _solverId = solverId;
        _currentPosition = currentMaze.getStartingPosition();
    }


    public MazePosition getCurrentPosition() {
        return _currentPosition;
    }

    public String getSolverId(){
        return _solverId;
    }

    public void setNewPosition(MazePosition newPosition) {
        _currentPosition = newPosition;
    }
}
