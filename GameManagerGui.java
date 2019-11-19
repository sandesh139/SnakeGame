/**
 * This class makes a object of the GameManager class and creats a gui for the snake game.
 * Author: Sandesh Timilsina
 * date: 2019-05-05
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;

/**
 * This class contains a main method for the program
 * This class extends JFrame and implements the KeyListener interface.
 */
public class GameManagerGui extends JFrame implements KeyListener {


    private GameManager gameManager;
    private Timer timer;
    String currentDirection;
    private BufferedImage grass;
    private BufferedImage food;
    private BufferedImage brick;
    private String txtFile;
    private BufferedReader br;
    private static ArrayList<Integer> highScore = new ArrayList<>();
    private PrintWriter pw;
    private int gameScore;
    boolean gameStatus = true;


    /**
     * constructor of the GameManagerGui
     * it reads the maze file if provided any as a first argument and gets board size and blocks
     * if argument is not provided creates a board of size 20 by 20.
     *
     * @param args
     * @throws IOException
     */
    public GameManagerGui(String[] args) throws IOException {

        writeHighScore();
        currentDirection = "up";       //default direction in the beginning of the game


        if (args.length == 1) {
            /* initiation the fr as a FileReader Object referencing to first argument */
            FileReader fr = new FileReader(args[0]);

            /*initiating the br as a BufferedReader Object referencing to second argument*/
            BufferedReader br = new BufferedReader(fr);

            /*intiating lineslist as an arraylist to store each lines of the file as its string elements */
            List<String> linesList = new ArrayList<>();

            /*initiating line as a String variable */
            String line;

            /* readLine method keeps reading file until we finish the last line of the file */
            while (((line = br.readLine()) != null)) {
                linesList.add(line);
            }
            br.close();                     //closing the file
            int length = linesList.size();  //getting the size of the length

            //given stringNumbers array stores each separated numbers as strings
            String[] stringNumbers = linesList.get(0).split(" ");


            //comverting string to integers
            int height = Integer.parseInt(stringNumbers[0]);
            int width = Integer.parseInt(stringNumbers[1]);

            //initializing the gameManager Object
            gameManager = new GameManager(width, height);
            gameManager.getBoard();


            for (int i = 1; i < length; i++) {
                if (!linesList.get(i).isEmpty()) {
                    stringNumbers = linesList.get(i).split(" ");
                    int xUpperLeftPosition = Integer.parseInt(stringNumbers[0]);
                    int yUpperLeftPosition = Integer.parseInt(stringNumbers[1]);
                    int xLowerRightPosition = Integer.parseInt(stringNumbers[2]);
                    int yLowerRightPosition = Integer.parseInt(stringNumbers[3]);

                    if (args[0].equals("maze-cross.txt") || args[0].equals("maze-stripes.txt")) {
                        gameManager.getBlocksCross(xUpperLeftPosition, yUpperLeftPosition, xLowerRightPosition, yLowerRightPosition);
                    } else {
                        gameManager.getBlocks(xUpperLeftPosition, yUpperLeftPosition, xLowerRightPosition, yLowerRightPosition);
                    }
                    if (!(args[0].equals("maze-simple.txt"))) {
                        if (gameManager.foodCounter > 8) {
                            gameManager.getMoreBlocks();
                            if (gameManager.foodCounter > 8) {
                                gameManager.getMoreBlocks();
                                gameManager.getMoreBlocks();
                                gameManager.getMoreBlocks();
                                gameManager.getMoreBlocks();
                            }
                        }
                    }

                }
            }
        } else {
            gameManager = new GameManager(20, 20);
            gameManager.getBoard();
        }
        gameManager.getFood();

        //intitiating the mainpanel and gamePanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        GamePanel gamePanel = new GamePanel(gameManager);

        //setting the size of the panel
        gamePanel.setPreferredSize(new Dimension(gameManager.getWidth() * 30, gameManager.getHeight() * 30));



        //adding the panel and buttons to the GameManagerGui
        add(gamePanel, BorderLayout.CENTER);
        JPanel topPanel = new JPanel(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        JPanel bottomPannel = new JPanel(new BorderLayout());
        add(bottomPannel, BorderLayout.SOUTH);


        //creating start and restart button and adding to the bottom panel.
        JButton startBotton = new JButton("Start");
        bottomPannel.add(startBotton, BorderLayout.CENTER);
        JButton restart = new JButton("Restart to default");
        bottomPannel.add(restart,BorderLayout.LINE_START);


        //creating scoreLabel and highScoreLabel to the topPanel
        JLabel scoreLabel = new JLabel("Score : " + 0);
        topPanel.add(scoreLabel, BorderLayout.LINE_START);
        JLabel highScoreLabel = new JLabel("    High Score: "+ getHighestScore());



        //adding actionlistener to restart button to default mode
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStatus = true;
                gameManager.snakeBody.clear();
                gameManager.snakeBody.addFirst(new Node(4 * gameManager.DEFAULT_NUM_ROWS / 5, gameManager.DEFAULT_NUM_COLS / 2 + 1));
                gameManager.getBoard();
                gameManager.getFood();
                gameScore = 0;
                gameManager.foodCounter=0;

                try {
                    writeHighScore();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                gameManager.playCounter=1;
                gameManager.gameEnds= "";
                startBotton.setText("Start");
                try {
                    highScoreLabel.setText("      High Score: "+ getHighestScore());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                repaint();
                timer.stop();





            }


        });


        topPanel.add(highScoreLabel, BorderLayout.CENTER);


        //creating anonymous timer to keep game running at 300 milli second per move.
        this.timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.getDirection(currentDirection);
                gameScore = (int) (gameManager.getScore());
                scoreLabel.setText("Score : " + gameScore);

                try {
                    highScoreLabel.setText("      High Score: "+ getHighestScore());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if(gameStatus) {
                    if (gameManager.gameEnds.equals("end")) {
                        JOptionPane.showMessageDialog(null, "Game has Ended", "Game Ends",
                                JOptionPane.INFORMATION_MESSAGE);

                        gameStatus = false;

                        try {
                            writeHighScore();
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                repaint();      //this re-draw the board.
            }


        });



        //creating start button
        startBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                    startBotton.setText("Start");
                } else {
                    timer.start();
                    startBotton.setText("pause");
                }
                GameManagerGui.this.requestFocus();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //setting the title of the board as Snake Game.
        setTitle("Snake Game");


        this.addKeyListener(this);


    }


