package amazing.exceptions;

/**
 * Created by nsimi on 6/1/15.
 */
public class SolverIdAlreadyInUse extends AmazingException {

    public SolverIdAlreadyInUse(String id){
        super(400,"Solver id["+id+"] passed is already in use");
    }

}
