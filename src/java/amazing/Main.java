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
        for ( int i = 0; i < 10; ++i ){
            Maze maze = BuilderAlgorithm.RecursiveDivision.create(BuilderAlgorithm.randomInRange(10,50),BuilderAlgorithm.randomInRange(10,50));
            System.out.println(maze);
        }
    }

}
