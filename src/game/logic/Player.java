package game.logic;

public class Player {
	private PlayerToken playerToken;
	private int numPieces;
	private int numPiecesLeft;
    
    public Player(PlayerToken Player, int numPieces) {
        this.numPieces = numPieces;
        this.numPiecesLeft = numPieces;
        playerToken = Player;
        
    }
	
	public PlayerToken getPlayerToken() {
		return playerToken;
	}
	
	public int getPiecesLeft() {
		return numPiecesLeft;
	}

	public void incrementPiecesOnBoard() {
		if (numPiecesLeft > 0) {
			numPiecesLeft--;
		}
	}
};