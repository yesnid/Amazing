/*
 * Copyright (c) 2015 Rhythm NewMedia
 */
package amazing.exceptions;

/**
 * Created by nsimi on 6/8/15.
 */
public class TooManyActiveMazes extends AmazingException {

    public TooManyActiveMazes(){
        super(423,"Too many mazes are active already");
    }

}