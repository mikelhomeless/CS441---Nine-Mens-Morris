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

	public void decrementPiecesLeft() {
		if (numPiecesLeft > 0) {
			numPiecesLeft--;
		}
	}

	public int getNumPieces() { return numPieces; }
	public PlayerToken getPlayerToken() {
		return playerToken;
	}

	public int getPiecesOnBoard() {
		return numPiecesOnBoard;
	}

	public int getPiecesLeft() {
		return numPiecesLeft;
	}

	public void incrementPiecesOnBoard() { numPiecesOnBoard++; }
};
