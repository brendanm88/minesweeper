/**
 * Name : Brendan Madden
 * PennKey : maddenb
 * Recitation : 206
 * 
 * Execution: N/A (no main method), used as a class/to create Cell objects in the
 * Board.java class.
 * 
 * A program that contains methods to create a new Cell object for use in the
 * Board.java class for the minesweeper game. Contains a constructor to make a new
 * Cell with five attributes listed below, as well as many methods to change or 
 * retrieve the attributes of a calling cell to be used in Board.java. Also contains
 * a method to draw cells based on their covered state and coordinate positions, and 
 * methods to allow for MineSweeper game functionality with placement of mines.
 */
public class Cell {
    private boolean covered;
    private boolean mine;
    private int number;
    private int x;
    private int y;
    
    
    /*
     * Description: constructor for a Cell object that assigns initial values to 
     * each of the fields of each Cell.
     * Input: N/A, takes no inputs for constructor.
     * Output: N/A, no outputs, only creates a new Cel object.
     */
    public Cell() {
        this.covered = true;
        this.mine = false;
        this.number = 0;
        int x = 0;
        int y = 0;
    }
    
    
    /*
     * Description: sets x coordinate field of the calling cell to the input integer.
     * Input: a single integer x, the value to which the x coordinate of the calling
     * cell will be set.
     * Output: N/A, only changes the x coordinate field of the calling cell to the
     * given input value.
     */
    public void xSet(int x) {
        this.x = x;
    }
    
    
    /*
     * Description: sets y coordinate field of the calling cell to the input integer.
     * Input: a single integer y, the value to which the y coordinate of the calling
     * cell will be set.
     * Output: N/A, only changes the y coordinate field of the calling cell to the
     * given input value.
     */
    public void ySet(int y) {
        this.y = y;
    }
    
    
    /*
     * Description: returns the mine attribute of the calling cell, which is either
     * true if a cell contains a mine, or false if it does not.
     * Input: N/A, only returns mine attribute of the calling cell.
     * Output: a boolean that is the mine field of the calling cell, true if calling
     * cell contains a mine and false if not.
     */
    public boolean mineGet() {
        return this.mine;
    }
    
    /*
     * Description: sets the mine attribute of the calling cell, or achieves the 
     * purpose of "placing" a mine in the calling cell. Makes the mine field boolean
     * true to represent this behavior.
     * Input: N/A, only called on cell.
     * Output: N/A, only sets mine field boolean of calling cell to true.
     */
    public void mineSet() {
        this.mine = true;
    }
    
    
    /*
     * Description: returns the integer contained in the number field of the calling
     * cell.
     * Input: N/A, only returns the number field of the calling cell.
     * Output: a single integer, which is the integer included in the number field 
     * of the calling cell.
     */
    public int numberGet() {
        return this.number;
    }
    
    /*
     * Description: changes the number attribute of the calling cell to a given
     * input number.
     * Input: a single integer num that is the number to which the number attribute
     * of the calling cell will be changed.
     * Output: N/A, only changes the number attribute of a cell to the input number.
     */
    public void numberSet(int num) {
        this.number = num;
    }
    
    /*
     * Description: function for adding to the number attribute of a cell, used when
     * bombs are placed on the board, so that cells can accurately display the number
     * of adjacent bombs.
     * Input: N/A, called on cell and has no inputs.
     * Output: N/A, only changes number attribute of a cell by adding 1, nothing
     * returned.
     */
    public void numberAdd() {
        this.number++;
    }
    
    
    /*
     * Description: returns the covered state of the calling cell as a boolean, which
     * is true if the cell is covered and false if it is not.
     * Input: N/A, called on cell and has no inputs.
     * Output: a boolean for the covered state of the calling cell.
     */
    public boolean coveredGet() {
        return this.covered;
    }
    
    /*
     * Description: uncovers calling cell by setting covered attribute to false, 
     * then re-draws cell to display newly uncovered state. 
     * Input: N/A, called on a cell and has no inputs.
     * Output: N/A, only changes covered attribute and then re-draws cell.
     */
    public void uncover() {
        this.covered = false;
        this.draw();
    }
    
    
    /*
     * Description: function that draws calling cells according to their covered or 
     * uncovered state, so that there is a noticeable difference between covered
     * and uncovered cells. Drawing style is similar to actual MineSweeper game.
     * Cells that are uncovered have the number of adjacent bombs drawn on them, and
     * if a bomb is uncovered, a red "B" is drawn.
     * Input: N/A, takes in no inputs, just called on a cell.
     * Output: N/A, only uses PennDraw functions to draw cell, nothing returned.
     */
    public void draw() {
        if (this.coveredGet() == true) {
            // covering of cell drawing
            PennDraw.setPenColor(150, 150, 150);
            PennDraw.filledPolygon(x, y, x + 1, y, x + 1, y + 1);
            PennDraw.setPenColor(PennDraw.WHITE);
            PennDraw.filledPolygon(x, y, x, y + 1, x + 1, y + 1);
            PennDraw.setPenColor(215, 215, 215);
            PennDraw.filledSquare(0.5 + x, 0.5 + y, 0.41);
        }
        else if (this.coveredGet() == false) {
            // background of cell drawing
            PennDraw.setPenColor(215, 215, 215);
            PennDraw.filledSquare(0.5 + x, 0.5 + y, 0.475);
            
            PennDraw.setFontSize(48);
            PennDraw.setPenColor(PennDraw.BLACK);
            
            // cell number drawing
            if (this.numberGet() < 9 && this.numberGet() > 0) {
                PennDraw.text(0.5 + x, 0.39 + y, "" + this.number);
            }
            // bomb drawing
            else if (this.mineGet() == true) {
                PennDraw.setPenColor(PennDraw.RED);
                PennDraw.text(0.5 + x, 0.39 + y, "B");
            }
        }
    }
}
