
import java.util.Scanner;
import java.awt.Color;
import java.awt.Font;

public class TicTacToe {

	private AI board;
	
	public TicTacToe() {
		board = new AI();
		// set the size of the window
		StdDraw.setCanvasSize(700, 700);
		//StdDraw.setXscale(0, 1.2);		// (0, 1) default
		//StdDraw.setYscale(0, 1);		// (0, 1) default
		StdDraw.enableDoubleBuffering();
	}
	
	public static void main(String[] args) {
		TicTacToe sde = new TicTacToe();
		sde.run();
	}
	
	public void run() {
		
		while (true) {
			board.drawBoard();
			board.getMouse();
			StdDraw.show();
			StdDraw.pause(5);
		}
	}
}

class AI
{
	private char[][] gameBoard = new char[3][3];
	private boolean playerTurn = true;
	boolean isFilled = false;
	boolean gameOver = false;
	public void getInput()
	{
		int userRow = 0,userCol = 0; 
		
		do
		{
		System.out.print("Please enter coordinate ->");
		Scanner in = new Scanner(System.in);
		userRow = in.nextInt();
		userCol = in.nextInt();
		}
		while(!isValid(userRow, userCol, gameBoard));
		gameBoard[userRow][userCol] = 'O';
		// if(checkWin(gameBoard, 'O'))
		playAImove();
		
	}
	
