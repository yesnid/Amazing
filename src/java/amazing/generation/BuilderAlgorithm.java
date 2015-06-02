package amazing.generation;

import amazing.exceptions.UnknownAlgorithm;
import amazing.model.Maze;

import java.util.Date;
import java.util.Random;

/**
 * Created by nsimi on 6/1/15.
 */
public enum  BuilderAlgorithm {
    RecursiveDivision(new IMazeBuilder() {
        @Override
        public Maze create(int width, int height) {
            Maze maze = new Maze(width,height);
            fillIn(maze.getCanvas(),width,height,0,0);
            return maze;
        }

        private void fillIn(char[][] canvas, int width, int height, int xOrigin, int yOrigin) {
            //int verticalWall = _random.nextInt()
        }
    });

    IMazeBuilder _builder;

    static private Random _random = new Random(new Date().getTime());

    private BuilderAlgorithm(IMazeBuilder builder){
        _builder = builder;
    }

    public Maze create(int width, int height){
        return _builder.create(width,height);
    }

    public static BuilderAlgorithm getAlgorithm(String name) throws UnknownAlgorithm {
        if (name == null)
            return RecursiveDivision;
        else if ( name.equals(RecursiveDivision.toString()) )
            return RecursiveDivision;
        throw new UnknownAlgorithm();
    }
}
