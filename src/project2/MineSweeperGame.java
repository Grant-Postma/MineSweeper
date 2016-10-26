package project2;

import java.util.*;
/***********************************************************************
 * This class handles all the game activities.
 * @author Grant Postma and Jon Watkins 
 * @version 1.0
 **********************************************************************/

public class MineSweeperGame {
	/**Creates a board of type Cell that is two dimensional**/
	private Cell[][] board;

	/**Creates variable status to check the state of the game**/
	private GameStatus status;

	/**Creates int variable to measure length/width of game board**/
	private int boardsize;

	/**Creates a int variable for total number of mines in game**/
	private int totalMines;

	/*******************************************************************
	 * Constructor that initializes the board.
	 * @param size sets size of gameboard ex. 10/10
	 * @param mines number of mines in particular game
	 ******************************************************************/

	public MineSweeperGame(int size, int mines) {
		setBoardsize(size);
		status = GameStatus.NotOverYet;
		board = new Cell[size][size];
		setEmpty();
		totalMines = mines;
		layMines (mines);
	}

	/******************************************************************
	 * Cycles through the rows and columns and sets the game board 
	 * clear with empty cells
	 ******************************************************************/

	private void setEmpty() {
		for (int r = 0; r < boardsize; r++)
			for (int c = 0; c < boardsize; c++)
				board[r][c] = new Cell(false, false);  // totally clear.
	}

	/******************************************************************
	 * Get method that returns the board with how many rows and columns
	 * So panel can make correct display.
	 * 
	 * @param row Length of row of game board
	 * @param col Length of column of game board
	 * @return board The dimensions of the board
	 ******************************************************************/

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/*******************************************************************
	 * Method is called from the MineSweeperPanel class and is invoked
	 * when the user has selected a JButton within the 2-Dim array at
	 * location row, col.
	 * 
	 * @param row Current length of row
	 * @param col Current length of column
	 ******************************************************************/
	public void select(int row, int col) {
		//exposes the original cell
		board[row][col].setExposed(true);
		
		//loses the game if the selected cell is a mine and not flagges
		if (board[row][col].isMine()){// did I lose
			if(!board[row][col].isFlagged()){
			status = GameStatus.Lost;
			for (int r = 0; r < boardsize; r++)
				for (int c = 0; c < boardsize; c++)
					if(board[r][c].isMine())
						board[r][c].setExposed(true);
			}
		}
		else {
			status = GameStatus.Won;    // did I win

		//makes an arraylist of the 0's, row,col,row,col,row,col
		ArrayList<Integer> i = new ArrayList<Integer>();
		i.clear();
		i.add(row);//adds the row
		i.add(col);// adds that col, the size should be 2

		//exposes that selected space
		board[i.get(0)][i.get(1)].setExposed(true);

		//loops when a < size of array, which is 2 +2*(adj 0's)
		for(int a = 0; a < i.size();a+=2){
			int r = i.get(a);
			int c = i.get(a+1);
			if(board[r][c].getAdjMines()==0){ //goes in if 0
				//checks if each adjacent cell is a zero
				//if it is, then it adds it to the arraylist
				if(r-1 > -1 && c-1 > -1){
					if(board[r-1][c-1].getAdjMines()==0 
							&& !board[r-1][c-1].isExposed()){
						i.add(r-1);
						i.add(c-1);
					}
					board[r-1][c-1].setExposed(true);
				}
				
				if(r-1 > -1){
					if(board[r-1][c].getAdjMines()==0 
							&& !board[r-1][c].isExposed()){
						i.add(r-1);
						i.add(c);
					}
					board[r-1][c].setExposed(true);
				}


				if(r-1 > -1 && c+1 < boardsize){
					if(board[r-1][c+1].getAdjMines()==0 
							&& !board[r-1][c+1].isExposed()){
						i.add(r-1);
						i.add(c+1);
					}
					board[r-1][c+1].setExposed(true);
				}

				if(c+1 < boardsize){						
					if(board[r][c+1].getAdjMines()==0
							&& !board[r][c+1].isExposed()){
						i.add(r);
						i.add(c+1);
					}
					board[r][c+1].setExposed(true);
				}

				if(c-1 > -1){
					if(board[r][c-1].getAdjMines()==0 
							&& !board[r][c-1].isExposed()){
						i.add(r);
						i.add(c-1);
					}
					board[r][c-1].setExposed(true);
				}

				if(r+1 < boardsize && c-1 > -1){
					if(board[r+1][c-1].getAdjMines()==0 
							&& !board[r+1][c-1].isExposed()){
						i.add(r+1);
						i.add(c-1);
					}
					board[r+1][c-1].setExposed(true);
				}

				if(r+1 < boardsize){
					if(board[r+1][c].getAdjMines()==0 &&  
							!board[r+1][c].isExposed()){						
						i.add(r+1);
						i.add(c);
					}
					board[r+1][c].setExposed(true);
				}

				if(r+1 < boardsize && c+1 < boardsize){
					if(board[r+1][c+1].getAdjMines()==0  
							&& !board[r+1][c+1].isExposed()){
						i.add(r+1);
						i.add(c+1);
					}
					board[r+1][c+1].setExposed(true);
				}
			}
		}
		
	    // checks if only mines left
		for (int r = 0; r < boardsize; r++) 
			for (int c = 0; c < boardsize; c++)
				if (!board[r][c].isExposed() && !board[r][c].isMine())
					status = GameStatus.NotOverYet;
		}


	}