	public void drawBoard()
	{
		StdDraw.setPenColor( new Color(0, 0, 0));
		StdDraw.filledRectangle(0.3666, 0.6, 0.01, 0.36666); 
		StdDraw.filledRectangle(0.6222, 0.6, 0.01, 0.36666); 
		StdDraw.filledRectangle(0.5, 0.7, 0.36666, 0.01); 
		StdDraw.filledRectangle(0.5, 0.4666, 0.3666, 0.01);
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 60));
		double x = 0.25;
		double y = 0.84;
		for(int i = 0; i < 3; i++)
		{
			x= 0.25;
			for(int j = 0; j < 3; j++)
			{
				StdDraw.text(x, y, Character.toString(gameBoard[i][j]));
				x += 0.2433333;
			}
			y-= 0.2433333;
		}
		
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
		StdDraw.text(0.95, 0.05, "New Game");
	}
	
	public void getMouse()
	{
		double xLoc = StdDraw.mouseX();
		double yLoc = StdDraw.mouseY();
		
		if(StdDraw.isMousePressed() && xLoc > 0.9 && yLoc < 0.1)
		{
			gameBoard = new char[3][3];
			isFilled = false;
			gameOver = false;
			playerTurn = true;
			StdDraw.clear();
		}
		if(StdDraw.isMousePressed() && xLoc > 0.13333 && xLoc < 1.0-0.13333 
		&& yLoc > 0.23333 && yLoc < 0.96666 && playerTurn && !gameOver)
		{		
			int row = 2-(int)((yLoc-0.23333)/0.243333);
			int col = (int)((xLoc-0.13333)/0.243333);
			if(row == 3) row = 2;
			if(col == 3) col = 2;
			if(gameBoard[row][col] != 'O' && gameBoard[row][col] != 'X' )
			{
				gameBoard[row][col] = 'O';
				isFilled = true;
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++)
						if(gameBoard[i][j] != 'O' && gameBoard[i][j] != 'X')isFilled = false;
				
				
				playerTurn = false;
				StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 60));
				if(isFilled) 
				{
					gameOver = true;
					StdDraw.text(0.5, 0.1, "Tie!");
				}
				if(checkWin(gameBoard, 'O')) 
				{
					isFilled = true;
					gameOver = true;
					StdDraw.text(0.5, 0.1, "You Won!");
					
				}
				if(!isFilled)playAImove();
			}
			

			
		}
		
		
			
		
		
	}
	
	public int[] getRandMove(char[][] board)
	{
		int randRow = 0;
		int randCol = 0;
		do
		{
			randRow = (int)(Math.random()*3);
			randCol = (int)(Math.random()*3);
		}
		while(!isValid(randRow, randCol, board));
		
		return new int[]{randRow, randCol};
		
	}
	
	public void playAImove()
	{
		int bestWins = 0;
		int bestRow = 0;
		int bestCol = 0;
		
		char[][] tBoard = new char[3][3];
			for(int c = 0; c < 3; c++)
				for(int d = 0; d < 3; d++)
					tBoard[c][d] = gameBoard[c][d];
		boolean plWin = false;
		
		for(int a = 0; a < 3; a++)
		{
			for(int b = 0; b < 3; b++)
			{
				if(isValid(a, b, gameBoard))
				{
					tBoard[a][b] = 'X';
					
					if(checkWin(tBoard, 'X'))
					{
						gameBoard[a][b] = 'X';
						playerTurn = true;
						gameOver = true;
						StdDraw.text(0.5, 0.1, "You Lost!");
						return;
					}
					else
					{
					for(int e = 0; e < 3; e++)
						for(int f = 0; f < 3; f++)
							tBoard[e][f] = gameBoard[e][f];
					}
					
				}
			}
		}
		
		for(int a = 0; a < 3; a++)
		{
			for(int b = 0; b < 3; b++)
			{
				if(isValid(a, b, gameBoard))
				{
					tBoard[a][b] = 'O';
					
					if(checkWin(tBoard, 'O'))
					{
						gameBoard[a][b] = 'X';
						playerTurn = true;
						return;
					}
					else
					{
					for(int e = 0; e < 3; e++)
						for(int f = 0; f < 3; f++)
							tBoard[e][f] = gameBoard[e][f];
					}
					
				}
			}
		}
				
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(isValid(i, j, gameBoard))
				{
					int aiWins = 0;
					
					for(int runs = 0; runs < 100000; runs++)
					{
						boolean testGameOver = false;
						
						char[][] testBoard = new char[3][3];
						for(int a = 0; a < 3; a++)
							for(int b = 0; b < 3; b++)
								testBoard[a][b] = gameBoard[a][b];
						
						int aiRow = i;
						int aiCol = j;
						
						testBoard[aiRow][aiCol] = 'X';
						testGameOver = checkWin(testBoard, 'X');
						if(testGameOver == true) aiWins++;
						
						while(testGameOver == false)
						{
							int[] randMove = getRandMove(testBoard);
							
							int plRow = randMove[0];
							int plCol = randMove[1];
							//System.out.print(plRow + " " + plCol);
							
							testBoard[plRow][plCol] = 'O';
							testGameOver = checkWin(testBoard, 'O');
							if(testGameOver != true)
							{
								randMove = getRandMove(testBoard);
								aiRow = randMove[0];
								aiCol = randMove[1];
								testBoard[aiRow][aiCol] = 'X';
								testGameOver = checkWin(testBoard, 'X');
								if(testGameOver == true)
								{
									aiWins++;		
								}
							}
							
			
						}
						
					}
					if(aiWins > bestWins)
					{
						bestWins = aiWins;
						bestRow = i;
						bestCol = j;
					}
					if(bestWins == 0)
					{
						bestWins = aiWins;
						bestRow = i;
						bestCol = j;
					}
					System.out.println(aiWins);
				}
			}
		}
		System.out.print("\n\n");
		gameBoard[bestRow][bestCol] = 'X';
		playerTurn = true;
	}
	
	public boolean isValid(int row, int col, char[][] board)
	{
		if(row >= 0 && row < 3 && col >=0 && col < 3 &&
		board[row][col]!= 'O' && board[row][col]!= 'X' )return true;
		return false;
	}
	
	
	public boolean checkWin(char[][] board, char player)
	{
		
		
		if(
		(board[0][0] == player && board[0][1] == player && board[0][2] == player) ||
		(board[1][0] == player && board[1][1] == player && board[1][2] == player) ||
		(board[2][0] == player && board[2][1] == player && board[2][2] == player) ||
		(board[0][0] == player && board[1][0] == player && board[2][0] == player) ||
		(board[0][1] == player && board[1][1] == player && board[2][1] == player) ||
		(board[0][2] == player && board[1][2] == player && board[2][2] == player) ||
		(board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
		(board[0][2] == player && board[1][1] == player && board[2][0] == player)
		) {
			
					
			return true;
		} else
		 {
			 boolean movesLeft = false;
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3;j++)
					if(isValid(i, j, board))movesLeft = true;
			if(!movesLeft && !isFilled) return true;
			 
			 return false;
		 }
	}
	
	
	
}
