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

    public boolean Move(int srcIndex, int destIndex) {  
        if(!gameBoard.getCell(srcIndex).isOccupiedBy(getActivePlayer()))  //immediately return false if source index player token does not match
            return false;
       /* if (gameBoard.getCell(destIndex).isOccupied())
            return false; */

       if(activePlayer.getPiecesLeft() > 3) {
          return gameBoard.getCell(srcIndex).isAdjacentTo(destIndex);
       }
       else if(activePlayer.getPiecesLeft() <=3) {
           return gameBoard.getCell(destIndex).isEmpty();
       }
        return false;
    }


    public PlayerToken[] getBoardAsPlayerTokens(){ return gameBoard.getBoard(); }
}