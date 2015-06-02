package amazing.servlet;

import amazing.exceptions.AmazingException;
import amazing.model.Direction;
import amazing.model.MazePosition;
import amazing.model.View;
import amazing.runner.MazeRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nsimi on 6/1/15.
 */
public class AmazingServlet extends HttpServlet {

    public static enum QueryTypes{
        CurrentPosition,EndPosition,StartPosition,VictorInfo,MazeStatus;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {//not do or do not there is no try...
        } catch (Throwable t) {
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /*
       /mazestart?id={foo}&maze={id} => is a request to start the maze for your passed id. This will return a MazePosition json in the body.

       /currentview?id={foo}&maze={id} => is a request to know what each adjacent space looks like (e.g wall or open), This will return a view object in the body as json.

       /query?id={foo}&maze={id}&type={currentposition,endlocation,victorinfo} => requests

       /maze => returns the list of active maze ids.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {//not do or do not there is no try....
            System.out.println("GET Request received:" + request);
            if (request.getRequestURI().toLowerCase().contains("maze")) {
                String result = MazeRunner.instance.getMazeIds();
                response.getWriter().write(result);
                return;
            }
            String id = request.getParameter("id"), maze = request.getParameter("maze");
            if (request.getRequestURI().toLowerCase().contains("mazestart")) {
                MazePosition result = MazeRunner.instance.addSolver(id, maze);
                response.getWriter().write(result.toString());
            } else if (request.getRequestURI().toLowerCase().contains("currentview")) {
                View view = MazeRunner.instance.getSolverView(id, maze);
                response.getWriter().write(view.toString());
            } else if (request.getRequestURI().toLowerCase().contains("query")) {
                String type = request.getParameter("type");
                String result = MazeRunner.instance.query(id, maze, QueryTypes.valueOf(type));
                response.getWriter().write(result);
            }
        } catch (AmazingException e) {
            response.getWriter().write(e.getResponseText());
            response.setStatus(e.getHttpStatus());
        }
    }

    /*
        /move?id=foo&direction={North,East,South,West}&maze=id => is a request to move from the current position in the direction given, will return {2XX} if the maze is solved.

        /create?width={#}&height={#} => creates a new maze.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("POST Request received:"+request);
        try{//not do or do not there is no try...
            if (request.getRequestURI().toLowerCase().contains("move")) {
                String id = request.getParameter("id"), maze = request.getParameter("maze");
                Direction direction = Direction.valueOf(request.getParameter("direction"));
                MazeRunner.instance.solverMove(id,direction,maze);
            } else if (request.getRequestURI().toLowerCase().contains("create")) {
                int width = Integer.valueOf(request.getParameter("width"));
                int height = Integer.valueOf(request.getParameter("height"));
                response.getWriter().write(MazeRunner.instance.create(width, height, null));
            }
        }catch (AmazingException e){
            response.getWriter().write(e.getResponseText());
            response.setStatus(e.getHttpStatus());
        }
    }

}
