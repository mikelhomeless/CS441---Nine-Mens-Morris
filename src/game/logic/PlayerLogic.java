package game.logic;

public class PlayerLogic {

    protected Board gameBoard = new Board();
    protected Player Player1 = new Player(PlayerToken.PLAYER1, 9);
    protected Player Player2 = new Player(PlayerToken.PLAYER2, 9);
    protected Player activePlayer = Player1;

    public PlayerLogic() {}
    public PlayerLogic(Player player1, Player player2){
        this.Player1 = player1;
        this.Player2 = player2;
        this.activePlayer = Player1;
    }
    public boolean placePlayerPiece(int index) {   // boolean for if player piece can be placed
        if (gameBoard.getCell(index).isEmpty() && isPhaseOne()) {
              gameBoard.setCell(index, activePlayer.getPlayerToken());
              activePlayer.incrementPiecesOnBoard();   // decrements the pieces left for current player
              return true;
        }
        return false;
    }

    public PlayerToken getActivePlayer() {   // call this to get current player token
      return activePlayer.getPlayerToken();
}

    public PlayerToken nextTurn() {   // finds next turn
        if(activePlayer == Player1)   // if current player token is player 1, then set to player 2, vice versa
        {
            activePlayer = Player2;
        }
        else
        {
            activePlayer = Player1;
        }
        return activePlayer.getPlayerToken();
    }

    // returns true if phase one is over
    public boolean isPhaseOneOver() {
        return Player1.getPiecesLeft() == 0 && Player2.getPiecesLeft() == 0;
    }

    // returns true if still in phase one
    public boolean isPhaseOne(){
        return !isPhaseOneOver();
    }

    public PlayerToken[] getBoardAsPlayerTokens(){ return gameBoard.getBoard(); }

    // Called when a player removes an opponent's piece
    public boolean removePiece(int index) {
        // Prevent player from removing own token
        if (activePlayer.getPlayerToken() == gameBoard.getCell(index).getPlayer()) {    // if current player has a piece in the selected cell,
            return false;
        }

        //
        if (gameBoard.removePieceFromCell(index)) {
            if (activePlayer == Player1)
                Player2.decrementPiecesOnBoard();
            else
                Player1.decrementPiecesOnBoard();;
            return true;
        }
        return false;
    }
    public boolean cookieMillCreated(Player currentPlayer, int index){
      /*
      returns true if move just made created as mill
      */
      return true;
    }

//    public PlayerToken[] cookieGetMills(Player givenPlayer){
//      /*
//      Returns array listing all mills for given player
//      */
//      return {PlayerToken.PLAYER1};
//    }

}
