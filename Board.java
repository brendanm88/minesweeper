/**
 * Name : Brendan Madden
 * PennKey : maddenb
 * Recitation : 206
 * 
 * Execution: N/A (no main method), used as a class/to create a Board object in the
 * MineSweeper.java game class.
 * 
 * A program that makes use of the Cell class to create a new instance of the Board
 * object for the playing of the game minesweeper. The program contains methods to 
 * construct a board object with 81 cells, place mines in random locations throughout
 * the board upon first click, assign numbers to cells based on mine locaitons, draw
 * the board, get/set attributes of the board, perform changes to revealed cells 
 * when a cell is clicked, reveal cells depending on numbers, draw messages
 * depending on winn/loss states of the game, and finally reset the game to create
 * a new game.
 */
public class Board {
    private Cell[][] cells;
    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;
    private static final int MINES = 10;
    private boolean first;
    private boolean lost;
    private boolean won;
    private int cellsLeft;
    private Cell firstClicked;
    
    
    /*
     * Description: constructor for the Board object, takes in no arguments and 
     * initializes most of the fields for the Board, except for firstClicked, which
     * is assigned/initialized later.
     * Input: N/A, constructor takes in no arguments.
     * Output: N/A, constructor for Board object.
     */
    public Board() {
        this.first = true;
        this.lost = false;
        this.won = false;
        // make new board (array of cell objects)
        this.cells = new Cell[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                cells[i][j] = new Cell();
            }
        }
        // number cells left on board initially
        this.cellsLeft = HEIGHT * WIDTH;
    }
    
    
    /*
     * Description: after first cell is clicked, place() places 10 mines in random
     * locations in the Cell[][] of the board, avoiding the first cell. Then,
     * the function allocates numbers to cells in the Cell[][] based on adjacent
     * mines, and opens up/uncovers the first cell that was clicked (as well as
     * any adjacents if the first clicked is empty). Finally, sets the first 
     * attribute of the Board object to false, so placement only occurs once/game.
     * Input: N/A, called on a game object to place mines and uncover first cell
     * clicked; updates attributes of the game but has no output.
     * Output: N/A, only updates/changes attributes of the game, no explicit output.
     */
    private void place() {
        // place mines in random locations in board's cell array
        int minesPlaced = 0;
        while (minesPlaced < MINES) {
            int i = (int) (Math.random() * 9);
            int j = (int) (Math.random() * 9);
            // don't want to repeat bombs or have first click be bomb
            if (this.cells[i][j].mineGet() || this.cells[i][j] == 
                this.firstClicked) {
                continue;
            }
            else {
                // place mines
                cells[i][j].mineSet();
                cells[i][j].numberSet(9);
                minesPlaced++;
            }
        }
        
        // calculate numbers for cells based on bombs placed
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (this.cells[i][j].mineGet()) {
                    if (i + 1 < cells.length) {
                        this.cells[i + 1][j].numberAdd();
                    }
                    if (i + 1 < cells.length && j + 1 < cells[0].length) {
                        this.cells[i + 1][j + 1].numberAdd();
                    }
                    if (j + 1 < cells[0].length) {
                        this.cells[i][j + 1].numberAdd();
                    }
                    if (i > 0) {
                        this.cells[i - 1][j].numberAdd();
                    }
                    if (i > 0 && j > 0) {
                        this.cells[i - 1][j - 1].numberAdd();
                    }
                    if (j > 0) {
                        this.cells[i][j - 1].numberAdd();
                    }
                    if (i > 0 && j + 1 < cells[0].length) {
                        this.cells[i - 1][j + 1].numberAdd();
                    }
                    if (i + 1 < cells.length && j > 0) {
                        this.cells[i + 1][j - 1].numberAdd();
                    }
                }
            }
        }
        
        // draw only cell that is uncovered after numbers have been calculated
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j] == firstClicked) {
                    emptyAdjacents(cells, i, j);
                }
            }
        }
        
        // no longer first draw/place
        this.first = false;
    }
    
    
    /*
     * Description: draws the entire board (using the Cell[][] field), with x and y
     * coordinates based on i and j indices of cells in the cells array. Calls the
     * draw() function in the Cell class for each cell on the board.
     * Input: N/A, only called on a Board object and draws using PennDraw functions
     * in the Cell class.
     * Output: N/A, only draws cells on the board, no epxlicit output.
     */
    private void draw() {
        PennDraw.clear(150, 150, 150);
        PennDraw.setXscale(0, 9);
        PennDraw.setYscale(0, 9);
        
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].xSet(i);
                cells[i][j].ySet(j);
                
                cells[i][j].draw();
            }
        }
    }
    
    /*
     * Description: returns the number of covered cells left on the calling Board 
     * object by returning the cellsLeft field.
     * Input: N/A, called on a Board object, no inputs.
     * Output: a single integer for the number of cells that are left covered on the
     * Board on which the method is called (returns the cellsLeft attribute of the
     * calling Board object).
     */
    public int getLeft() {
        return this.cellsLeft;
    }
    
    /*
     * Description: subtracts one from the number of covered cells left on the 
     * calling Board by subtracting one from the cellsLeft field of the Board.
     * Input: N/A, called on a Board object, no inputs.
     * Output: N/A, no explicit output, only reduces cellsLeft by one (modifies a 
     * field).
     */
    private void subLeft() {
        this.cellsLeft--;
    }
    
    /*
     * Description: method for when a cell in the Cell[][] of a Board object is 
     * clicked, returns without doing anything if game is lost or won. Uses mouse
     * x and y coordinates to find cell that has been clicked, if first click calls
     * place() on the calling Board. Calls emptyAdjacents using the Cell[][] and the
     * clicked cell coordinates as inputs, then assigns the mine state of the 
     * clicked cell to the output boolean of the function.
     * Input: N/A, only called on a Board object.
     * Output: a single boolean mine, the mine state of the cell being clicked from
     * the Board Cell[][].
     */
    public boolean clickedCell() {
        boolean mine = false;
        // do nothing if a mine was clicked/game is lost
        if (this.lost || this.won) {
            return mine;
        }
        
        double clickX = PennDraw.mouseX();
        double clickY = PennDraw.mouseY();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                // find cell that was clicked
                if (clickX < i + 1 && clickY < j + 1 && clickX > i && clickY > j) {
                    // first click condition for placement
                    if (this.first) {
                        this.firstClicked = this.cells[i][j];
                        this.place();
                    }
                    // behavior for interaction between cells
                    emptyAdjacents(cells, i, j);
                    
                    // used to check if clicked cell is mine in MineSweeper.java
                    mine = cells[i][j].mineGet();
                }
            }
        }
        
        return mine;
    }
    
    
    /*
     * Description: a helper function that has a base case for if the input cell is
     * a mine. If the cell that depends on the input location (i and j) is empty 
     * (number is 0) and it is covered, number of covered cells left is reduced by 
     * 1 before that cell is uncovered, and then the function recurses on each of 
     * the surrounding 8 locations to continue opening empty cells. When recursion 
     * reaches cells with numbers that aren't 0, the cell is uncovered, number of 
     * covered cells left is reduced, and the function returns (base case).
     * Input: a Cell[][] cells, which is a field of the Board object being used, and
     * int i and j, the indices/coordinates of a specific cell in the Board's
     * Cell[][].
     * Output: N/A, no explicit outputs, only changes states of some cells in the
     * Board's Cell[][].
     */
    private void emptyAdjacents(Cell[][] cells, int i, int j) {
        // base case for mine
        if (cells[i][j].mineGet()) {
            return;
        }
        // base case for non-empty square
        if (cells[i][j].numberGet() != 0) {
            // reduce cells left when uncovering number
            if (cells[i][j].coveredGet()) {
                this.subLeft();
            }
            
            cells[i][j].uncover();
            return;
        }
        
        // reduce number of cells left when uncovering empty
        if (cells[i][j].numberGet() == 0 && cells[i][j].coveredGet()) {
            this.subLeft();
        }
        
        // uncover and recurse until reach base case above
        cells[i][j].uncover();
        
            if (i + 1 < cells.length && cells[i + 1][j].coveredGet()) {
                emptyAdjacents(cells, i + 1, j);
            }
            if (i + 1 < cells.length && j + 1 < cells[0].length && 
                cells[i + 1][j + 1].coveredGet()) {
                emptyAdjacents(cells, i + 1, j + 1);
            }
            if (j + 1 < cells[0].length && cells[i][j + 1].coveredGet()) {
                emptyAdjacents(cells, i, j + 1);
            }
            if (i > 0 && cells[i - 1][j].coveredGet()) {
                emptyAdjacents(cells, i - 1, j);
            }
            if (i > 0 && j > 0 && cells[i - 1][j - 1].coveredGet()) {
                emptyAdjacents(cells, i - 1, j - 1);
            }
            if (j > 0 && cells[i][j - 1].coveredGet()) {
                emptyAdjacents(cells, i, j - 1);
            }
            if (i > 0 && j + 1 < cells[0].length && 
                cells[i - 1][j + 1].coveredGet()) {
                emptyAdjacents(cells, i - 1, j + 1);
            }
            if (i + 1 < cells.length && j > 0 && 
                cells[i + 1][j - 1].coveredGet()) {
                emptyAdjacents(cells, i + 1, j - 1);
            }
    }
    
    
    /*
     * Description: method for condition when losing the game. Uncovers all cells 
     * that contain mines on the calling board, and draws a message telling the user
     * they lost the game and to press the 'r' key to reset the game. Also sets the
     * lost attribute of the game to true to change behavior of clickedCell().
     * Input: N/A, only called on a Board object.
     * Output: N/A, only changes attributes of different fields of the calling Board,
     * and draws using PennDraw, with no explicit outputs.
     */
    public void lose() {
        // condition for what happens when losing the game
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (this.cells[i][j].mineGet()) {
                    this.cells[i][j].uncover();
                }
            }
        }
        
        // upper/bottom banner backgrounds
        PennDraw.setPenColor(150, 150, 150);
        PennDraw.filledRectangle(4.5, 8.1, 2.5, .7);
        PennDraw.filledRectangle(4.5, 1.58, 2.4, .4);
        // upper/bottom banner foregrounds
        PennDraw.setPenColor(215, 215, 215);
        PennDraw.filledRectangle(4.5, 8.1, 2.4, .6);
        PennDraw.filledRectangle(4.5, 1.58, 2.35, .35);
        
        // text for losing
        PennDraw.setPenColor(194, 19, 19);
        PennDraw.setFontSize(60);
        PennDraw.text(4.5, 8, "You Lose");
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setFontSize(32);
        PennDraw.text(4.5, 1.5, "Press r to Restart");
        
        // lose condition
        this.lost = true;
    }
    
    
    /*
     * Description: method for condition when winning the game. Leaves 10 mines 
     * covered and draws a message telling the user they won the game and to press 
     * the 'r' key to reset the game. Also sets the won attribute of the game to 
     * true to change behavior of clickedCell().
     * Input: N/A, only called on a Board object.
     * Output: N/A, only changes attributes of different fields of the calling Board,
     * and draws using PennDraw, with no explicit outputs.
     */
    public void win() {
        // condition for what happens when winning the game
        PennDraw.setPenColor(150, 150, 150);
        // upper/bottom banner backgrounds
        PennDraw.filledRectangle(4.5, 8.15, 2.6, .7);
        PennDraw.filledRectangle(4.5, 1.58, 2.75, .4);
        // upper.bottom banner foregrounds
        PennDraw.setPenColor(215, 215, 215);
        PennDraw.filledRectangle(4.5, 8.15, 2.5, .6);
        PennDraw.filledRectangle(4.5, 1.58, 2.7, .35);
        
        // text for winning
        PennDraw.setPenColor(51, 194, 19);
        PennDraw.setFontSize(60);
        PennDraw.text(4.5, 8, "You Win!!");
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setFontSize(32);
        PennDraw.text(4.5, 1.5, "Press r to Play Again");
        
        // win condition
        this.won = true;
    }
    
    
    /*
     * Description: resets three fields of the Board object to their initial values
     * when the game is first created or after the game is reset after reaching a 
     * game over condition. Then (re)calls the Board's draw method to draw the game.
     * Input: N/A, called on a Board object.
     * Output: N/A, no explicit outputs, just changes fields of the Board object and
     * calls draw() to draw the game.
     */
    public void newGame() {
        // reset conditions for start of game
        this.first = true;
        this.lost = false;
        this.won = false;
        this.draw();
    }
}
