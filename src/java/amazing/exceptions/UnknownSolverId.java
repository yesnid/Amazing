package amazing.exceptions;

/**
 * Created by nsimi on 6/1/15.
 */
public class UnknownSolverId extends AmazingException {
    public UnknownSolverId(String id){
        super(400,"Solver["+id+"] is unknown");
    }
}
