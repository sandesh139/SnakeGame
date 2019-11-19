/**
 * Date: 2019-04-19
 * Name: Sandesh Timilsina
 */

import java.util.LinkedList;

/**GameManager class handles the game logic of the Snake Game  */

public class GameManager {


    /**
     * this linkedList snakeBody is used to store the x and y position of the snake.
     */
    LinkedList<Node> snakeBody = new LinkedList<>();
    /**
     * initiating integer playCounter to store the number of times console is printed
     */
    int playCounter = 1;

    /**
     * initiating the row and the column of the board
     */
    int DEFAULT_NUM_ROWS = 0;
    int DEFAULT_NUM_COLS = 0;

    /**
     * creating two dimensional arrays for storing the character for blocks, snake, and space
     */
    char[][] board;

    /**
     * initiating the integer for storing the row of the food in the board
     */
    int foodRow;

    /**
     * initiating the integer for storing the row of the food in the board
     */
    int foodCol;




    int blockRow;
    int blockColumn;
    /**
     * initiating the foodTaken boolean and is made true if the food is eaten by snake.
     */
    boolean foodTaken = false;

    /**
     * initiating the gameEnds string which is updated whenever the move is illegal.
     */
    String gameEnds = "";


    //counts the score for food
    double foodCounter;


    /**
     * constructor of the GameManager
     *
     * @param width  -width of the board
     * @param height -height of the board
     */
    public GameManager(int width, int height) {
        DEFAULT_NUM_COLS = height;
        DEFAULT_NUM_ROWS = width;

        snakeBody.addFirst(new Node(4 * DEFAULT_NUM_ROWS / 5, DEFAULT_NUM_COLS / 2 + 1));
    }


    /**
     * this method returns the height of the method.
     * @return
     */
    public int getHeight(){
        return DEFAULT_NUM_ROWS;
    }


    /**
     * this method returns the width of the method.
     * @return
     */
    public int getWidth(){
        return DEFAULT_NUM_COLS;
    }



    /**
     * this method returns the score of the point as a double data type
     * @return
     */
    public double getScore()
    {
        return foodCounter;
    }


    /**
     * isLegal function checks if the snakes move is legal or not
     * this returns true if the move is allowed and otherwise it returns false
     *
     * @param row - row of the snakes next move
     * @param col - column of the snake next move
     * @return
     */
    public boolean isLegal(int row, int col) {
        if (board[row][col] == '.' || board[row][col] == 'F') {
            return true;
        }
        return false;
    }


    /**
     * this method sets up the board
     */
    public void getBoard() {
        board = new char[DEFAULT_NUM_ROWS][DEFAULT_NUM_COLS];
        for (int row = 0; row < DEFAULT_NUM_ROWS; row++) {
            for (int column = 0; column < DEFAULT_NUM_COLS; column++) {
                board[row][column] = '.';
            }
        }
        board[snakeBody.getFirst().getX()][snakeBody.getFirst().getY()] = 'S'; //getting the initial head position

    }


    /**
     * this method is called for the files: maze-cross, maze-stripes
     * this method is called for getting the blocks as character 'X' in the board
     * the first two are the xL and yL position of the upper left corner of the wall, the second two are the
     * xR and yR position of the the lower right corner of the wall
     *
     * @param xL
     * @param yL
     * @param xR
     * @param yR
     */
    public void getBlocksCross(int xL, int yL, int xR, int yR) {
        for (int i = xL; i <= xR; i++) {
            for (int j = yL; j <= yR; j++) {
                if (i < DEFAULT_NUM_ROWS) {
                    if (j < DEFAULT_NUM_COLS) {
                        board[j][i] = 'X';
                    }
                }
            }
        }
    }


    /**
     * this method is called for the files : maze-simple, maze-zigzag-large, maze-zigzag-small
     * this method is called for getting the blocks as character 'X' in the board
     * the first two are the xL and yL position of the upper left corner of the wall, the second two are the
     * xR and yR position of the the lower right corner of the wall
     *
     * @param xL
     * @param yL
     * @param xR
     * @param yR
     */
    public void getBlocks(int xL, int yL, int xR, int yR) {
        for (int row = yL; row <= yR; row++) {
            board[row][xL] = 'X';
        }
        for (int column = xL; column <= xR; column++) {
            board[yL][column] = 'X';
        }
    }


    /**
     * this method gets random block as game progress.
     */
    public  void getMoreBlocks(){
        for (int row = 0; row < DEFAULT_NUM_ROWS; row++) {
            for (int column = 0; column < DEFAULT_NUM_COLS; column++) {
                int randomx = (int) (Math.random() * DEFAULT_NUM_ROWS);
                int randomy = (int) (Math.random() * DEFAULT_NUM_COLS);
                if (board[randomx][randomy] == '.') {
                    blockRow = randomx;
                    blockColumn = randomy;
                }
            }
        }

        board[blockRow][blockColumn] = 'X';
    }






