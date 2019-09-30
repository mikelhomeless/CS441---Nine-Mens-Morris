package game.logic;
enum PlayerToken { NOPLAYER, PLAYER1, PLAYER2 };

package game.logic;
enum PlayerToken { NOPLAYER, PLAYER1, PLAYER2 };

class InvalidDecrementOperation extends Exception {
    InvalidDecrementOperation(String s) {
        super(s);
    }
}

private class Player {
	
	private PlayerToken playerToken;
	
	
	private int numPieces;
	
	private int numPiecesOnBoard = 0;
	
	private int numPiecesLeft;
	
    
    public Player(PlayerToken Player, int numPieces) {
        this.numPieces = numPieces;
        this.numPiecesLeft = numPieces;
        playerToken = Player;
        
    }
	
	
	public PlayerToken getPlayerToken() {
		return playerToken;
	}
	
	
	public int getPiecesOnBoard() {
		return numPiecesOnBoard;
	}
	
	
	public int getPiecesLeft() {
		return numPiecesLeft;
	}
	
	
	public void decrementPiecesLeft() throws InvalidDecrementOperation {
		if (numPiecesLeft > 0) {
			numPieceOnBoard++;
			numPiecesLeft--;
		}
		else {
            throw InvalidDecrementOperation("Cannot decrement when no pieces left :(");
		}
	}
	
	
};

