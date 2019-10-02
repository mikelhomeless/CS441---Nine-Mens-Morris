package game.logic;

public class PlayerLogic {

    protected Board gameBoard;
    protected Player Player1 = new Player(PlayerToken.PLAYER1, 9);
    protected Player Player2 = new Player(PlayerToken.PLAYER2, 9);
    protected Player activePlayer = Player1;

public boolean placePlayerPiece(int index) {
    int pieces;
    if (gameBoard.getCell(index).isEmpty()) {
        gameBoard.setCell(index, activePlayer.getPlayerToken());
        activePlayer.decrementPiecesLeft();
        pieces = activePlayer.getPiecesLeft();
        isPhaseOneOver(pieces);
        return true;
    }
    return false;
}

public void nextTurn() {
    if(activePlayer == Player1)
    {
        activePlayer = Player2;
    }
    else
    {
        activePlayer = Player1;
    }
}

    public boolean isPhaseOneOver(int pieces) {
        if(pieces > 0) {
            return false;
        }
        return true;
    }
}