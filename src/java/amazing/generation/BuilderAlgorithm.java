package amazing.generation;

import amazing.exceptions.UnknownAlgorithm;
import amazing.model.Door;
import amazing.model.Maze;
import amazing.model.MazePosition;
import amazing.model.Wall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nsimi on 6/1/15.
 */
public enum  BuilderAlgorithm {
    RecursiveDivision(new IMazeBuilder() {
        @Override
        public Maze create(int width, int height) {
            Maze maze = new Maze(width,height);
            fillIn(maze.getCanvas(),width-2,height-2);
            return maze.chooseStartAndEndPositions();
        }

        private void fillIn(char[][] canvas, int width, int height) {
            List<Door> doors = fillIn(canvas,width,height,1,1,"whole");
            for ( Door door : doors ){
                if ( door.isInHorizontalWall() ){
                    canvas[door.getDoorPosition().getYPosition()-1][door.getDoorPosition().getXPosition()] = Maze.OPEN_CHAR;
                    canvas[door.getDoorPosition().getYPosition()+1][door.getDoorPosition().getXPosition()] = Maze.OPEN_CHAR;
                }else{
                    canvas[door.getDoorPosition().getYPosition()][door.getDoorPosition().getXPosition()+1] = Maze.OPEN_CHAR;
                    canvas[door.getDoorPosition().getYPosition()][door.getDoorPosition().getXPosition()-1] = Maze.OPEN_CHAR;
                }
            }
        }

        private List<Door> fillIn(char[][] canvas, int width, int height, int xOrigin, int yOrigin, String type) {
            if ( width < 3 || height < 3 )
                return new ArrayList<Door>();
            int wallOnYAxis = (height != 3 ) ? randomInRange(yOrigin+1,height+yOrigin-1) : yOrigin+1;
            int wallOnXAxis = (width != 3 ) ? randomInRange(xOrigin+1,width+xOrigin-1) : xOrigin+1;


            List<Wall> walls = new ArrayList<Wall>(Arrays.asList(
                    new Wall(wallOnXAxis,yOrigin,wallOnXAxis,wallOnYAxis),//Top
                    new Wall(wallOnXAxis,wallOnYAxis+1,wallOnXAxis,yOrigin+height),//Bottom
                    new Wall(xOrigin,wallOnYAxis,wallOnXAxis,wallOnYAxis),//LEft
                    new Wall(wallOnXAxis+1,wallOnYAxis,width+xOrigin,wallOnYAxis))//Right
            );

            for ( int i = yOrigin; i < height+yOrigin; ++i ) {
                canvas[i][wallOnXAxis] = Maze.WALL_CHAR;
            }
            for ( int i = xOrigin; i < width+xOrigin; ++i ) {
                canvas[wallOnYAxis][i] = Maze.WALL_CHAR;
            }


            //Punch Holes
            List<Door> doors = new ArrayList<Door>();
            do{
                Wall wallE =  walls.remove(walls.size() > 1 ? randomInRange(0,walls.size()) : 0);
                int xPos = wallE.isVerticalWall() ? wallE.getEndingPoint().getXPosition() : randomInRange(wallE.getStartingPoint().getXPosition(),wallE.getEndingPoint().getXPosition());
                int yPos = wallE.isHorizontalWall() ? wallE.getEndingPoint().getYPosition() : randomInRange(wallE.getStartingPoint().getYPosition(),wallE.getEndingPoint().getYPosition());
                Door doorway = new Door(new MazePosition(xPos,yPos),wallE.isHorizontalWall());
                canvas[yPos][xPos]=Maze.OPEN_CHAR;
                doors.add(doorway);
            }while( !walls.isEmpty() && doors.size() < 3 );


            //Top Left
            doors.addAll(fillIn(canvas,wallOnXAxis-xOrigin,wallOnYAxis-yOrigin,xOrigin,yOrigin,type+":TopLeft"));
            //Top Right
            doors.addAll(fillIn(canvas,width-((wallOnXAxis-xOrigin)+1),wallOnYAxis-yOrigin,wallOnXAxis+1,yOrigin,type+":TopRight"));
            //Bottom Right
            doors.addAll(fillIn(canvas, width - ((wallOnXAxis - xOrigin) + 1), height - ((wallOnYAxis - yOrigin) + 1), wallOnXAxis + 1, wallOnYAxis + 1, type + ":BottomRight"));
            //Bottom Left
            doors.addAll(fillIn(canvas,wallOnXAxis-xOrigin,height-((wallOnYAxis-yOrigin)+1),xOrigin,wallOnYAxis+1,type+":BottomLeft"));
            return doors;
        }
    });

    IMazeBuilder _builder;

    //Number between min(inclusive) and Max(exlusive)
    static public int randomInRange(int min, int max){
        if (min == max) return min;
        return _random.nextInt(max-min)+min;
    }

    static private Random _random = new Random(new Date().getTime());

    private BuilderAlgorithm(IMazeBuilder builder){
        _builder = builder;
    }

    public Maze create(int width, int height){
        int clippedWidth = width < 10 || width > 100 ? 20 : width;
        int clippedHeight = height < 10 || height > 100 ? 10 : height;
        return _builder.create(clippedWidth,clippedHeight);
    }

    public static BuilderAlgorithm getAlgorithm(String name) throws UnknownAlgorithm {
        if (name == null)
            return RecursiveDivision;
        else if ( name.equals(RecursiveDivision.toString()) )
            return RecursiveDivision;
        throw new UnknownAlgorithm();
    }
}
