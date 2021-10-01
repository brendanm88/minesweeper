/**********************************************************************
 * Project: Minesweeper
 **********************************************************************/

Name: Brendan Madden

/**********************************************************************
 *  Instructions for how to run program:
 **********************************************************************/
Open MineSweeper.java class, and have Cell.java and Board.java in the same file
    directory (as well as PennDraw.java). Then, press run in MineSweeper.java, and
    a new window will open that contains the game to be played. The board is 9x9 and
    will have 10 mines hidden after the first click, as specified by the writeup. 
    Click cells with the mouse to reveal them, and the game is won when there are 10
    cells left covered (only mines are left covered). Press the r key on the keyboard
    to reset the game after a loss or a win. No command-line arguments necessary.

/**********************************************************************
 *  Additional features beyond the assignment specifications:                   
 **********************************************************************/
N/A, except that the game has been made to look similar to the MineSweeper
    windows game.


/**********************************************************************
 *  A brief description of each file and its purpose:                                          
 **********************************************************************/
These descriptions are basically the file headers located in each of Board.java,
    Cell.java, and MineSweeper.java; 

Board.java is a program that makes use of the Cell class to create a new instance 
 of the Board object for the playing of the game minesweeper. The program contains 
 methods to construct a board object with 81 cells, place mines in random locations 
 throughout the board upon first click, assign numbers to cells based on mine 
 locations, draw the board, get/set attributes of the board, perform changes to 
 revealed cells when a cell is clicked, reveal cells depending on numbers, draw 
 messages depending on winn/loss states of the game, and finally reset the game to 
 create a new game.
    
Cell.Java is a program that contains methods to create a new Cell object for use in
 Board.java for the minesweeper game. Contains a constructor to make a new
 Cell with five attributes listed below, as well as many methods to change or 
 retrieve the attributes of a calling cell to be used in Board.java. Also contains
 a method to draw cells based on their covered state and coordinate positions, and 
 methods to allow for MineSweeper game functionality with placement of mines.
    
MineSweeper.java is a program that plays the game Minesweeper when run. The program 
 creates a new board object for a new game when first run, then draws and starts 
  the playing loop, in which a mouse click will call functions to interact with 
  cells on the board. When there are only 10 cells left uncovered, the win condition
  is reached and a win message is displayed. If a cell hiding a mine is clicked, the
  program's loss condition is reached and a loss message is displayed. At any point 
  (or if one of the above game over conditions is reached), a player can press 'r' 
  on the keyboard to reset the game and create a new game with a new board without 
  rerunning the program. (Rules can be found online).