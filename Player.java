package game.logic;

public class Player {
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

    public void decrementPiecesOnBoard() {
        if (numPiecesOnBoard > 0) {
            numPiecesOnBoard--;
        }
    }
    
	public void incrementPiecesOnBoard() {
		if (numPiecesLeft > 0) {
			numPiecesOnBoard++;
			numPiecesLeft--;
		}
		// else {
        //     throw InvalidDecrementOperation("Cannot decrement when no pieces left :(");
		// }
	}
};
