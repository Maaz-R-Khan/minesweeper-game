import org.w3c.dom.ls.LSOutput;

public class Minesweeper {

    // Data members
    private char[][] board;   // The game board where cells will be displayed
    private boolean[][] mines; // Array to track the locations of mines
    private boolean[][] revealed; // Array to track which cells have been revealed
    private int rows; // Number of rows in the board
    private int cols; // Number of columns in the board
    private int numMines; // Number of mines in the game
    private boolean gameOver; // Boolean to check if the game is over
    private boolean[][] flagged;

    // Constructor to initialize the board with the specified dimensions and number of mines
    public Minesweeper(int rows, int cols, int numMines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        this.board = new char[rows][cols];
        this.mines = new boolean[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.gameOver = false;

        initializeBoard();
        placeMines();
        calculateNumbers();


        // Call methods to initialize the board and place mines
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    public void setGameOver(boolean status)
    {
        this.gameOver = status;
    }

    // Method to initialize the game board with empty values
  private void initializeBoard() {
      //  char gameboard [][] = new char[getRows()][getCols()]; //initialize the 10 x 10 gameboard through a 2d array
        for(int i = 0; i < getRows(); i++) { //loops through the rows in the board
            for(int j = 0; j < getCols(); j++) { //loops through the columns on the board
             board[i][j] = '*'; //
             setBoard(board);
                System.out.print(getBoard(i,j));
            }
            System.out.println();
        }
    }




    // Method to randomly place mines on the board
    public void placeMines() {
        // TODO: Implement this method
        int placedMines = 0; // tracks the current number of placed mines.

        //numMines tracks the total number of mines that have to be placed.
       while(placedMines < numMines ) {
           //Randomly assigns a column and row
           int randomCol = (int) (Math.random() * getCols()) ;
           int randomRow = (int) (Math.random() * getRows() ) ;

           //check if there is a mine already at the location.
           if(!(mines[randomRow][randomCol])) {
               //false means that there is no mine at the cell
               //true means that there is a mine at the cell
              mines[randomRow][randomCol] = true; //places a mine if there is no mine at the selected random row and random column.
              placedMines++; //increment placedMines by 1 if a mine is placed.
           }
         }
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                System.out.print(getMines(i,j) + " ");
            }
            System.out.println();
        }
      }





