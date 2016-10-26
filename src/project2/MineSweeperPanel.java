package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

/**********************************************************************
 * The GUI class that portrays the game as played by the user.
 * 
 * @author Grant Postma and Jonathan Watkins
 * @version 1.0
 **********************************************************************/

public class MineSweeperPanel extends JPanel{

	/**Creates the JButton board that is two dimensional**/
	private JButton[][] board;

	/**Creates the JButton quitButton**/
	private JButton quitButton;

	/**Creates the JButton quitButton**/
	private JButton gameResetButton;

	/****/
	private JPanel bottom = new JPanel();

	/****/
	private JPanel center = new JPanel();

	/****/
	private JPanel top = new JPanel();


	/**Creates the JLabel for wins and loses**/
	private JLabel winsLabel;
	private int winsCounter = 0;
	private JLabel loseLabel;
	private int loseCounter = 0;

	/**Creates a cell variable that can be contain a mine**/
	private Cell iCell;

	//**creates a varible for the size of the board
	private int gameSize;

	public int getGameSize() {
		return gameSize;
	}

	/****/
	private MineSweeperGame game;  // model

	private ImageIcon flagImage;
	private ImageIcon mineImage;

	/****/
	private String sizeInput;

	private int size;


	/****/
	private int mines;

	/****/
	private String minesInput;

	// create game, listeners
	private	ButtonListener listener = new ButtonListener();
	private FlagListener mouse = new FlagListener();

	/******************************************************************
	 * Constructor
	 ******************************************************************/
	public MineSweeperPanel() {


		setLayout (new BorderLayout(3,1));

		bottom.setLayout(new GridLayout(1,2));
		top.setLayout(new GridLayout(1,3));



		flagImage =  new ImageIcon("mineSweeperFlag.png");
		mineImage = new ImageIcon("mineSweeperMine.jpg");




		//prompt user for entering the number of mines
		//declares new number of mines
		resetBoardSize();
		//prompt user for entering the number of mines
		//declares new number of mines
		resetMines();

		displayCenterPanel();

		//calls the displayboard() method
		displayBoard();



		// add all to contentPane



		winsLabel = new JLabel("Wins:"+ winsCounter);
		bottom.add(winsLabel, BorderLayout.WEST);

		loseLabel = new JLabel("Loses:"+ loseCounter);
		bottom.add(loseLabel,BorderLayout.EAST);

		//Adds visible quit button to JFrame
		quitButton = new JButton("Quit");
		top.add(quitButton,BorderLayout.WEST);
		quitButton.addActionListener(listener);
		quitButton.setVisible(true);

		top.add(new JLabel("Mine Sweeper"),BorderLayout.CENTER);

		//add reset tiles button
		gameResetButton = new JButton("Reset Game");
		top.add(gameResetButton,BorderLayout.EAST);
		gameResetButton.addActionListener(listener);
		gameResetButton.setVisible(true);

		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		add(top,BorderLayout.NORTH);
	}

	/******************************************************************
	 * Method for displaying the individual cells in the JFrame
	 ******************************************************************/
	private void displayBoard() {

		for (int r = 0; r < game.getBoardsize(); r++)
			for (int c = 0; c < game.getBoardsize(); c++) {
				iCell = game.getCell(r, c);

				if(iCell.isFlagged()){
					board[r][c].setIcon(flagImage); 
					iCell.setExposed(false);

				}
				else{
					board[r][c].setIcon(null);
					board[r][c].setText(null);
				}

				if (iCell.isMine()&& iCell.isExposed()){
					board[r][c].setIcon(mineImage);
				}
				// readable, ifs are verbose





				if (iCell.isExposed()  && !iCell.isFlagged()){
					board[r][c].setEnabled(false);
					if (!iCell.isMine()){
						if(iCell.getAdjMines()!=0 /*&& iCell.isExposed()*/){
							board[r][c].setText(""+
									game.getCell(r,c).getAdjMines());
						}
					}
					else
						board[r][c].setText(" ");
				}
				else
					board[r][c].setEnabled(true);


			}

	}

	/******************************************************************
	 * This class impliments the button listener
	 *
	 ******************************************************************/

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JComponent buttonPressed = (JComponent) e.getSource();

