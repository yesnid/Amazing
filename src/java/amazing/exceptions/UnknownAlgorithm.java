package amazing.exceptions;

/**
 * Created by nsimi on 6/2/15.
 */
public class UnknownAlgorithm extends AmazingException {
    public UnknownAlgorithm(){
        super(400,"Unknown builder algorithm");
    }
}
