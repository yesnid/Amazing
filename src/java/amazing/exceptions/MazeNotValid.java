package amazing.exceptions;

/**
 * Created by nsimi on 6/1/15.
 */
public class MazeNotValid extends AmazingException {

    public MazeNotValid(){
        super(400,"This is not a valid maze id.");
    }

}
