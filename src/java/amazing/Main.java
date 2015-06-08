/*
 * Copyright (c) 2015 Rhythm NewMedia
 */
package amazing;

import amazing.generation.BuilderAlgorithm;
import amazing.model.Maze;

/**
 * Created by nsimi on 6/4/15.
 */
public class Main {

    public static void main(String[] args){
        System.out.println("Amazing...");
//        for ( int i = 0; i < 1000; ++i ) {
//             int value = BuilderAlgorithm.randomInRange(0, 10);
//            if ( value >= 10 || value < 0 ) throw new RuntimeException("SHIT: "+value);
//        }
        for ( int i = 0; i < 10; ++i ){
            Maze maze = BuilderAlgorithm.RecursiveDivision.create(20,10);
            System.out.println(maze);
        }
    }

}
