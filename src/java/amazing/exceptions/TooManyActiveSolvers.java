/*
 * Copyright (c) 2015 Rhythm NewMedia
 */
package amazing.exceptions;

/**
 * Created by nsimi on 6/8/15.
 */
public class TooManyActiveSolvers extends AmazingException {

    public TooManyActiveSolvers(){
        super(422,"Too many solvers are active");
    }

}