	/******************************************************************
	 * Method is called from the MineSweeperPanel class and it 
	 * determines if a player has won the game after the select method
	 * was called.
	 * @return status The status of the game win or lose
	 ******************************************************************/

	public GameStatus getGameStatus() {
		return status;
	}

	/******************************************************************
	 * This method is called from the MineSweeperPanel class and it 
	 * resets the board to a new game.
	 ******************************************************************/

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (totalMines);
	}

	/******************************************************************
	 * Lays the mines in random cells on the game board.
	 * Also checks adjacent cells and assigns them a number according
	 * to how many adjacent mines are there. Loops through all rows and
	 * columns.
	 * 
	 * @param mineCount Number of mines
	 ******************************************************************/

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {	   // perhaps the loop will never end
			int c = random.nextInt(boardsize);
			int r = random.nextInt(boardsize);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}	
		}
		//This is a counter for the # of adj mines
		int tempMines = 0; 
		//this is a double for loop which does every row and column
		for(int a = 0; a < boardsize ;a++)
			for(int b = 0; b < boardsize; b++){
				//each one of these if statments checks if the cell --
				//--is one the board and is not a mine
				if(!board[a][b].isMine()){
					if(a-1 > -1 && b-1 > -1 && board[a-1][b-1].isMine())
						tempMines++;
					if(a-1 > -1 && board[a-1][b].isMine())
						tempMines++;
					if(a-1 > -1 && b+1 < boardsize && 
							board[a-1][b+1].isMine())
						tempMines++;
					if(b+1 < boardsize && board[a][b+1].isMine())
						tempMines++;
					if(b-1 > -1 && board[a][b-1].isMine())
						tempMines++;
					if(a+1 < boardsize && b-1 > -1 && 
							board[a+1][b-1].isMine())
						tempMines++;
					if(a+1 < boardsize && board[a+1][b].isMine())
						tempMines++;
					if(a+1 < boardsize && b+1 < boardsize && 
							board[a+1][b+1].isMine())
						tempMines++;
				}
				board[a][b].setAdjMines(tempMines);
				tempMines = 0;
			}

	}

	/******************************************************************
	 * Get method for boardsize dimensions
	 * @return boardsize The size of the board as the user was prompted
	 * to enter.
	 ******************************************************************/

	public int getBoardsize() {
		return boardsize;
	}

	/******************************************************************
	 * Set method for boardsize dimensions
	 * @param boardsize Sets the boardsize to user input
	 ******************************************************************/

	public void setBoardsize(int boardsize) {
		this.boardsize = boardsize;
	}

	/******************************************************************
	 * Get method for totalMines
	 * @return totalMines The total number of mines in the current game
	 ******************************************************************/

	public int getTotalMines() {
		return totalMines;
	}

	/******************************************************************
	 * Set method for totalMines
	 * @param totalMines Sets the totalMines to user input as prompted
	 ******************************************************************/

	public void setTotalMines(int totalMines) {
		this.totalMines = totalMines;
	}
}