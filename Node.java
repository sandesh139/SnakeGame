/**
 * Date: 2019-04-19
 * Name: Sandesh Timilsina
 */


/**
 *class Node is used to have the x and y pair as each element of creat snakeBody Linkedlist
 */
public class Node {

    /*initiating a private integer variable x */
    private int x;

    /*initiating a private integer variable y */
    private int y;


    /**constructor
     * when object point is created we assign parameter passed to global variable*/
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * this method returns the x value of the node
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * this method returns the y - value of the node
     * @return
     */
    public int getY() {
        return y;
    }

}