    // Method to calculate numbers on the board for non-mine cells
    private void calculateNumbers() {
        // TODO: Implement this method
        //loops through every cell on the board
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {

                //if there is a mine at the current cell then skip it
                if(mines[row][col]) {
                    continue;
                }

                //Counts the number of adjacent mines.
                int mineCount = 0;

                //this loops through the 8 adjacent cells to the current cell.
               for(int i = -1; i <= 1; i++ ) {
                   for(int j = -1; j <= 1; j++ ) {
                       //skip checking the current cell meaning continue the loop.
                       if(i == 0 && j == 0) {
                           continue;
                       }

                       //calculates the adjacent cell's row and column
                       int adjRow = row + i;
                       int adjCol = col + j;

                     //this checks to see if the adjacent cells are within bounds of the board
                      if(adjRow >= 0 && adjRow < rows && adjCol >= 0 && adjCol < cols) {
                          //if there is a mine at the adjacent cell, add one to the mine count
                          if (mines[adjRow][adjCol]) {
                              mineCount++;
                          }
                      }
                   }
               }
               //mine count must be stored as a char in the board because the board is represented by a char array.
               board[row][col] = (char) (mineCount + '0');
            }
        }
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                // Print each cell followed by a space
                System.out.print(board[i][j] + " ");
            }
            // Move to the next line after printing each row
            System.out.println();
        }
    }


    // Method to display the current state of the board
    public void displayBoard() {
        // TODO: Implement this method

/*
        //loop through all the cells on the board
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
               if(revealed[row][col])  {
                   System.out.println("M ");
               }
             else {
                 System.out.println(board[row][col] + " ");
             }
            if(flagged[row][col]) {
                 System.out.println("F");
                }
             else{
                 System.out.println("*");
                }
            }
            System.out.println();
        }
 */
        // Loop through all the cells on the board
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (revealed[row][col]) {
                    // Display the number of adjacent mines or a space if none
                    System.out.print(board[row][col] + " ");
                } else {
                    // Display "*" for unrevealed cells
                    System.out.print("* ");
                }
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }


    // Method to handle a player's move (reveal a cell or place a flag)
    public void playerMove(int row, int col, String action) {
        // TODO: Implement this method
        if (action.equals("reveal")) {
            //if the cell is not revealed
            if (!revealed[row][col]) {
                //reveal the cell
                revealCell(row, col);
                displayBoard();
             //   if the selected cell to be revealed is a mine, then it is game over.
                if (mines[row][col]) {
                    gameOver = true;
                    System.out.println("Game Over! You hit a mine.");
                }
            }

            //if a user wants to flag, then flag the selected cell.
        } else if (action.equals("flag")) {
            flagCell(row, col);
            displayBoard();
            //if a user wants to unflag a cell, then unflag the selected cell.
        } else if (action.equals("unflag")) {
            unflagCell(row, col);
            displayBoard();
        }
    }


    // Method to check if the player has won the game
    public boolean checkWin() {
        // TODO: Implement this method
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(revealed[row][col] && !mines[row][col]) {
                    System.out.println("Congrats, you have won the game!!!");
                    gameOver = true;
                    return true;
                }
            }
        }
        return false;
    }

    // Method to check if the player has lost the game
    public boolean checkLoss(int row, int col) {
        // TODO: Implement this method
        return false;
    }

    // Method to reveal a cell (and adjacent cells if necessary)
    private void revealCell(int row, int col) {
        // TODO: Implement this method

        // Prevent revealing a cell that is already revealed or out of bounds
        if (row < 0 || row >= rows || col < 0 || col >= cols || revealed[row][col]) {
            return;
        }

        //if the reveal selected cell is a mine, then
        if(mines[row][col]) {
            System.out.println("You hit a mine. Game Over!");
            gameOver = true;
        }

        //setting revealed cells to true means that a cell is revealed successfully.
        //if selected cell to be revealed is not a mine, then
        if(revealed[row][col] && !mines[row][col]) {
            revealed[row][col] = true;
            displayBoard();
        }
    }

    // Method to flag a cell as containing a mine
    private void flagCell(int row, int col) {
        // TODO: Implement this method
        if (!revealed[row][col]) {
            flagged[row][col] = !flagged[row][col];
            if (flagged[row][col]) {
                System.out.println("Flagged cell at (" + row + ", " + col + ")");
            } else {
                System.out.println("Unflagged cell at (" + row + ", " + col + ")");
            }
        } else {
            System.out.println("Cannot flag a revealed cell.");
        }
    }

    // Method to unflag a cell
    private void unflagCell(int row, int col) {
        // TODO: Implement this method
        if (flagged[row][col]) {
            flagged[row][col] = false; // Set the flagged state to false
            System.out.println("Unflagged cell at (" + row + ", " + col + ")");
        } else {
            System.out.println("Cell at (" + row + ", " + col + ") is not flagged.");
        }
    }


    // setter method for setBoard
    public void setBoard(char[][] board) {
        this.board = board;
    }

    // getter method for getBoard
    public char getBoard(int rows, int cols) {
        return board[rows][cols];
    }

    //getter method for rows.
    public int getRows() {
      return rows;
    }

    //getter method for columns.
    public int getCols() {
       return cols;
    }

    //getter method to get the number of Num Mines.
    public int getNumMines() {
        return numMines;
    }

    public void getNumMines(int rows, int cols, boolean result) {
        mines[rows][cols] = result;
    }

/*
    public void setMines(int rows, int cols, boolean result) {
        mines[rows][cols] = result;
    }

 */

    public boolean getMines(int rows, int cols ) {
        return mines[rows][cols];
    }

    }


