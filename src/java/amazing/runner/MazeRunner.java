package amazing.runner;

import amazing.exceptions.*;
import amazing.generation.BuilderAlgorithm;
import amazing.model.*;
import amazing.servlet.AmazingServlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by nsimi on 6/1/15.
 */
public enum MazeRunner implements Runnable{
    instance;

    private final HashMap<String,SolverStatus> _solvers = new HashMap<String, SolverStatus>();
    private Map<String,Maze> _mazes = new HashMap<String, Maze>();
    private ScheduledExecutorService _executorService = Executors.newScheduledThreadPool(1);


    private MazeRunner(){
        _executorService.scheduleAtFixedRate(this,300,300, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        Date now = new Date();
        List<String> toBeRemoved = new ArrayList<String>();
        for ( Map.Entry<String,Maze> entry : _mazes.entrySet() ){
            if ( entry.getValue().isSolved() && (now.getTime() - entry.getValue().getSolvedTime().getTime()) > 300 )
                toBeRemoved.add(entry.getKey());
        }
        for (String key : toBeRemoved )
            _mazes.remove(key);
    }

    public boolean isKnownSolver(String solverId){
        return _solvers.containsKey(solverId);
    }

    private void validateMaze(String mazeId) throws MazeNotValid {
        if ( !_mazes.containsKey(mazeId) )
            throw new MazeNotValid();
    }

    private void validateUnknown(String solverId, String mazeId) throws MazeNotValid, SolverIdAlreadyInUse{
        validateMaze(mazeId);
        if ( isKnownSolver(solverId) )
            throw new SolverIdAlreadyInUse(solverId);
    }
    private void validateKnown(String solverId, String mazeId) throws MazeNotValid, UnknownSolverId {
        validateMaze(mazeId);
        if ( !isKnownSolver(solverId) )
            throw new UnknownSolverId(solverId);

    }

    public MazePosition addSolver(String solverId, String mazeId) throws MazeNotValid, SolverIdAlreadyInUse, TooManyActiveSolvers {
        validateUnknown(solverId, mazeId);
        if ( _solvers.size() > 1000 )
            throw new TooManyActiveSolvers();
        _solvers.put(solverId,new SolverStatus(solverId,_mazes.get(mazeId)));
        return _solvers.get(solverId).getCurrentPosition();
    }

    public void solverMove(String solverId, Direction direction, String mazeId) throws UnknownSolverId, MazeNotValid, InvalidMove, Solved, AlreadySolved {
        validateKnown(solverId, mazeId);
        SolverStatus status = _solvers.get(solverId);
        MazePosition newPosition = direction.applyDirection(status.getCurrentPosition(),_mazes.get(mazeId));
        status.setNewPosition(newPosition);
        if ( _mazes.get(mazeId).IsSolved(status) ) {
            _solvers.remove(solverId);
            throw new Solved(mazeId);
        }
    }


    public View getSolverView(String solverId, String mazeId) throws UnknownSolverId, MazeNotValid, SolverIdAlreadyInUse {
        validateUnknown(solverId, mazeId);
        SolverStatus status = _solvers.get(solverId);
        Maze maze = _mazes.get(mazeId);
        return maze.getView(status);
    }

    public String query(String id, String maze, AmazingServlet.QueryTypes query) throws AmazingException {

        switch (query) {
            case CurrentPosition:
                validateKnown(id,maze);
                return _solvers.get(id).getCurrentPosition().toString();
            case EndPosition:
                validateMaze(maze);
                return _mazes.get(maze).getEndingPosition().toString();
            case StartPosition:
                validateMaze(maze);
                return _mazes.get(maze).getStartingPosition().toString();
            case VictorInfo:
                validateMaze(maze);
                _mazes.get(maze).getSolverInfo();
            case MazeStatus:
                validateMaze(maze);
                return _mazes.get(maze).solutionStatus();
            default:
                throw new AmazingException(400, "Unknown Query Type");
        }
    }

    public String getMazeIds() {
        StringBuilder builder = new StringBuilder("[");
        for ( String id : _mazes.keySet() )
            builder.append("\"").append(id).append("\",");
        builder.setLength(builder.length()-1);
        return builder.append("]").toString();
    }

    public String create(int width, int height, String algorithm) throws TooManyActiveMazes, UnknownAlgorithm {
        if ( _mazes.size() > 10 )
            throw new TooManyActiveMazes();
        Maze maze = BuilderAlgorithm.getAlgorithm(algorithm).create(width, height);
        _mazes.put(maze.getId(),maze);
        return maze.getId();
    }
}
