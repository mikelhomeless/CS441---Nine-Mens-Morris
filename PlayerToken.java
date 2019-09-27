package game.logic;
enum PlayerToken { NOPLAYER, PLAYER1, PLAYER2 };


private class Player {
	
	private PlayerToken playerTokens;
	
	
	private int numPieces;
	
	private int numPiecesOnBoard;
	
	private int numPiecesLeft;
	
	
	
	
	public PlayerToken getPlayerToken() {
		return playerTokens;
	}
	
	
	public int getPiecesTotal() {
		return numPiecesTotal;
	}
	
	
	public int getPiecesOnBoard() {
		return numPiecesOnBoard;
	}
	
	
	public int getPiecesLeft() {
		return numPiecesLeft;
	}
	
	
	public void placePiece() {
		if (numPiecesLeft > 0) {
			numPieceOnBoard++;
			numPiecesLeft--;
		}
		else {
			System.out.print("You are out of pieces to place")); 
		}
	}
	
	
};