import java.util.Scanner;

public class Tictactoe
{
	private char[][] gameBoard = new char[3][3];
	
	public static void main(String[] args)
	{
		Tictactoe ttt = new Tictactoe();
		ttt.getInput();
	}
	public void getInput()
	{
		int userRow = 0,userCol = 0; 
		printBoard();
		do
		{
		System.out.print("Please enter coordinate ->");
		Scanner in = new Scanner(System.in);
		userRow = in.nextInt();
		userCol = in.nextInt();
		}
		while(!isValid(userRow, userCol, gameBoard));
		gameBoard[userRow][userCol] = 'O';
		if(checkWin(gameBoard, 'X'))System.out.print("you won");
		playAImove();
		
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
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(isValid(i, j, gameBoard))
				{
					int aiWins = 0;
					
					for(int runs = 0; runs < 1000; runs++)
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
				}
			}
		}
		gameBoard[bestRow][bestCol] = 'X';
		if(checkWin(gameBoard, 'X'))System.out.print("you lost");
		getInput();
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
		if(!movesLeft) return true;
			 
			 return false;
		 }
	}
	public void printBoard()
	{
		for(int i = 0; i < 3; i++)
		{
			System.out.print("|");
			
			for(int j = 0; j < 3; j++)
			{
				if(gameBoard[i][j] == 'X' ||gameBoard[i][j] == 'O')
				System.out.print(gameBoard[i][j] + "|");
				else
				System.out.print(" " + "|");
			}
			System.out.println();
		}

	}
	
	
}
