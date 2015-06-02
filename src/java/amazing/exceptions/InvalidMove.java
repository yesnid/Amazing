package amazing.exceptions;

/**
 * Created by nsimi on 6/1/15.
 */
public class InvalidMove extends AmazingException {
    public InvalidMove(){
        super(421,"The requested move is invalid");
    }
}
