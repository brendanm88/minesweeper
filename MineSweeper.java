/**
 * Name : Brendan Madden
 * PennKey : maddenb
 * Recitation : 206
 * 
 * Execution: java MineSweeper
 * 
 * A program that plays the game Minesweeper when run. The program creates a new
 * board object for a new game when first run, then draws and starts the playing
 * loop, in which a mouse click will call functions to interact with cells on the
 * board. When there are only 10 cells left uncovered, the win condition is reached 
 * and a win message is displayed. If a cell hiding a mine is clicked, the program's
 * loss condition is reached and a loss message is displayed. At any point (or if
 * one of the above game over conditions is reached), a player can press 'r' on the 
 * keyboard to reset the game and create a new game with a new board without 
 * rerunning the program. (Rules can be found online).
 */
public class MineSweeper {
    
    public static void main(String[] args) {
        // initial state before running game
        Board game = new Board();
        boolean running = true;
        boolean playing = false;
        
        // game loop
        while (running) {
            // first draw
            if (!playing) {
                game = new Board();
                game.newGame();
                playing = true;
            }
            
            // running game after first draw
            while (playing) {
                if (PennDraw.mousePressed()) {
                    // uncover clicked cell
                    game.clickedCell();
                    // win condition
                    if (game.getLeft() == 10) {
                        game.win();
                    }
                    // lose condition, if mine clicked then lose game
                    if (game.clickedCell()) {
                        game.lose();
                    }
                }
                // reset condition
                if (PennDraw.hasNextKeyTyped()) {
                    char input = PennDraw.nextKeyTyped(); 
                    if (input == 'r') {
                        playing = false;
                    }
                }
            }
        }

        /* test array setup/randomizing
         for (int i = 0; i < game.cells.length; i++) {
         System.out.println();
         for (int j = 0; j < game.cells[0].length; j++) {
         System.out.print(game.cells[i][j].mineGet());
         }
         }
         */
    }
}
