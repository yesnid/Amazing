# Amazing
Maze Puzzle Hosting Server

Originally developed as a C++ application that would DL open solver libraries,
I have moved this to a Java Servlet that now generates and hosts mazes.

The idea is that you can use this as teaching or teambuilding tool. Write a solver in anylanguage, and point it at the
server to solve the mazes.

The following are the supported URLs


GET:

       /mazestart?id={foo}&maze={id} => is a request to start the maze for your passed id. This will return a MazePosition json in the body.

       /currentview?id={foo}&maze={id} => is a request to know what each adjacent space looks like (e.g wall or open), This will return a view object in the body as json.

       /query?id={foo}&maze={id}&type={currentposition,endlocation,victorinfo} => requests

       /maze => returns the list of active maze ids.


POST:

       /move?id=foo&direction={North,East,South,West}&maze=id => is a request to move from the current position in the direction given, will return {242} if the maze is solved.

       /create?width={#}&height={#} => creates a new maze.

The flow is as follows:

    1. query existing mazes with the '/maze' url
    2. pick a maze to solve
    3. GET on the mazestart URL, and pass in your solver id(must be unique acrosss all solvers)
    4. Read in your starting position
    5. GET your view, with the '/currentview' url
    6. Move a direction with a POST to the '/move' url.
    7. Repeat 5 & 6 until solved.


Return codes:

    - 4XX Bad Request
        -- 420 Maze already solved you are too late
        -- 421 invalid move
        -- 400 invalid maze, solver id in use, unknown algorithm, unknown solver id
        -- 423 too many active mazes
        -- 422 too many solvers
    - 5XX Internal Server Error
        --
    - 2XX Success
        -- 242 Maze Solved


Example Maze:

        ##########################################
        #       #   #                            #
        ### ##    # ## ## ########## #############
        #    #  #   #   # #                      #
        # ######### ################# ############
        #  #    # # #                         #  #
        #       #   ##### #                      #
        ##### ### # #     ##################### ##
        #  #    # # # # # #                      #
        #  #### # #   # # ######## ######### ##  #
        #         # # # # #                #  #  #
        ######## ########## ######################
        # # #       #       X               #    #
        #   ### # # #### ####################  # #
        # # # # # # #*   #   #           #  # ## #
        ### # # # # #    #   ######### ### ##  # #
        # #     # # #### ##              #  #  # #
        # # # # # # #        ##### ### ###     # #
        #   ### # ###    #   #       #      #  # #
        # # #       #################### ####### #
        # # #   #   # #                     #  # #
        ##### ####### #                     #  # #
        #   #   #   # ####### ###############  # #
        # #         #         #             ## # #
        #   ## ## ### #### ######## #########    #
        # # #   #   # #                     #  # #
        ##########################################