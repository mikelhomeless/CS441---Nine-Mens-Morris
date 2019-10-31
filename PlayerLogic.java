package game.logic;

public class PlayerLogic {

    protected Board gameBoard = new Board();
    protected Player Player1 = new Player(PlayerToken.PLAYER1, 9);
    protected Player Player2 = new Player(PlayerToken.PLAYER2, 9);
    protected Player activePlayer = Player1;

    public boolean placePlayerPiece(int index) {   //boolean for if player piece can be placed
        if (gameBoard.getCell(index).isEmpty() && isPhaseOne()) {
            gameBoard.setCell(index, activePlayer.getPlayerToken());
            activePlayer.incrementPiecesOnBoard();   //decrements the pieces left for current player
            return true;
        }
        return false;
    }

    public PlayerToken getActivePlayer() {   //call this to get current player token
    return activePlayer.getPlayerToken();
}

    public PlayerToken nextTurn() {   //finds next turn
        if(activePlayer == Player1)   //if current player token is player 1, then set to player 2, vice versa
        {
            activePlayer = Player2;
        }
        else
        {
            activePlayer = Player1;
        }
        return activePlayer.getPlayerToken();
    }

    public boolean isPhaseOneOver() {  //returns false if phase one is not over yet
        return Player1.getPiecesLeft() == 0 && Player2.getPiecesLeft() == 0;
    }

    public boolean isPhaseOne(){
        return !isPhaseOneOver();
    }

    public PlayerToken[] getBoardAsPlayerTokens(){ return gameBoard.getBoard(); }
    
    public boolean removePiece(int index) {
        if (activePlayer.getPlayerToken() == gameBoard.getCell(index).getPlayer()) { // i wrote this
            return false;
        }
        
        if gameBoard.removePieceFromCell(int index) {
            if (activePlayer == Player1) {
                Player2.decrementPiecesOnBoard();
            }
            else {
                Player1.decrementPiecesOnBoard()
            }
            return true;
        }
        return false;
    }
}
