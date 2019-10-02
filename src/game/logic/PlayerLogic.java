package game.logic;

public class PlayerLogic {

    protected Board gameBoard;
    protected Player Player1 = new Player(PlayerToken.PLAYER1, 9);
    protected Player Player2 = new Player(PlayerToken.PLAYER2, 9);
    protected Player activePlayer = Player1;

public boolean placePlayerPiece(int index) {   //boolean for if player piece can be placed
    int pieces;
    if (gameBoard.getCell(index).isEmpty()) {
        gameBoard.setCell(index, activePlayer.getPlayerToken());
        activePlayer.decrementPiecesLeft();   //decrements the pieces left for current player
        pieces = activePlayer.getPiecesLeft();   //sets the pieces left
        return true;
    }
    return false;
}
public PlayerToken getActivePlayer() {   //call this to get current player token
    return activePlayer;
}

public void nextTurn() {   //finds next turn
    if(activePlayer == Player1)   //if current player token is player 1, then set to player 2, vice versa
    {
        activePlayer = Player2;
    }
    else
    {
        activePlayer = Player1;
    }
}

    public boolean isPhaseOneOver(int pieces) {  //check if phase one is over yet
        if(pieces > 0) {
            return false;
        }
        return true;
    }
}