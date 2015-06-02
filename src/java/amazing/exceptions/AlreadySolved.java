package amazing.exceptions;

/**
 * Created by nsimi on 6/2/15.
 */
public class AlreadySolved extends AmazingException {
    public AlreadySolved(String mazeId){
        super(420,"Maze["+mazeId+"] is already Solved");
    }
}
