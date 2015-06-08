package amazing.generation;

import amazing.exceptions.UnknownAlgorithm;
import amazing.model.Maze;
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
            fillIn(maze.getCanvas(),width-2,height-2,1,1,"Whole");
            return maze.makePrintReady();
        }

        private void fillIn(char[][] canvas, int width, int height, int xOrigin, int yOrigin, String type) {
//            System.out.println(String.format("[%s] Fill in width[%d], height[%d], xOrigin[%d], yOrigin[%d]",type,width,height,xOrigin,yOrigin));
            if ( width < 3 || height < 3 )
                return;
            int wallOnYAxis = (height != 3 ) ? randomInRange(yOrigin+1,height+yOrigin-1) : yOrigin+1;
            int wallOnXAxis = (width != 3 ) ? randomInRange(xOrigin+1,width+xOrigin-1) : xOrigin+1;


            List<Wall> walls = new ArrayList<Wall>(Arrays.asList(
                    new Wall(wallOnXAxis,yOrigin,wallOnXAxis,wallOnYAxis),//Top
                    new Wall(wallOnXAxis,wallOnYAxis+1,wallOnXAxis,yOrigin+height),//Bottom
                    new Wall(xOrigin,wallOnYAxis,wallOnXAxis,wallOnYAxis),//LEft
                    new Wall(wallOnXAxis+1,wallOnYAxis,width+xOrigin,wallOnYAxis))//Right
            );


//            System.out.println("Y Axis wall: "+wallOnYAxis);
//            System.out.println("X Axis wall: "+wallOnXAxis);
//            for ( Wall e : walls )
//                System.out.println(e);

            for ( int i = yOrigin; i < height+yOrigin; ++i ) {
                canvas[i][wallOnXAxis] = Maze.WALL_CHAR;
            }
            for ( int i = xOrigin; i < width+xOrigin; ++i ) {
                canvas[wallOnYAxis][i] = Maze.WALL_CHAR;
            }


            //Punch Holes
            int holePunchCount = 0;
            do{
                Wall wallE =  walls.remove(walls.size() > 1 ? randomInRange(0,walls.size()) : 0);
//                if ( wallE.length() < 3) continue;
                int xPos = wallE.isVerticalWall() ? wallE.getEndingPoint().getXPosition() : randomInRange(wallE.getStartingPoint().getXPosition(),wallE.getEndingPoint().getXPosition());
                int yPos = wallE.isHorizontalWall() ? wallE.getEndingPoint().getYPosition() : randomInRange(wallE.getStartingPoint().getYPosition(),wallE.getEndingPoint().getYPosition());
//                System.out.println(String.format("xPos[%d]yPos[%d], wall:%s",xPos,yPos,wallE));
                canvas[yPos][xPos]=Maze.OPEN_CHAR;
                holePunchCount++;
            }while( !walls.isEmpty() && holePunchCount < 3 );
//            System.out.println("HolePunch Count:"+holePunchCount);


//            System.out.println(Maze.convertToString(canvas));
            //Top Left
            fillIn(canvas,wallOnXAxis-xOrigin,wallOnYAxis-yOrigin,xOrigin,yOrigin,type+":TopLeft");
            //Top Right
            fillIn(canvas,width-((wallOnXAxis-xOrigin)+1),wallOnYAxis-yOrigin,wallOnXAxis+1,yOrigin,type+":TopRight");
            //Bottom Right
            fillIn(canvas, width - ((wallOnXAxis - xOrigin) + 1), height - ((wallOnYAxis - yOrigin) + 1), wallOnXAxis + 1, wallOnYAxis + 1, type + ":BottomRight");
            //Bottom Left
            fillIn(canvas,wallOnXAxis-xOrigin,height-((wallOnYAxis-yOrigin)+1),xOrigin,wallOnYAxis+1,type+":BottomLeft");
        }
    });

    IMazeBuilder _builder;

    //Number between min(inclusive) and Max(exlusive)
    static public int randomInRange(int min, int max){
//        System.out.println(String.format("Random in range[%d->%d]",min,max));
        if (min == max) return min;
        return _random.nextInt(max-min)+min;
    }

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