    /**
     * this method reads the textFile.txt and adds the string number to highscore collection.
     * @return
     * @throws IOException
     */
    public int getHighestScore() throws IOException {

        String line;
        FileReader fr = new FileReader("textFile.txt");
        BufferedReader br = new BufferedReader(fr);
        while (((line = br.readLine()) != null)){
            highScore.add(Integer.parseInt(line));
        }
        br.close();

        highScore.add(0);
        Collections.sort(highScore);

        return highScore.get(highScore.size()-1);
    }


    /**
     * this method appends highscore to the textFile.txt
     * @throws FileNotFoundException
     */
    public void writeHighScore() throws FileNotFoundException {

        try
        {
            String filename= "textFile.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(""+gameScore+"\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }



    /**
     *this method sizes the frame so that all its contents are at or above their preferred sizes
     * @param gui
     */
    public static void createGUI(GameManagerGui gui) {
        gui.pack();
        gui.setVisible(true);

    }


    /**
     * keypadTyped method from keyListener interface
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }


    /**
     * keyPressed method from KeyListener interface
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                currentDirection = "right";

                break;
            case KeyEvent.VK_LEFT:
                currentDirection = "left";
                break;
            case KeyEvent.VK_DOWN:
                currentDirection = "down";

                break;

            case KeyEvent.VK_UP:
                currentDirection = "up";

                break;
        }
        timer.setDelay(100);

    }

    /**
     * keyReleased method from keyListener interface
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        timer.setDelay(300);
    }


    //Create class for GamePanel, where you draw board, snake,food,walls

    /**
     * this class is for GamePanel.
     *
     */
    public class GamePanel extends JPanel {
        private GameManager m;

        //constructor which takes GameManager object as a parameter
        public GamePanel(GameManager manager) {
            this.m = manager;



        }


        /**
         *this method is invoked when picture need to be painted.
         * @param g
         */
        public void paintComponent(Graphics g) {

            int width = m.getWidth();
            int height = m.getHeight();
            int heightTrack =0 ;
            int widthTrack =0;
            int pixels = 0;


            char[] board = m.toString().toCharArray();
            if(getWidth()/width>getHeight()/height) {
                pixels = getHeight()/height;

            }else {
                pixels = getWidth()/width;             //getting the size of block in term of pixel
            }


            URL resource1 = getClass().getResource("/res/grass.jpg");
            try {
                grass = ImageIO.read(resource1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            URL resource2 = getClass().getResource("/res/apple.jpg");
            try {
                food = ImageIO.read(resource2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            URL resource3 = getClass().getResource("/res/brick.png");
            try {
                brick = ImageIO.read(resource3);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(gameManager.foodTaken){

            }

            for (int i=0; i < board.length; i++) {
                char block = board[i];

                switch (block) {
                    case '.':
                        g.setColor(Color.BLACK);
                        //g.fillRect(widthTrack*pixels, heightTrack*pixels, pixels, pixels);
                        g.drawImage(grass,widthTrack*pixels, heightTrack*pixels,pixels,pixels,this);
                        break;
                    case 'X':
                        g.setColor((Color.RED));
                        g.drawImage(brick,widthTrack*pixels, heightTrack*pixels,pixels,pixels,this);
                        //g.fillRect(widthTrack*pixels, heightTrack*pixels, pixels, pixels);
                        break;

                    case 'S':
                        g.setColor(Color.PINK);
                        g.fillRect(widthTrack*pixels, heightTrack*pixels, pixels, pixels);
                        break;

                    case 's':
                        g.setColor(Color.BLACK);
                        g.fillRect(widthTrack*pixels, heightTrack*pixels, pixels, pixels);
                        break;

                    case 'F':
                        g.setColor(Color.BLUE);
                        //g.fillRect(widthTrack*pixels, heightTrack*pixels, pixels, pixels);
                        g.drawImage(food,widthTrack*pixels, heightTrack*pixels,pixels,pixels,this);
                        break;

                    case '\n':
                        widthTrack = -1;
                        heightTrack++;
                        break;
                }
                widthTrack++;

            }
        }
    }


    /**
     * main method which uses threading with swing since any user
     * interface updates happens on the event dispatch thread
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createGUI(new GameManagerGui(args));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
