package amazing.exceptions;

/**
 * Created by nsimi on 6/2/15.
 */
public class Solved extends AmazingException {
    public Solved(String mazeId){
        super(242,"Congratulations, you have solved the maze: "+mazeId);
    }
}
