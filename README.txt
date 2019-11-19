Snake Game
Author- Sandesh Timilsina

1. How to Play
In the command line, there are following options to run the game:
             java -jar SnakeGame.jar
             java -jar SnakeGame.jar maze-zigzag-small.txt
	     java -jar SnakeGame.jar maze-zigzag-large.txt
             java -jar SnakeGame.jar maze-simple.txt
	     java -jar SnakeGame.jar maze-cross.txt
             
Note: the above txt file is required to be on the same directory as the 'SnakeGame' Jar file is kept.
The game is played by pressing the right, left,up down arrows for giving the corresponding direction to the motion of the snake. If key is kept pressing
the speed of the snake motion will be increased. Game will be running until your snake dies.If you press the start, the snake will start to move in 
upward direction by default. You can pause the snake game while playing by clicking on pause button. There is Reset Button to reset the board to default
mode with no blocks. In this mode you can go from oneside and come from the opposite side.

2. Score/Loss
Snake will die if it hits its body or wall. Game Score and snake body will be increased when snake eats the apple. Be careful with the extra blocks
that will be added as game progress. You will be getting more score at higher rates with the progress of the time.

3.Game Logic
The classes in the game are GameManagerGUi, GameManger and Node. At the start of the game, the board is set up based on the command argument mentioned
above in point 1. Using Math.Random function, food is randomly placed each times. The images for brick, grass and apple is obtained. Firstly, they are
set to the specific pixel based on the different maze board size. But,regular rectangles are obtained for snake body. KeyListener interface is used to get
the information from the keyboards.In the logic part the gameboard is represented by the bunch of different characters and in the GUI part those characters
are replaced by the respective rectangles. Timer is used for the speed of the snake motion.

4. Algorithm
Linkedlist is used for keeping the position of the snake body. Each position in the board is tracked by x and y coordinate. If snake doesnot eat food,
we remove the tail position from linkedlist and add the new head position on the linked list. If snake eats food then the tail is not removed. The tail
is removed first before the head moves. Modulus(%) operator is used to reset the x or y coordinates for the snake head when it hits the blank space at 
the end of the board. Following math is
used for specific direction for the snake head.
		Direction         UP      Down    Left     Right    
		X-position        0	   0	    -1       +1		
		Y-position         -1     +1        0         0

For the detection of collision, the position of the snake head after the above direction added is used to find if the position is blank space '.' or Food
'F'. If the position is filled with any other character in two dimensional array board, the game ends. 


5. Extras
a. The custom background image is changed with very good graphics with grass.
b. Brick image is used for block. When the game is over the dialogue panel is displayed.
c. As the game progesses more score, food and walls are added.
d. High score file is created when played for the first time and gets appended in every next play. High score is displayed on top panel.
e. Modular operator is used to get the snake passing from one edge to other.
f. We can speed up the snake velocity by keep pressing the arrow key. And, the timer is reset to normal once released.
g. There is a reset key method to reset the game to default mode without blocks.


6.
There are some bugs in this game. Main bug is my code has fixed position for snake game. So, maze-stripes overrides the snake position.
If we press the keys quickly then the direction is not detected properly. Sometime when we change the 
direction the snake moves rapidly for less than a second and goes back to normal. Additional feature that could be added in the game are:
     a. Blocks can be added to the reset mode.
     b. Time can be increased once game is played for a while.
     c. Snake body can be replaced with good images.
     d. Different level of game button can be added with corresponding Levels.