			for (int r = 0; r < game.getBoardsize() ; r++)
				for (int c = 0; c < game.getBoardsize(); c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

			displayBoard();

			//If game is lost then prompt user of outcome and reset game
			if (game.getGameStatus() == GameStatus.Lost) {

				displayBoard();
				JOptionPane.showMessageDialog(null, 
						"You Lose \n The game will reset");
				//exposeMines = false;
				game.reset();
				loseCounter++;
				loseLabel.setText("Loses:"+ loseCounter);
				displayBoard();

			}

			//if game is won then prompt user of outcome and reset game
			if (game.getGameStatus() == GameStatus.Won) {
				JOptionPane.showMessageDialog(null, 
						"You Win: all mines have been found!\n "
								+ "The game will reset");
				game.reset();
				winsCounter++;
				winsLabel.setText("Wins:"+ winsCounter);
				displayBoard();
			}

			//if the quit button is pressed display a yes no JOptionPane
			if(buttonPressed == quitButton){
				Object[] options = {"Yes", "No"};

				int answer = JOptionPane.showOptionDialog(null,
						"Would you like to quit? ","Quit:Continue", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE,
						null, options,options[1]);
				if(answer == JOptionPane.YES_OPTION)
				{
					System.exit(0); 
				}


			}

			if(buttonPressed == gameResetButton){
				center.removeAll();

				resetBoardSize();
				resetMines();
				displayCenterPanel();

				


				center.revalidate();
				center.repaint();
				displayBoard();
			}


		}
	}

	public void resetBoardSize(){
		size = 0;
		
		//prompt user for entering the number of mines
		//declares new number of mines
		while(size > 30 || size < 3){
			sizeInput = JOptionPane.showInputDialog(
					" Enter dimension of game board (Must be between 3-30): ");
			try  
			{  
				double d = Double.parseDouble(sizeInput); 
				size = Integer.parseInt(sizeInput);
			}  
			catch(NumberFormatException nfe)  
			{  
				JOptionPane.showMessageDialog(null,"Thats not a number..." +
						"Defaulting size to 10");
				size = 10;
			}  

		}
	}

	public void resetMines(){
		mines = 0;
		//prompt user for entering the number of mines
		//declares new number of mines
		while(mines < 1 || mines > (size*size)-1){
			minesInput = JOptionPane.showInputDialog(
					"Enter the numebr of mines you would like: ");
			try  
			{  
				double d = Double.parseDouble(minesInput); 
				mines = Integer.parseInt(minesInput);
			}  
			catch(NumberFormatException nfe)  
			{  
				JOptionPane.showMessageDialog(null,"Thats not a number..." +
						"Defaulting mines to 5");
				mines = 5;
			} 
		}

	}
	
	public void displayCenterPanel(){
		game = new MineSweeperGame(size,mines);

		// creates the board
		center.setLayout(new GridLayout(size, size));
		board = new JButton[size][size];

		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++) {
				board[row][col] = new JButton("");
				board[row][col].addActionListener(listener);
				board[row][col].addMouseListener(mouse);
				center.add(board[row][col]);
			}
		
	}


	public class FlagListener implements MouseListener {

		public void mouseReleased(MouseEvent e){
		}
		public void mousePressed(MouseEvent e){

			for(int r=0; r < game.getBoardsize(); r++){
				for(int c = 0; c < game.getBoardsize(); c++){
					if(board[r][c] == e.getSource()){
						iCell = game.getCell(r, c);
						if(e.getButton() == MouseEvent.BUTTON3 
								&& !iCell.isFlagged() && !iCell.isExposed()){
							game.getCell(r, c).setFlagged(true);
						}
						else if(e.getButton() == MouseEvent.BUTTON3 
								&& iCell.isFlagged() && !iCell.isExposed()){
							game.getCell(r, c).setFlagged(false);
						}
					}
				}
			}
			displayBoard();
		}

		public void mouseClicked(MouseEvent e){
		}
		public void mouseExited(MouseEvent e){
		}
		public void mouseEntered(MouseEvent e){
		}

	}
}