    /**
     * this method generates random food once the fixed food is eaten by the snake
     */
    public void getFood() {
        for (int row = 0; row < DEFAULT_NUM_ROWS; row++) {
            for (int column = 0; column < DEFAULT_NUM_COLS; column++) {
                int randomx = (int) (Math.random() * DEFAULT_NUM_ROWS);
                int randomy = (int) (Math.random() * DEFAULT_NUM_COLS);
                if (board[randomx][randomy] == '.') {
                    foodRow = randomx;
                    foodCol = randomy;
                }
            }
        }
        board[foodRow][foodCol] = 'F';
    }





    /**
     * this method is called to assign the new head position of the snake to the first place of snakeGame
     *
     * @param direction specifies the direction snake moves
     */
    public void getDirection(String direction) {

        if (gameEnds != "end") {
            if (direction.toLowerCase().equals("up")) {
                snakeBody.addFirst(new Node(snakeBody.getFirst().getX() - 1, snakeBody.getFirst().getY()));

            }
            if (direction.toLowerCase().equals("down")) {
                snakeBody.addFirst(new Node(snakeBody.getFirst().getX() + 1, snakeBody.getFirst().getY()));

            }
            if (direction.toLowerCase().equals("right")) {
                snakeBody.addFirst(new Node(snakeBody.getFirst().getX(), snakeBody.getFirst().getY() + 1));
            }
            if (direction.toLowerCase().equals("left")) {
                snakeBody.addFirst(new Node(snakeBody.getFirst().getX(), snakeBody.getFirst().getY() - 1));

            }


            //if food is eaten
            if (foodRow == snakeBody.getFirst().getX() && foodCol == snakeBody.getFirst().getY()) {
                foodTaken = true;
                foodCounter ++;
                if (foodCounter>3){
                    foodCounter ++;
                }
                if (foodCounter>8){
                    foodCounter++;

                    if (foodCounter < 20) {
                        getMoreBlocks();
                    }
                }
                if (foodCounter>20){
                    foodCounter =foodCounter+5;
                }
            }

            //following two line of code is just doing modular to snake head by respective size of the board
            snakeBody.set(0, new Node((DEFAULT_NUM_ROWS + snakeBody.getFirst().getX()) % DEFAULT_NUM_ROWS,
                    (DEFAULT_NUM_COLS + snakeBody.getFirst().getY()) % DEFAULT_NUM_COLS));


            if (!isLegal(snakeBody.getFirst().getX(), snakeBody.getFirst().getY())) {
                if (snakeBody.getFirst().getX() != snakeBody.getLast().getX() || snakeBody.getFirst().getY()
                        != snakeBody.getLast().getY()) {
                    gameEnds = "end";   //updating string if game ends
                }
            }
            snakeBody.set(0, new Node((DEFAULT_NUM_ROWS + snakeBody.getFirst().getX()) % DEFAULT_NUM_ROWS,
                    (DEFAULT_NUM_COLS + snakeBody.getFirst().getY()) % DEFAULT_NUM_COLS));
            makeSnake();
        }
    }


    /**
     * this method stores the x and y position of the body of the snake in the snakeBody Linkedlist
     */
    public void makeSnake() {


        //this condition is met until snake reaches its initial maximum length of 5
        if (playCounter < 5) {
            for (int i = 0; i < snakeBody.size(); i++) {
                if (i == 0) {
                    board[snakeBody.get(i).getX()][snakeBody.get(i).getY()] = 'S';
                } else {
                    board[snakeBody.get(i).getX()][snakeBody.get(i).getY()] = 's';
                }
            }
            if (foodTaken) {
                getFood();
                foodTaken = false;

            }
        }


        if (playCounter > 4) {
            for (int i = 1; i < snakeBody.size(); i++) {

                board[snakeBody.get(i).getX()][snakeBody.get(i).getY()] = 's';
            }
            board[snakeBody.getFirst().getX()][snakeBody.getFirst().getY()] = 'S';

            if (!foodTaken) {
                if (snakeBody.getFirst().getX() != snakeBody.getLast().getX() || snakeBody.getFirst().getY() !=
                        snakeBody.getLast().getY()) {
                    board[snakeBody.getLast().getX()][snakeBody.getLast().getY()] = '.';
                }
                snakeBody.removeLast();

            } else {
                getFood();

                if (foodCounter>15){
                    getFood();
                }
                foodTaken = false;
            }

        }
        playCounter++;


    }

    /**
     * this method gets the representation of the board in string data type
     * @return the board as string data type
     */
    public String toString() {


        String boardString = "";
        for (int i = 0; i < DEFAULT_NUM_ROWS; i++) {
            for (int j = 0; j < this.DEFAULT_NUM_COLS; j++) {
                boardString = boardString + board[i][j];
            }
            boardString = boardString + "\n";
        }
        return boardString;
    }

}