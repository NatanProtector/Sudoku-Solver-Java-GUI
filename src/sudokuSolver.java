public class sudokuSolver {
	final boolean row = true;
	final boolean column = false;
	int[][] board;
	/*
	 * default constructor
	 */
	public sudokuSolver() {
		board = new int[9][9];
	}
	
	/////////////////////
	// getters setters //
	/////////////////////
	
	public void setBoard(int[][] b) {
		this.board = b;
	}
	public int[][] getBoard() {
		return this.board;
	}
	public String getString() {
		int[][] thisBoard = getBoard();
		String thisRow = "";
		for (int j=0; j<thisBoard.length ; j++) {
			for (int i=0; i<thisBoard.length ; i++) {
				thisRow += thisBoard[j][i]+" ";
			}
			thisRow += "\n";
		}
		return thisRow;
	}
	
	///////////////////////////////////
	// check for legal board methods //
	///////////////////////////////////
	
	private int[][] generateSubBoard(int[][] board, int i) {
		int[][] coordinateList = {
				{0,0},{0,3},{0,6},
				{3,0},{3,3},{3,6},
				{6,0},{6,3},{6,6}
		};
		return generateSubBoard(board,coordinateList[i-1]);
	}
	private int[][] generateSubBoard(int[][] board, int[] cor) {
		int[][] subBoard = new int[3][3];
		int[][] currentBoard = board;
		for (int j = 0; j<3 ; j++) {
			for (int i = 0; i<3 ; i++) {
			subBoard[j][i] = currentBoard[cor[0]+j][cor[1]+i];
			}
		}
		return subBoard;
	}
	private boolean legalRow(int[][] board, int n) {
		return legalRowOrColumn(board, n, row);
	}
	private boolean legalColumn(int[][] board, int n) {
		return legalRowOrColumn(board, n, column);
	}
	/**
	 * input true for row
	 * input false for column
	 */
	private boolean legalRowOrColumn(int[][] board, int n, boolean isRow) {
		int[] numList = {1,2,3,4,5,6,7,8,9};
		for (int i = 0; i<9 ; i++) {
			int thisNum = isRow? board[n-1][i]: board[i][n-1];
			if (thisNum == 0)
				continue;
			if (numList[thisNum-1] == 0)
				return false;
			numList[thisNum-1] = 0;
		}
		return true;
	}
	private boolean legalSubBoard(int[][] board, int n) {
		int[][] subBoard = generateSubBoard(board,n);
		int[] numList = {1,2,3,4,5,6,7,8,9};
		for (int j = 0; j < subBoard.length; j++) {
			for (int i = 0; i < subBoard.length; i++) {
				int thisNum = subBoard[j][i];
				if (thisNum != 0) {
					if (numList[thisNum-1] == 0 ) 
						return false;
					numList[thisNum-1] = 0;
				}
			}
		}
		return true;
	}
	private boolean checkIfLegalBoard(int[][] board) {
		for (int i = 0 ; i<board.length ; i++) {
			if (!legalSubBoard(board,i+1))
				return false;
			if (!legalRow(board,i+1))
				return false;
			if (!legalColumn(board,i+1))
				return false;
		}
		return true;
	}
	
	/////////////////////
	// Solving methods //
	/////////////////////
	
	public boolean solve(){
		boolean solvable = true;
		if (solve(getBoard(), 0 , 0)) {
			System.out.println("\nsolved board:\n"+getString());
		}
		else {
			System.out.println("Error in solving");
			solvable = false;
		}
		return solvable;
			
	}
	private boolean solve(int[][] board, int i, int j) {
		if (!checkIfLegalBoard(board))
			return false;
		if (j==9) {
			setBoard(board);
			return true;
		}
		if (i==9) 
			return solve(board, 0, j+1);
		if (board[j][i] != 0)
			return solve(board, i+1, j);
		int[][] copy = copySquareMatrix(board);
		for (int k = 1; k<10 ; k++ ) {
			copy[j][i] = k;
			if (solve(copy, i, j))
				return true;
		}
		return false;
	}
	private static int[][] copySquareMatrix(int[][] mat) {
		int[][] newMatrix =  new int[9][9];
		for (int i = 0; i<9 ; i++) {
			for (int j = 0; j<9 ; j++) {
				newMatrix[i][j] = mat[i][j];
			}
		}
		return newMatrix;
	}
}
