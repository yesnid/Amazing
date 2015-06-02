package amazing.generation;

import amazing.model.Maze;

/**
 * Created by nsimi on 6/2/15.
 */
public interface IMazeBuilder {

    public Maze create(int width, int height);

}
